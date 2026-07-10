# The Coffee Bean & Tea Leaf — Backend

A ready-to-run Spring Boot backend matching the `api.ts` contract of your Lovable frontend.

## What's included
- Full menu (Food + Drinks, all categories/items/prices) auto-seeded on startup
- Reservation create / list-by-range / delete / status update
- Admin menu CRUD (add / edit / delete items)
- Daily scheduled job that purges reservations older than 30 days
- CORS wide open for easy testing (tighten before going live - see `CorsConfig.java`)
- Advanced **Google Gemini AI Chatbot** integration (`gemini-2.5-flash` model grounded directly to the live database menu, budget filtering rules, and support for English/Bangla/Banglish queries)
- **PostgreSQL by default** (an H2 fallback config is included, commented out in `application.yaml`, for quick tests without Postgres running)

## Requirements
- Java 21+
- Gradle (runs out of the box using the included `./gradlew` wrapper)
- PostgreSQL installed and running locally on port 5432
- A valid Google Gemini API Key

---

## 1. Installing & setting up PostgreSQL on Mac

**Option A — Homebrew (recommended):**
```bash
brew install postgresql@16
brew services start postgresql@16
