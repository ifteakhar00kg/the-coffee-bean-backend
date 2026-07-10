package com.cbtl.service;

import com.cbtl.model.MenuCategory;
import com.cbtl.model.MenuGroup;
import com.cbtl.model.MenuItem;
import com.cbtl.repository.MenuCategoryRepository;
import com.cbtl.repository.MenuItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MenuCategoryRepository categoryRepo;
    private final MenuItemRepository itemRepo;

    public DataSeeder(MenuCategoryRepository categoryRepo, MenuItemRepository itemRepo) {
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
    }

    @Override
    public void run(String... args) {
        if (categoryRepo.count() > 0) {
            return; // ডাটাবেজে ডেটা অলরেডি থাকলে স্কিপ করবে
        }
        seedDrinks();
        seedFood();
    }

    private void seedDrinks() {
        int order = 1;

        // 01. Espresso
        MenuCategory espresso = category("Espresso", MenuGroup.DRINKS, 1, "The pure heart of the bean — short, bold, unflinching.", "https://images.unsplash.com/photo-1510707577719-ae7c14805e3a?auto=format&fit=crop&w=900&q=80", order++);
        singleDouble(espresso, "Espresso", "Two ounces of unhurried, syrup-thick shot.", "https://images.unsplash.com/photo-1510707577719-ae7c14805e3a?auto=format&fit=crop&w=900&q=80", false, 260, 320, 0);
        singleDouble(espresso, "Espresso Macchiato", "Espresso marked with a spoon of milk foam.", "https://images.unsplash.com/photo-1610889556528-9a770e32642f?auto=format&fit=crop&w=900&q=80", false, 290, 350, 1);
        singleDouble(espresso, "Caramel Macchiato", "Vanilla, milk, espresso, drizzled with caramel.", "https://images.unsplash.com/photo-1587734195503-904fca47e0e9?auto=format&fit=crop&w=900&q=80", false, 370, 430, 2);
        singleDouble(espresso, "Cappuccino", "Even thirds of espresso, milk, and cloud-like foam.", "https://images.unsplash.com/photo-1534778101976-62847782c213?auto=format&fit=crop&w=900&q=80", false, 330, 410, 3);
        singleDouble(espresso, "Caramel Cappuccino", "Classic cappuccino kissed with buttery caramel.", "https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?auto=format&fit=crop&w=900&q=80", false, 380, 460, 4);
        singleDouble(espresso, "Americano", "Espresso lengthened with hot water — clean and direct.", "https://images.unsplash.com/photo-1494314671902-399b18174975?auto=format&fit=crop&w=900&q=80", false, 270, 330, 5);
        singleDouble(espresso, "Hazelnut Americano", "Americano warmed with a note of toasted hazelnut.", "https://images.unsplash.com/photo-1541167760496-1628856ab772?auto=format&fit=crop&w=900&q=80", true, 420, 590, 6);

        // 02. Latte
        MenuCategory latte = category("Latte", MenuGroup.DRINKS, 2, "Silken milk poured over a warm foundation of espresso.", "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=900&q=80", order++);
        smallRegLarge(latte, "Café Latte", "Espresso layered under silken steamed milk.", "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=900&q=80", false, 290, 350, 390, 0);
        smallRegLarge(latte, "Caramel Latte", "House caramel folded through espresso and milk.", "https://images.unsplash.com/photo-1497636577773-f1231844b336?auto=format&fit=crop&w=900&q=80", false, 360, 460, 560, 1);
        smallRegLarge(latte, "Mocha Latte", "Dark cocoa, espresso, and steamed milk in balance.", "https://images.unsplash.com/photo-1578314675249-a6910f80cc4e?auto=format&fit=crop&w=900&q=80", false, 340, 460, 560, 2);
        smallRegLarge(latte, "Vanilla Latte", "A soft breath of Madagascar vanilla.", "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?auto=format&fit=crop&w=900&q=80", false, 360, 460, 560, 3);
        smallRegLarge(latte, "Dark Chocolate Latte", "Deep, unsweetened cocoa poured over espresso.", "https://images.unsplash.com/photo-1517578239113-b03992dcdd25?auto=format&fit=crop&w=900&q=80", false, 360, 460, 560, 4);
        smallRegLarge(latte, "White Chocolate Latte", "Creamy white chocolate, gently sweet.", "https://images.unsplash.com/photo-1485808191679-5f86510681a2?auto=format&fit=crop&w=900&q=80", false, 360, 460, 560, 5);
        smallRegLarge(latte, "Hazelnut Latte", "Roasted hazelnut warmed into milk and espresso.", "https://images.unsplash.com/photo-1542990253-a781e04c0082?auto=format&fit=crop&w=900&q=80", false, 550, 600, 690, 6);

        // 03. Brewed Coffee
        MenuCategory brewedCoffee = category("Brewed Coffee", MenuGroup.DRINKS, 3, "Slow-drawn brews, honest and unadorned.", "https://images.unsplash.com/photo-1497935586351-b67a49e012bf?auto=format&fit=crop&w=900&q=80", order++);
        regLarge(brewedCoffee, "Today's Brew", "The bean of the day, brewed fresh by the pot.", "https://images.unsplash.com/photo-1509042239860-f550ce710b93?auto=format&fit=crop&w=900&q=80", false, 250, 300, 0);
        regLarge(brewedCoffee, "Café au Lait", "Brewed coffee softened with steamed milk.", "https://images.unsplash.com/photo-1497935586351-b67a49e012bf?auto=format&fit=crop&w=900&q=80", true, 300, 350, 1);
        regLarge(brewedCoffee, "Café Mocha", "Brewed coffee stirred with cocoa and cream.", "https://images.unsplash.com/photo-1442512595331-e89e73853f31?auto=format&fit=crop&w=900&q=80", true, 390, 490, 2);
        regLarge(brewedCoffee, "Café Vanilla", "Brewed coffee lifted with a touch of vanilla.", "https://images.unsplash.com/photo-1521302200778-33500795e128?auto=format&fit=crop&w=900&q=80", true, 390, 490, 3);

        // 04. Kid-Friendly
        MenuCategory kidFriendly = category("Kid-Friendly", MenuGroup.DRINKS, 4, "Gentle, caffeine-free comforts for smaller hands.", "https://images.unsplash.com/photo-1517578239113-b03992dcdd25?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(kidFriendly, "Babycino", "A small cup of steamed milk with cocoa dust.", "https://images.unsplash.com/photo-1517578239113-b03992dcdd25?auto=format&fit=crop&w=900&q=80", false, 200, 0);
        smallRegLarge(kidFriendly, "Hot Chocolate", "Rich cocoa melted into velvety milk.", "https://images.unsplash.com/photo-1517578239113-b03992dcdd25?auto=format&fit=crop&w=900&q=80", false, 330, 370, 410, 1);
        smallRegLarge(kidFriendly, "Hot Vanilla", "Warm milk gently sweetened with vanilla.", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=900&q=80", false, 330, 380, 430, 2);

        // 05. Ice Blended — The Original
        MenuCategory iceBlendedOriginal = category("Ice Blended — The Original", MenuGroup.DRINKS, 5, "The blend that began it all, still made just the same.", "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=900&q=80", order++);
        regLarge(iceBlendedOriginal, "Mocha", "The original — coffee, cocoa, ice, cream.", "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=900&q=80", false, 510, 620, 0);
        regLarge(iceBlendedOriginal, "Vanilla", "Cold-blended vanilla ice with espresso.", "https://images.unsplash.com/photo-1497534446932-c925b458314e?auto=format&fit=crop&w=900&q=80", false, 550, 650, 1);
        regLarge(iceBlendedOriginal, "Caramel", "Icy, silky caramel with a coffee spine.", "https://images.unsplash.com/photo-1558122104-355edad709f6?auto=format&fit=crop&w=900&q=80", false, 550, 650, 2);
        regLarge(iceBlendedOriginal, "White Chocolate", "Creamy white chocolate over crushed ice.", "https://images.unsplash.com/photo-1485808191679-5f86510681a2?auto=format&fit=crop&w=900&q=80", false, 550, 650, 3);
        regLarge(iceBlendedOriginal, "Dark Chocolate", "Deep chocolate blended cold with coffee.", "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=900&q=80", false, 550, 650, 4);
        regLarge(iceBlendedOriginal, "Cookies & Cream", "Crushed cookies whipped through cold cream.", "https://images.unsplash.com/photo-1568051243858-533a607809a5?auto=format&fit=crop&w=900&q=80", false, 550, 650, 5);
        regLarge(iceBlendedOriginal, "Black Forest", "Cherry and dark chocolate, blended cold.", "https://images.unsplash.com/photo-1553787499-6f9133860278?auto=format&fit=crop&w=900&q=80", false, 560, 660, 6);
        regLarge(iceBlendedOriginal, "Hazelnut", "Toasted hazelnut whipped with espresso ice.", "https://images.unsplash.com/photo-1542990253-a781e04c0082?auto=format&fit=crop&w=900&q=80", false, 590, 690, 7);
        regLarge(iceBlendedOriginal, "Matcha Green Tea", "Stone-ground matcha blended smooth.", "https://images.unsplash.com/photo-1536256263959-770b48d82b0a?auto=format&fit=crop&w=900&q=80", false, 540, 640, 8);
        regLarge(iceBlendedOriginal, "Matcha Espresso", "Matcha meets espresso in a cold, layered blend.", "https://images.unsplash.com/photo-1515823064-d6e0c04616a7?auto=format&fit=crop&w=900&q=80", true, 590, 690, 9);

        // 06. Non-Coffee Ice Blended
        MenuCategory nonCoffeeIceBlended = category("Non-Coffee Ice Blended", MenuGroup.DRINKS, 6, "Pure creamy blends without a trace of coffee.", "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=900&q=80", order++);
        regLarge(nonCoffeeIceBlended, "Pure Caramel", "Caramel and cream, blended cold — no coffee.", "https://images.unsplash.com/photo-1558122104-355edad709f6?auto=format&fit=crop&w=900&q=80", false, 550, 650, 0);
        regLarge(nonCoffeeIceBlended, "Pure Vanilla", "Vanilla-forward, coffee-free.", "https://images.unsplash.com/photo-1497534446932-c925b458314e?auto=format&fit=crop&w=900&q=80", false, 550, 650, 1);
        regLarge(nonCoffeeIceBlended, "Pure Chocolate", "Pure cocoa blended with ice and milk.", "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=900&q=80", false, 550, 650, 2);

        // 07. Iced Espresso & Coffee
        MenuCategory icedEspresso = category("Iced Espresso & Coffee", MenuGroup.DRINKS, 7, "Cold, clear-headed, and endlessly refreshing.", "https://images.unsplash.com/photo-1517701604599-bb29b565090c?auto=format&fit=crop&w=900&q=80", order++);
        regLarge(icedEspresso, "Iced Americano", "Espresso poured over cold water and ice.", "https://images.unsplash.com/photo-1517701604599-bb29b565090c?auto=format&fit=crop&w=900&q=80", false, 290, 350, 0);
        regLarge(icedEspresso, "Iced Cappuccino", "Cold foam, cold milk, deep espresso.", "https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?auto=format&fit=crop&w=900&q=80", false, 390, 480, 1);
        regLarge(icedEspresso, "Iced Café Latte", "Chilled milk over a slow-drawn shot.", "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=900&q=80", false, 390, 480, 2);
        regLarge(icedEspresso, "Iced Mocha Latte", "Iced latte layered with cocoa.", "https://images.unsplash.com/photo-1578314675249-a6910f80cc4e?auto=format&fit=crop&w=900&q=80", false, 390, 480, 3);
        regLarge(icedEspresso, "Iced Vanilla Latte", "Vanilla-scented iced latte.", "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?auto=format&fit=crop&w=900&q=80", false, 390, 480, 4);
        regLarge(icedEspresso, "Iced Caramel Latte", "Iced latte with a caramel drizzle.", "https://images.unsplash.com/photo-1497636577773-f1231844b336?auto=format&fit=crop&w=900&q=80", false, 390, 480, 5);
        regLarge(icedEspresso, "Iced Dark Chocolate Latte", "Cold latte with deep chocolate.", "https://images.unsplash.com/photo-1517578239113-b03992dcdd25?auto=format&fit=crop&w=900&q=80", false, 390, 480, 6);
        regLarge(icedEspresso, "Iced White Chocolate Latte", "Cold latte, creamy white cocoa.", "https://images.unsplash.com/photo-1485808191679-5f86510681a2?auto=format&fit=crop&w=900&q=80", false, 390, 480, 7);
        regLarge(icedEspresso, "Iced Hazelnut Latte", "Iced latte warmed by hazelnut.", "https://images.unsplash.com/photo-1542990253-a781e04c0082?auto=format&fit=crop&w=900&q=80", false, 550, 600, 8);
        regLarge(icedEspresso, "Iced Hazelnut Americano", "Iced americano lifted by toasted hazelnut.", "https://images.unsplash.com/photo-1541167760496-1628856ab772?auto=format&fit=crop&w=900&q=80", true, 590, 690, 9);

        // 08. Brewed Tea
        MenuCategory brewedTea = category("Brewed Tea", MenuGroup.DRINKS, 8, "Hand-picked loose leaves, steeped with patience.", "https://images.unsplash.com/photo-1594631252845-29fc4cc8cde9?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(brewedTea, "Chai", "Spiced black tea, slowly infused.", "https://images.unsplash.com/photo-1571934811356-5cc061b6821f?auto=format&fit=crop&w=900&q=80", false, 220, 0);
        singlePrice(brewedTea, "English Breakfast", "Bold, malty, the tea of quiet mornings.", "https://images.unsplash.com/photo-1594631252845-29fc4cc8cde9?auto=format&fit=crop&w=900&q=80", false, 220, 1);
        singlePrice(brewedTea, "Moroccan Mint", "Green tea steeped with fresh mint.", "https://images.unsplash.com/photo-1547825407-2d060104b7f8?auto=format&fit=crop&w=900&q=80", false, 220, 2);
        singlePrice(brewedTea, "Earl Grey", "Ceylon black tea scented with bergamot.", "https://images.unsplash.com/photo-1518057111178-44a106bad636?auto=format&fit=crop&w=900&q=80", false, 220, 3);
        singlePrice(brewedTea, "Apricot Ceylon", "Ceylon black tea with soft apricot notes.", "https://images.unsplash.com/photo-1571658734495-e73607e0f8e6?auto=format&fit=crop&w=900&q=80", false, 220, 4);
        singlePrice(brewedTea, "Tropical Passion", "Bright fruit and hibiscus, gently brewed.", "https://images.unsplash.com/photo-1563822249366-3efb23b8e0c9?auto=format&fit=crop&w=900&q=80", false, 220, 5);
        singlePrice(brewedTea, "Blueberry Pomegranate", "Ruby-red infusion, fruit-forward.", "https://images.unsplash.com/photo-1556679343-c7306c1976bc?auto=format&fit=crop&w=900&q=80", false, 220, 6);
        singlePrice(brewedTea, "Lemon Chamomile", "Chamomile flowers with a whisper of lemon.", "https://images.unsplash.com/photo-1523920290228-4f321a939b4c?auto=format&fit=crop&w=900&q=80", false, 220, 7);
        singlePrice(brewedTea, "Green Tea", "Delicate, grassy, quietly restorative.", "https://images.unsplash.com/photo-1519002222695-a768c184eab7?auto=format&fit=crop&w=900&q=80", false, 220, 8);
        singlePrice(brewedTea, "Ginseng Peppermint", "Cooling peppermint with a note of ginseng.", "https://images.unsplash.com/photo-1547825407-2d060104b7f8?auto=format&fit=crop&w=900&q=80", false, 220, 9);

        // 09. Tea Latte
        MenuCategory teaLatte = category("Tea Latte", MenuGroup.DRINKS, 9, "Steamed milk meets steeped leaf — hot or iced.", "https://images.unsplash.com/photo-1536256263959-770b48d82b0a?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(teaLatte, "Chai Latte", "Spiced chai steeped through steamed milk.", "https://images.unsplash.com/photo-1571934811356-5cc061b6821f?auto=format&fit=crop&w=900&q=80", false, 290, 0);
        singlePrice(teaLatte, "English Breakfast Latte", "Malty black tea folded with milk.", "https://images.unsplash.com/photo-1544787219-7f47ccb76574?auto=format&fit=crop&w=900&q=80", false, 290, 1);
        singlePrice(teaLatte, "Earl Grey Latte", "Bergamot-scented tea, warmed with milk.", "https://images.unsplash.com/photo-1518057111178-44a106bad636?auto=format&fit=crop&w=900&q=80", false, 290, 2);
        singlePrice(teaLatte, "Tropical Passion Latte", "Bright fruit-tea, softened with milk.", "https://images.unsplash.com/photo-1563822249366-3efb23b8e0c9?auto=format&fit=crop&w=900&q=80", false, 290, 3);
        singlePrice(teaLatte, "Moroccan Mint Latte", "Mint-green tea, poured over hot milk.", "https://images.unsplash.com/photo-1515823064-d6e0c04616a7?auto=format&fit=crop&w=900&q=80", false, 290, 4);
        singlePrice(teaLatte, "Apricot Ceylon Latte", "Apricot-scented ceylon with steamed milk.", "https://images.unsplash.com/photo-1544787219-7f47ccb76574?auto=format&fit=crop&w=900&q=80", false, 290, 5);
        singlePrice(teaLatte, "Matcha Green Latte", "Whisked matcha, silk-milk, no shortcuts.", "https://images.unsplash.com/photo-1536256263959-770b48d82b0a?auto=format&fit=crop&w=900&q=80", false, 290, 6);

        // 10. Vanilla Tea Latte
        MenuCategory vanillaTeaLatte = category("Vanilla Tea Latte", MenuGroup.DRINKS, 10, "A soft vanilla whisper folded into tea and milk.", "https://images.unsplash.com/photo-1515823064-d6e0c04616a7?auto=format&fit=crop&w=900&q=80", order++);
        regLarge(vanillaTeaLatte, "Chai Vanilla", "Chai and vanilla, steamed together.", "https://images.unsplash.com/photo-1571934811356-5cc061b6821f?auto=format&fit=crop&w=900&q=80", false, 460, 560, 0);
        regLarge(vanillaTeaLatte, "English Breakfast Vanilla", "Vanilla-brightened black tea latte.", "https://images.unsplash.com/photo-1544787219-7f47ccb76574?auto=format&fit=crop&w=900&q=80", false, 460, 560, 1);
        regLarge(vanillaTeaLatte, "Matcha Vanilla", "Matcha and Madagascar vanilla, blended.", "https://images.unsplash.com/photo-1515823064-d6e0c04616a7?auto=format&fit=crop&w=900&q=80", false, 550, 650, 2);

        // 11. Signature Iced Tea
        MenuCategory signatureIcedTea = category("Signature Iced Tea", MenuGroup.DRINKS, 11, "Slow-brewed cold infusions, house-recipe only.", "https://images.unsplash.com/photo-1556679343-c7306c1976bc?auto=format&fit=crop&w=900&q=80", order++);
        regLarge(signatureIcedTea, "Swedish Berries", "Elderberry and hibiscus, slow-brewed cold.", "https://images.unsplash.com/photo-1556679343-c7306c1976bc?auto=format&fit=crop&w=900&q=80", false, 360, 430, 0);
        regLarge(signatureIcedTea, "Tea of The Month Cold Brew", "A rotating leaf, steeped overnight.", "https://images.unsplash.com/photo-1499638673689-79a0b5115d87?auto=format&fit=crop&w=900&q=80", false, 360, 430, 1);

        // 12. Fruit Based
        MenuCategory fruitBased = category("Fruit Based", MenuGroup.DRINKS, 12, "Bright, sun-lit fruit, poured over ice.", "https://images.unsplash.com/photo-1600271886742-f049cd451bba?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(fruitBased, "California Sunrise", "Fresh orange and vanilla poured over ice.", "https://images.unsplash.com/photo-1613478223719-2ab802602423?auto=format&fit=crop&w=900&q=80", false, 550, 0);

        MenuItem freshOJ = new MenuItem();
        freshOJ.setCategory(fruitBased);
        freshOJ.setName("Freshly Squeezed Orange Juice");
        freshOJ.setDescription("Eight ounces of just-pressed oranges.");
        freshOJ.setImageUrl("https://images.unsplash.com/photo-1600271886742-f049cd451bba?auto=format&fit=crop&w=900&q=80");
        freshOJ.setIsNew(false);
        freshOJ.setDisplayOrder(1);
        itemRepo.save(freshOJ);

        // 13. Customize It
        MenuCategory customizeIt = category("Customize It", MenuGroup.DRINKS, 13, "Small additions to make your cup entirely your own.", "https://images.unsplash.com/photo-1509365465985-25d11c17e812?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(customizeIt, "Vanilla Syrup", "A soft round of vanilla sweetness.", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?auto=format&fit=crop&w=900&q=80", false, 120, 0);
        singlePrice(customizeIt, "Chocolate Syrup", "Ribbon of cocoa syrup, stirred in.", "https://images.unsplash.com/photo-1587049633312-d628ae50a8ae?auto=format&fit=crop&w=900&q=80", false, 120, 1);
        singlePrice(customizeIt, "Chocolate Sauce", "A finishing drizzle of dark chocolate.", "https://images.unsplash.com/photo-1587049633312-d628ae50a8ae?auto=format&fit=crop&w=900&q=80", false, 100, 2);
        singlePrice(customizeIt, "Espresso Shot", "One extra shot, deeper and darker.", "https://images.unsplash.com/photo-1510707577719-ae7c14805e3a?auto=format&fit=crop&w=900&q=80", false, 100, 3);
        singlePrice(customizeIt, "Whipped Cream", "House-whipped cream, softly sweetened.", "https://images.unsplash.com/photo-1541599540903-216a46ca1dc0?auto=format&fit=crop&w=900&q=80", false, 100, 4);
        singlePrice(customizeIt, "Caramel Sauce", "Slow-cooked caramel, drizzled on top.", "https://images.unsplash.com/photo-1558122104-355edad709f6?auto=format&fit=crop&w=900&q=80", false, 100, 5);
        singlePrice(customizeIt, "Honey Stick", "Wildflower honey in a wooden stick.", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?auto=format&fit=crop&w=900&q=80", false, 90, 6);
    }

    private void seedFood() {
        int order = 1;

        // 14. Breakfast All Day
        MenuCategory breakfast = category("Breakfast All Day", MenuGroup.FOOD, 1, "Slow mornings, served whenever you like.", "https://images.unsplash.com/photo-1533089860892-a7c6f0a88666?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(breakfast, "Ultimate Breakfast", "Eggs, sausage, beans, hash brown, toast — the full plate.", "https://images.unsplash.com/photo-1533089860892-a7c6f0a88666?auto=format&fit=crop&w=900&q=80", false, 750, 0);
        singlePrice(breakfast, "Ultimate Breakfast (Veg)", "The full plate, without meat.", "https://images.unsplash.com/photo-1525351484163-7529414344d8?auto=format&fit=crop&w=900&q=80", false, 620, 1);
        singlePrice(breakfast, "Salmon & Egg Muffin", "Smoked salmon, poached egg, toasted muffin.", "https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=900&q=80", false, 720, 2);
        singlePrice(breakfast, "Salmon Scramble", "Soft eggs folded with smoked salmon.", "https://images.unsplash.com/photo-1550328521-e239df22fb62?auto=format&fit=crop&w=900&q=80", false, 680, 3);
        singlePrice(breakfast, "Eggs Ben", "Poached eggs, hollandaise, English muffin.", "https://images.unsplash.com/photo-1608039755401-742074f0548d?auto=format&fit=crop&w=900&q=80", false, 530, 4);
        singlePrice(breakfast, "Ham Cheese Egg Muffin", "Ham, melted cheese, egg, toasted muffin.", "https://images.unsplash.com/photo-1484723091739-30a097e8f929?auto=format&fit=crop&w=900&q=80", false, 460, 5);
        singlePrice(breakfast, "Club Sandwich", "Triple-layer with chicken, egg, and greens.", "https://images.unsplash.com/photo-1567234669003-dce7a7a88821?auto=format&fit=crop&w=900&q=80", false, 450, 6);
        singlePrice(breakfast, "Toast Butter & Jam", "Warm toast, salted butter, house preserve.", "https://images.unsplash.com/photo-1484284001141-2ecc59f47f5a?auto=format&fit=crop&w=900&q=80", false, 330, 7);
        singlePrice(breakfast, "Chocolate Croissant", "Warm pastry, dark chocolate baton inside.", "https://images.unsplash.com/photo-1509365465985-25d11c17e812?auto=format&fit=crop&w=900&q=80", false, 320, 8);
        singlePrice(breakfast, "Butter Croissant", "Hand-laminated, all-butter, golden crust.", "https://images.unsplash.com/photo-1555507036-ab1f4038808a?auto=format&fit=crop&w=900&q=80", false, 230, 9);
        singlePrice(breakfast, "Waffle: Apple & Cream", "Crisp waffle, spiced apple, whipped cream.", "https://images.unsplash.com/photo-1562376552-0d160a2f238d?auto=format&fit=crop&w=900&q=80", false, 300, 10);
        singlePrice(breakfast, "Bagel", "Chewy, boiled, and freshly baked.", "https://images.unsplash.com/photo-1592767830261-59d51e0e2b74?auto=format&fit=crop&w=900&q=80", false, 230, 11);

        // 15. Pastas
        MenuCategory pastas = category("Pastas", MenuGroup.FOOD, 2, "Twirled, tossed, and finished in the pan.", "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(pastas, "Chicken Lasagna", "Layered pasta, chicken ragù, béchamel.", "https://images.unsplash.com/photo-1619895092538-128341789043?auto=format&fit=crop&w=900&q=80", false, 670, 0);
        singlePrice(pastas, "Spicy Tuna", "Tuna, chili, garlic, olive oil.", "https://images.unsplash.com/photo-1563379926898-05f4575a45d8?auto=format&fit=crop&w=900&q=80", false, 620, 1);
        singlePrice(pastas, "Chicken Bolognese", "Slow-cooked chicken ragù, twirled through pasta.", "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?auto=format&fit=crop&w=900&q=80", false, 620, 2);
        singlePrice(pastas, "Creamy Mushroom", "Mixed mushrooms in a slow cream sauce.", "https://images.unsplash.com/photo-1473093226795-af9932fe5856?auto=format&fit=crop&w=900&q=80", false, 620, 3);
        singlePrice(pastas, "Chicken Mushroom", "Chicken and mushroom folded through pasta.", "https://images.unsplash.com/photo-1551183053-bf91a1d81141?auto=format&fit=crop&w=900&q=80", false, 620, 4);
        singlePrice(pastas, "Chef Special Chicken", "Chef's rotating pasta of the week.", "https://images.unsplash.com/photo-1608219992759-35c37bef1b3e?auto=format&fit=crop&w=900&q=80", false, 560, 5);
        singlePrice(pastas, "Tomato & Herbs", "Bright tomato sauce with fresh basil.", "https://images.unsplash.com/photo-1598866594230-a7c12756260f?auto=format&fit=crop&w=900&q=80", false, 500, 6);

        // 16. Salads
        MenuCategory salads = category("Salads", MenuGroup.FOOD, 3, "Crisp bowls built for a bright afternoon.", "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(salads, "Chinese Chicken", "Crisp greens, sesame-soy dressing, chicken.", "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?auto=format&fit=crop&w=900&q=80", false, 530, 0);
        singlePrice(salads, "Chicken Cashewnut", "Chicken, cashews, greens, honey-mustard.", "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?auto=format&fit=crop&w=900&q=80", false, 530, 1);
        singlePrice(salads, "CBTL Caesar Salad", "Cos lettuce, parmesan, house Caesar dressing.", "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=900&q=80", false, 510, 2);

        // 17. Wraps
        MenuCategory wraps = category("Wraps", MenuGroup.FOOD, 4, "Warm flatbreads rolled around what we love best.", "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(wraps, "Beef Wrap", "Slow-cooked beef, greens, tortilla.", "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?auto=format&fit=crop&w=900&q=80", false, 580, 0);
        singlePrice(wraps, "Tuna Wrap", "Tuna, sweetcorn, and crunch.", "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?auto=format&fit=crop&w=900&q=80", false, 560, 1);
        singlePrice(wraps, "Tandoori Chicken Wrap", "Yogurt-marinated chicken, mint yogurt.", "https://images.unsplash.com/photo-1600335895229-6e75511892c8?auto=format&fit=crop&w=900&q=80", false, 540, 2);
        singlePrice(wraps, "Chicken Arabiata Wrap", "Spiced tomato chicken in flatbread.", "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?auto=format&fit=crop&w=900&q=80", false, 540, 3);
        singlePrice(wraps, "Rosemary Chicken Wrap", "Rosemary-roasted chicken, garlic mayo.", "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?auto=format&fit=crop&w=900&q=80", false, 540, 4);
        singlePrice(wraps, "Curry Chicken Wrap", "Mild curry chicken, cool cucumber.", "https://images.unsplash.com/photo-1600335895229-6e75511892c8?auto=format&fit=crop&w=900&q=80", false, 540, 5);
        singlePrice(wraps, "Vegetarian Wrap", "Roasted vegetables, hummus, greens.", "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?auto=format&fit=crop&w=900&q=80", false, 400, 6);

        // 18. Sandwiches
        MenuCategory sandwiches = category("Sandwiches", MenuGroup.FOOD, 5, "Considered fillings between careful bread.", "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(sandwiches, "Rosemary Chicken Sandwich", "Slow-roasted chicken, garlic aioli, sourdough.", "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?auto=format&fit=crop&w=900&q=80", false, 710, 0);
        singlePrice(sandwiches, "Bagel, Cream Cheese & Smoked Salmon", "Toasted bagel, cream cheese, salmon, dill.", "https://images.unsplash.com/photo-1592767830261-59d51e0e2b74?auto=format&fit=crop&w=900&q=80", false, 710, 1);
        singlePrice(sandwiches, "Smoked Salmon Sandwich", "Smoked salmon, cucumber, cream cheese.", "https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=900&q=80", false, 560, 2);
        singlePrice(sandwiches, "Tuna Sandwich", "Tuna mayo, red onion, crisp lettuce.", "https://images.unsplash.com/photo-1567234669003-dce7a7a88821?auto=format&fit=crop&w=900&q=80", false, 530, 3);
        singlePrice(sandwiches, "Curry Chicken Sandwich", "Mild curry chicken salad on soft bread.", "https://images.unsplash.com/photo-1539252554453-80ab65ce3586?auto=format&fit=crop&w=900&q=80", false, 530, 4);
        singlePrice(sandwiches, "Tandoori Sandwich", "Tandoori chicken, mint yogurt, greens.", "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?auto=format&fit=crop&w=900&q=80", false, 530, 5);
        singlePrice(sandwiches, "Cheese & Tomato", "Aged cheddar, ripe tomato, toasted bread.", "https://images.unsplash.com/photo-1539252554453-80ab65ce3586?auto=format&fit=crop&w=900&q=80", false, 370, 6);

        // 19. Pizza
        MenuCategory pizza = category("Pizza", MenuGroup.FOOD, 6, "Detroit-style — thick, honest, edge-crisp.", "https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(pizza, "Tandoori Chicken Pizza", "Detroit-style, tandoori chicken, red onion.", "https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=900&q=80", false, 420, 0);
        singlePrice(pizza, "Curry Chicken Pizza", "Detroit-style, mild curry chicken.", "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=900&q=80", false, 420, 1);

        // 20. Soup
        MenuCategory soup = category("Soup", MenuGroup.FOOD, 7, "A quiet bowl to steady the day.", "https://images.unsplash.com/photo-1603105037880-880cd4edfb0d?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(soup, "Chicken Mushroom Soup", "Slow-simmered chicken and mushrooms.", "https://images.unsplash.com/photo-1547592180-85f173990554?auto=format&fit=crop&w=900&q=80", false, 520, 0);
        singlePrice(soup, "Cream of Tomato Soup", "Roasted tomato, cream, basil oil.", "https://images.unsplash.com/photo-1603105037880-880cd4edfb0d?auto=format&fit=crop&w=900&q=80", false, 400, 1);

        // 21. Light Bites
        MenuCategory lightBites = category("Light Bites", MenuGroup.FOOD, 8, "Small pastries for small hungers.", "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(lightBites, "Tandoori Puff", "Buttery puff, spiced tandoori chicken.", "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=900&q=80", false, 185, 0);
        singlePrice(lightBites, "Chicken Curry Puff", "Mildly spiced chicken curry in puff pastry.", "https://images.unsplash.com/photo-1626074353765-517a681e40be?auto=format&fit=crop&w=900&q=80", false, 185, 1);
        singlePrice(lightBites, "Tuna Puff", "Tuna and sweet corn baked in pastry.", "https://images.unsplash.com/photo-1619740455993-9d77a82c8556?auto=format&fit=crop&w=900&q=80", false, 185, 2);
        singlePrice(lightBites, "Egg & Spinach Puff", "Egg and wilted spinach in puff.", "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=900&q=80", false, 175, 3);
        singlePrice(lightBites, "Mushroom Puff", "Mushrooms and herbs in golden pastry.", "https://images.unsplash.com/photo-1626074353765-517a681e40be?auto=format&fit=crop&w=900&q=80", false, 175, 4);

        // 22. Burgers
        MenuCategory burgers = category("Burgers", MenuGroup.FOOD, 9, "Stacked, sauced, and pressed to order.", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(burgers, "CBTL Beef Burger", "Ground beef, cheddar, house sauce, brioche bun.", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=900&q=80", false, 660, 0);
        singlePrice(burgers, "CBTL Chicken Burger", "Crisp chicken, slaw, brioche bun.", "https://images.unsplash.com/photo-1550317138-10000687a72b?auto=format&fit=crop&w=900&q=80", false, 580, 1);

        // 23. Add-Ons & Toppings
        MenuCategory addOns = category("Add-Ons & Toppings", MenuGroup.FOOD, 10, "A little something extra — because sometimes you want more.", "https://images.unsplash.com/photo-1585032226651-759b368d7246?auto=format&fit=crop&w=900&q=80", order++);
        singlePrice(addOns, "Smoked Salmon", "A generous fold of smoked salmon.", "https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=900&q=80", false, 400, 0);
        singlePrice(addOns, "Fries with 1000 Island (Large)", "Golden fries, house dip.", "https://images.unsplash.com/photo-1585032226651-759b368d7246?auto=format&fit=crop&w=900&q=80", false, 255, 1);
        singlePrice(addOns, "Potato Wedges", "Skin-on wedges, salted crisp.", "https://images.unsplash.com/photo-1541599540903-216a46ca1dc0?auto=format&fit=crop&w=900&q=80", false, 210, 2);
        singlePrice(addOns, "Fries with 1000 Island (Regular)", "Smaller share of golden fries.", "https://images.unsplash.com/photo-1585032226651-759b368d7246?auto=format&fit=crop&w=900&q=80", false, 200, 3);
        singlePrice(addOns, "Tuna", "A scoop of house tuna mix.", "https://images.unsplash.com/photo-1563379926898-05f4575a45d8?auto=format&fit=crop&w=900&q=80", false, 160, 4);
        singlePrice(addOns, "Cream Cheese", "A round of cream cheese.", "https://images.unsplash.com/photo-1607532941433-304659e8198a?auto=format&fit=crop&w=900&q=80", false, 125, 5);
        singlePrice(addOns, "Sausage (2 pcs)", "Two grilled breakfast sausages.", "https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?auto=format&fit=crop&w=900&q=80", false, 115, 6);
        singlePrice(addOns, "Cheese", "A slice of melting cheddar.", "https://images.unsplash.com/photo-1486297678162-eb2a19b0a32d?auto=format&fit=crop&w=900&q=80", false, 100, 7);
        singlePrice(addOns, "Baked Beans", "Slow-baked beans in tomato.", "https://images.unsplash.com/photo-1601000937077-c22c2a8f19c6?auto=format&fit=crop&w=900&q=80", false, 100, 8);
        singlePrice(addOns, "Hash Brown", "Crisp, golden potato patty.", "https://images.unsplash.com/photo-1584278860047-22db9ff82bed?auto=format&fit=crop&w=900&q=80", false, 100, 9);
        singlePrice(addOns, "Chicken", "A portion of roast chicken.", "https://images.unsplash.com/photo-1598103442097-8b74394b95c6?auto=format&fit=crop&w=900&q=80", false, 100, 10);
        singlePrice(addOns, "Egg", "One egg — your way.", "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?auto=format&fit=crop&w=900&q=80", false, 100, 11);
        singlePrice(addOns, "Tomato & Herb Sauce", "House tomato-herb sauce.", "https://images.unsplash.com/photo-1598866594230-a7c12756260f?auto=format&fit=crop&w=900&q=80", false, 100, 12);
        singlePrice(addOns, "Strawberry Topping", "Ripe strawberry compote.", "https://images.unsplash.com/photo-1587393855524-087f83d95c9f?auto=format&fit=crop&w=900&q=80", false, 100, 13);
        singlePrice(addOns, "Blueberry Topping", "Blueberry compote, softly sweet.", "https://images.unsplash.com/photo-1498557850523-fd3d118b962e?auto=format&fit=crop&w=900&q=80", false, 100, 14);
        singlePrice(addOns, "Honey", "A spoon of wildflower honey.", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?auto=format&fit=crop&w=900&q=80", false, 100, 15);
        singlePrice(addOns, "Maple Syrup", "Pure grade-A maple syrup.", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?auto=format&fit=crop&w=900&q=80", false, 100, 16);
    }

    private MenuCategory category(String name, MenuGroup group, int chapterNumber, String description, String heroImageUrl, int displayOrder) {
        MenuCategory c = new MenuCategory();
        c.setName(name);
        c.setGroup(group);
        c.setChapterNumber(chapterNumber);
        c.setDescription(description);
        c.setHeroImageUrl(heroImageUrl);
        c.setDisplayOrder(displayOrder);
        return categoryRepo.save(c);
    }

    private void singlePrice(MenuCategory category, String name, String desc, String imageUrl, boolean isNew, int price, int order) {
        MenuItem item = baseItem(category, name, desc, imageUrl, isNew, order);
        item.setPriceSingle(price);
        itemRepo.save(item);
    }

    private void singleDouble(MenuCategory category, String name, String desc, String imageUrl, boolean isNew, int single, int dbl, int order) {
        MenuItem item = baseItem(category, name, desc, imageUrl, isNew, order);
        item.setPriceSingle(single);
        item.setPriceDouble(dbl);
        itemRepo.save(item);
    }

    private void regLarge(MenuCategory category, String name, String desc, String imageUrl, boolean isNew, int reg, int large, int order) {
        MenuItem item = baseItem(category, name, desc, imageUrl, isNew, order);
        item.setPriceRegular(reg);
        item.setPriceLarge(large);
        itemRepo.save(item);
    }

    private void smallRegLarge(MenuCategory category, String name, String desc, String imageUrl, boolean isNew, int small, int reg, int large, int order) {
        MenuItem item = baseItem(category, name, desc, imageUrl, isNew, order);
        item.setPriceSmall(small);
        item.setPriceRegular(reg);
        item.setPriceLarge(large);
        itemRepo.save(item);
    }

    private MenuItem baseItem(MenuCategory category, String name, String desc, String imageUrl, boolean isNew, int order) {
        MenuItem item = new MenuItem();
        item.setCategory(category);
        item.setName(name);
        item.setDescription(desc);
        item.setImageUrl(imageUrl);
        item.setIsNew(isNew);
        item.setDisplayOrder(order);
        return item;
    }
}