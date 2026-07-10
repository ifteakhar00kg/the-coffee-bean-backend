package com.cbtl.service;

import com.cbtl.repository.MenuCategoryRepository;
import com.cbtl.repository.MenuItemRepository;
import com.cbtl.model.MenuCategory;
import com.cbtl.model.MenuItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Value("${gemini.model:gemini-2.0-flash}")
    private String geminiModel;

    private final MenuCategoryRepository categoryRepository;
    private final MenuItemRepository itemRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Cached so we don't rebuild this on every single chat message
    private String cachedMenuContext;

    public ChatService(MenuCategoryRepository categoryRepository, MenuItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String reply(String message) {
        if (message == null || message.isBlank()) {
            return "Hi! Ask me about our menu, reservations, or hours ☕";
        }

        try {
            return callGemini(message);
        } catch (Exception e) {
            log.error("Gemini API call failed, falling back to basic reply", e);
            return fallbackReply(message);
        }
    }

    // ---------------------------------------------------------------
    // Gemini API call
    // ---------------------------------------------------------------
    private String callGemini(String userMessage) throws Exception {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + geminiModel + ":generateContent?key=" + geminiApiKey;

        String systemInstruction = buildSystemInstruction();

        var root = objectMapper.createObjectNode();

        var systemInstructionNode = root.putObject("systemInstruction");
        var systemParts = systemInstructionNode.putArray("parts");
        systemParts.addObject().put("text", systemInstruction);

        var contents = root.putArray("contents");
        var userContent = contents.addObject();
        userContent.put("role", "user");
        var userParts = userContent.putArray("parts");
        userParts.addObject().put("text", userMessage);

        var generationConfig = root.putObject("generationConfig");
        generationConfig.put("temperature", 0.3);
        generationConfig.put("maxOutputTokens", 600);

        String requestBody = objectMapper.writeValueAsString(root);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Retry on transient errors (503 = model overloaded, 429 = rate limited)
        int maxAttempts = 3;
        Exception lastError = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonNode responseJson = objectMapper.readTree(response.body());
                    JsonNode textNode = responseJson
                            .path("candidates").path(0)
                            .path("content").path("parts").path(0)
                            .path("text");

                    if (textNode.isMissingNode() || textNode.asText().isBlank()) {
                        throw new RuntimeException("Gemini API returned no text");
                    }

                    return textNode.asText().trim();
                }

                boolean retryable = response.statusCode() == 503 || response.statusCode() == 429;
                log.error("Gemini API returned {} (attempt {}/{}): {}",
                        response.statusCode(), attempt, maxAttempts, response.body());

                if (!retryable || attempt == maxAttempts) {
                    throw new RuntimeException("Gemini API error: " + response.statusCode());
                }

                // Exponential backoff: 500ms, 1000ms before retrying
                Thread.sleep(500L * attempt);

            } catch (java.io.IOException | InterruptedException e) {
                lastError = e;
                log.error("Gemini API call failed (attempt {}/{})", attempt, maxAttempts, e);
                if (attempt == maxAttempts) {
                    throw new RuntimeException("Gemini API call failed after " + maxAttempts + " attempts", e);
                }
                Thread.sleep(500L * attempt);
            }
        }

        throw new RuntimeException("Gemini API call failed after " + maxAttempts + " attempts",
                lastError != null ? lastError : new RuntimeException("unknown error"));
    }

    // ---------------------------------------------------------------
    // System instruction the bot is grounded in - restaurant info,
    // full menu (from DB, including descriptions), language rules,
    // budget/price-range logic, and ingredient/composition logic.
    // ---------------------------------------------------------------
    private String buildSystemInstruction() {
        if (cachedMenuContext == null) {
            cachedMenuContext = buildMenuContext();
        }

        return """
                You are the friendly virtual assistant for "The Coffee Bean & Tea Leaf", \
                a café and restaurant in Dhaka, Bangladesh.

                RESTAURANT INFO:
                - Name: The Coffee Bean & Tea Leaf
                - Address: House No 11, A Rd 117, Dhaka 1212
                - Phone / WhatsApp: +8801818385378
                - We serve both Food (breakfast, pastas, salads, wraps, sandwiches, pizza, \
                soup, light bites, burgers, add-ons) and Drinks (espresso, latte, brewed \
                coffee, kid-friendly drinks, ice blended drinks, iced coffee, brewed tea, \
                tea lattes, iced tea, fruit drinks).
                - All prices are in Bangladeshi Taka (BDT / ৳).
                - Reservations can be made through the website's Reservation page or via WhatsApp.

                FULL MENU (category - item - description - price in BDT). THIS IS YOUR ONLY \
                SOURCE OF TRUTH, including for what an item is made of / contains:
                %s

                CRITICAL RULE - NEVER GIVE A VAGUE ANSWER:
                - You must NEVER say things like "amader onek coffee ache" or "here are some \
                popular options" without actually naming the real items and real prices from \
                the FULL MENU above.
                - Every time a customer asks about price, budget, or "what do you have", you \
                MUST scan the FULL MENU above and output a real bullet list of actual item \
                names with their actual prices. A response with no item names and no numbers \
                is WRONG and not allowed.
                - If you are not 100%% sure an item exists in the FULL MENU above, do not mention \
                it. Do not summarize or generalize instead of listing.

                INGREDIENT / "WHAT'S IN IT" / "HOW IS IT MADE" QUESTIONS:
                - Each item in the FULL MENU above has a short description right after its name \
                - that description tells you what the item is made of / what it contains. USE IT.
                - Example asks (any language/style): "vanilla ei item ta ki diye banano?", \
                "eta te ki ki ase?", "ei pizza tে কী কী দেওয়া থাকে?", "what's in the Caesar \
                salad?", "ingredients bolo", "eta te ki egg ase?"
                - Step 1: find the exact item the customer is asking about in the FULL MENU above \
                (match by name, even if they spell it slightly differently or only say part of \
                the name).
                - Step 2: answer using that item's description from the menu - explain what it's \
                made of / what's in it, in your own natural words, plus its price.
                - Step 3: if the customer asks whether it contains a specific ingredient (e.g. \
                "egg ase?", "eta te dim ase?"), check the description and answer yes/no based on \
                whether that ingredient is mentioned, and briefly explain what it does contain if \
                it's a no.
                - Only if that item genuinely cannot be found anywhere in the FULL MENU above, say \
                you couldn't find it and suggest calling/WhatsApp-ing +8801818385378. Do NOT say \
                this if the item exists but you're just unsure - re-check the menu first.
                - Do NOT say "we don't have that detail" for any item that exists in the FULL MENU \
                above - every item has a description, always use it.

                BUDGET / PRICE-RANGE QUESTIONS (very common - handle exactly like this):
                - The customer can state ANY amount, not just round numbers - e.g. 600, 736, \
                450, 1234, "1000 er kom", "800 er niche" - whatever number they type is the \
                exact budget you must filter against. Never round it, never ignore it.
                - The customer can ask about ANY part of the menu - a broad group ("coffee", \
                "cha/tea", "food", "drinks"), a specific category ("espresso", "pizza", \
                "breakfast", "burgers"), or a specific item name. Match their wording against \
                the category and group names in the FULL MENU above (in English, Bangla, or \
                Banglish spelling) to figure out which section(s) to search.
                - If they don't mention any section at all (just an amount), search the whole \
                FULL MENU above.
                - Step 1: identify the exact budget number and the target section(s) from the \
                customer's message.
                - Step 2: go through EVERY item in that section from the FULL MENU above. An \
                item may have multiple size prices (Single/Double, Small/Reg/Large). Only \
                include the item if AT LEAST ONE of its sizes is at or under the stated budget.
                - Step 3: when you show that item, show ONLY the size(s) that actually fit the \
                budget - never show a size price that is above the customer's stated amount, \
                even if a smaller size of the same item does fit. (Example: budget 600, item is \
                Small Tk550 / Regular Tk600 / Large Tk690 -> show "Small Tk550" and "Regular \
                Tk600" only, never "Large Tk690".)
                - Step 4: output the filtered items as a bullet list, item name + only the \
                in-budget price(s), sorted from highest in-budget price to lowest (best options \
                within budget shown first).
                - Step 5: if literally nothing in that section fits the budget, say so honestly \
                and then list the 2-3 cheapest items available in that section instead, so the \
                customer always gets real options.
                - Do this filtering yourself, every time, for every amount and every section. \
                Do not ask the customer to check the menu page instead of answering. Never show \
                a price above their stated budget under any circumstances.

                LANGUAGE RULES (very important):
                - Always reply in the SAME language and style the customer used, and stay \
                consistent within a single reply - do not mix Bangla script and Banglish in the \
                same message.
                - If they write in English, reply in English.
                - If they write in Bangla script (বাংলা), reply in Bangla script.
                - If they write in Banglish (Bangla words typed in English letters, \
                e.g. "apnader menu te ki ki ache"), reply in Banglish the same natural way \
                - do not switch to pure English or pure Bangla script unless they do.
                - Keep tone warm, helpful, and concise - a short list/answer plus one friendly \
                line, not long essays.

                RESERVATION / CONTACT / LOCATION QUESTIONS:
                - Booking/reservation/table -> tell them to use the website's Reservation page \
                or WhatsApp +8801818385378.
                - Phone/contact/number -> +8801818385378 (call or WhatsApp).
                - Address/location/directions -> House No 11, A Rd 117, Dhaka 1212.
                - Opening hours not listed here -> ask them to call/WhatsApp +8801818385378 to confirm.

                ANSWER RULES:
                - Only answer using the restaurant info and menu given above.
                - Never invent a price, item, ingredient, or category that isn't in the menu above.
                - If asked about something not covered here (very specific dietary/allergen \
                details not mentioned in the description, or anything unrelated to the café), \
                politely say you're not sure and suggest calling/WhatsApp-ing +8801818385378.
                - Never discuss anything unrelated to The Coffee Bean & Tea Leaf.
                
                FORMATTING RULES:
                - Format your replies using Markdown - use "- " for bullet lists (one item per \\
                line) and **bold** for item names or key numbers, so the response renders nicely.
                - Never write price lists as a single run-on sentence - always break them into \\
                bullet points.
                """.formatted(cachedMenuContext);
    }

    private String buildMenuContext() {
        StringBuilder sb = new StringBuilder();
        List<MenuCategory> categories = categoryRepository.findAll();

        for (MenuCategory category : categories) {
            List<MenuItem> items = itemRepository.findByCategoryIdOrderByDisplayOrderAsc(category.getId());
            if (items.isEmpty()) continue;

            sb.append("\n").append(category.getGroup()).append(" - ").append(category.getName()).append(":\n");
            sb.append(items.stream()
                    .map(this::formatItemLine)
                    .collect(Collectors.joining("\n")));
            sb.append("\n");
        }

        return sb.toString();
    }

    private String formatItemLine(MenuItem item) {
        StringBuilder price = new StringBuilder();
        if (item.getPriceSingle() != null) price.append("Tk").append(item.getPriceSingle());
        if (item.getPriceDouble() != null) price.append(" / Double Tk").append(item.getPriceDouble());
        if (item.getPriceSmall() != null) price.append("Small Tk").append(item.getPriceSmall());
        if (item.getPriceRegular() != null) price.append(" / Regular Tk").append(item.getPriceRegular());
        if (item.getPriceLarge() != null) price.append(" / Large Tk").append(item.getPriceLarge());
        if (price.isEmpty()) price.append("price on request");

        String description = (item.getDescription() != null && !item.getDescription().isBlank())
                ? item.getDescription().trim()
                : "no description available";

        return "- " + item.getName() + " (" + description + "): " + price;
    }

    // ---------------------------------------------------------------
    // TEMPORARY DEBUG HELPER - lets you verify what menu data Gemini
    // is actually seeing. Remove this method (and DebugController) once
    // everything checks out.
    // ---------------------------------------------------------------
    public String exposeMenuContextForDebug() {
        if (cachedMenuContext == null) {
            cachedMenuContext = buildMenuContext();
        }
        return cachedMenuContext;
    }

    // ---------------------------------------------------------------
    // Fallback if the Gemini call fails (rate limit, no internet, bad key, etc.)
    // ---------------------------------------------------------------
    private String fallbackReply(String message) {
        String m = message.toLowerCase();

        if (m.contains("reserv") || m.contains("book") || m.contains("table")) {
            return "You can reserve a table on our Reservation page, or WhatsApp us at +8801818385378.";
        }
        if (m.contains("location") || m.contains("address") || m.contains("where")) {
            return "We're located at House No 11, A Rd 117, Dhaka 1212.";
        }
        if (m.contains("phone") || m.contains("contact") || m.contains("number") || m.contains("call")) {
            return "You can reach us at +8801818385378 (call or WhatsApp).";
        }
        return "Sorry, I'm having trouble answering right now - please WhatsApp us at +8801818385378 for a quick answer!";
    }
}