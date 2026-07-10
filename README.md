# The Coffee Bean & Tea Leaf — Backend

A ready-to-run Spring Boot backend matching the api.ts contract of your Lovable frontend.

## What's included
- Full menu (Food + Drinks, all categories/items/prices) auto-seeded on startup
- Reservation create / list-by-range / delete / status update
- Admin menu CRUD (add / edit / delete items)
- Daily scheduled job that purges reservations older than 30 days
- CORS wide open for easy testing (tighten before going live - see `CorsConfig.java`)
- Simple keyword-based chatbot endpoint (swap for a real LLM call later)
- **PostgreSQL by default** (an H2 fallback config is included, commented out in `application.yaml`, for quick tests without Postgres running)

## Requirements
- Java 17+
- Gradle (or use `./gradlew` if you generate a wrapper — otherwise `gradle` on your PATH)
- PostgreSQL installed and running locally (see setup below)

---

## 1. Installing & setting up PostgreSQL on Mac

**Option A — Homebrew (recommended):**
```bash
brew install postgresql@16
brew services start postgresql@16
```
This installs Postgres and starts it running in the background (auto-starts on login too).

**Option B — Postgres.app:** download from https://postgresapp.com, drag to Applications, open it, click "Initialize" — gives you a GUI to start/stop the server and a built-in `psql` in your PATH once you follow their one-line PATH setup step.

### Create the database and a user (Terminal / cmd)
Once Postgres is running, open Terminal:
```bash
# connect to the default postgres database as your Mac user
psql postgres

# inside the psql prompt, run:
CREATE DATABASE cbtldb;
CREATE USER cbtl_user WITH PASSWORD 'yourpassword';
GRANT ALL PRIVILEGES ON DATABASE cbtldb TO cbtl_user;
\q
```
(If `psql postgres` complains about role/user not existing, try `psql -U postgres` instead, or — with Homebrew's default setup — just `psql -d postgres` using your Mac username, which Homebrew grants superuser access to by default.)

### Update `application.yaml` with your real credentials
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cbtldb
    username: cbtl_user
    password: yourpassword
```
(Or use `postgres`/no extra user at all if you didn't create a separate one — just match whatever you actually created above.)

### Verify Postgres is reachable any time
```bash
psql -h localhost -U cbtl_user -d cbtldb
# should drop you into a cbtldb=> prompt if everything's connected correctly
```

---

## 2. Setting this project up in IntelliJ IDEA

1. **Open the project**: `File → Open...` and select the unzipped `cbtl-backend` folder (the one containing `build.gradle`). IntelliJ will detect it as a Gradle project and auto-import — wait for the Gradle sync to finish (progress bar bottom-right).
2. **Check the JDK**: `File → Project Structure → Project` — set the Project SDK to Java 17 (or newer). If you don't have one listed, click "Add SDK → Download JDK" and grab 17 directly from IntelliJ.
3. **Confirm Lombok works**: IntelliJ usually prompts to install the Lombok plugin automatically since it detects the dependency — accept that prompt (`Settings → Plugins → search "Lombok" → Install` if it doesn't prompt). Also enable annotation processing: `Settings → Build, Execution, Deployment → Compiler → Annotation Processors → check "Enable annotation processing"`.
4. **Set up the Postgres connection inside IntelliJ (optional but handy)**: open the **Database** tool window (`View → Tool Windows → Database`), click `+ → Data Source → PostgreSQL`, fill in host `localhost`, port `5432`, database `cbtldb`, user/password matching what you created above, test the connection, then you can browse tables directly inside IntelliJ once the app has run once and created them.
5. **Run the app**: open `CbtlApplication.java`, click the green ▶ run icon next to the `main` method (or right-click the file → Run). IntelliJ builds and starts it — watch the Run panel for `Started CbtlApplication` and `Tomcat started on port 8080`.
6. **If it fails to connect to Postgres**: double-check Postgres is actually running (`brew services list` should show `postgresql@16` as `started`), and that the URL/username/password in `application.yaml` exactly match what you created in step 1.

## Run it from the terminal instead (no IntelliJ)
```bash
cd cbtl-backend
gradle bootRun
```
The API starts on **http://localhost:8080**. The menu is seeded automatically the first time it starts (only if the categories table is empty), directly into your real Postgres database — so it'll persist across restarts, unlike the old H2 setup.

## Try it
```bash
curl "http://localhost:8080/api/menu/categories?group=drinks"
curl "http://localhost:8080/api/menu/items?categoryId=1"

curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","phone":"+8801XXXXXXXXX","reservationDate":"2026-07-10","reservationTime":"19:30","guests":2}'

curl "http://localhost:8080/api/reservations?range=7"
```

## Reverting to H2 for a quick local test (no Postgres needed)
1. In `build.gradle`, you can leave both dependencies as-is (H2 is already included alongside Postgres).
2. In `application.yaml`, comment out the PostgreSQL `spring:` block at the top and uncomment the H2 `spring:` block at the bottom.
3. Run it — no database setup needed, data resets on every restart.

## Connecting to your Lovable frontend
1. Deploy this backend somewhere reachable (Render, Railway, Fly.io, a VPS, etc.) and note the live URL.
2. In your Lovable project's `services/api.ts`, point `API_BASE_URL` at that live URL and replace the mock functions with real `fetch` calls to the endpoints below.
3. Update `CorsConfig.java`'s `allowedOriginPatterns` to your real frontend domain once you're done testing with `"*"`.

## Endpoint summary
| Method | Path | Purpose |
|---|---|---|
| GET | `/api/menu/categories?group=food\|drinks` | list categories for a group |
| GET | `/api/menu/items?categoryId=` | list items in a category |
| GET | `/api/menu/items` | list all items |
| POST | `/api/menu/items` | (admin) create item |
| PUT | `/api/menu/items/{id}` | (admin) update item |
| DELETE | `/api/menu/items/{id}` | (admin) delete item |
| POST | `/api/reservations` | create a reservation |
| GET | `/api/reservations?range=today\|yesterday\|3\|7\|15\|30` | (admin) list reservations by range |
| DELETE | `/api/reservations/{id}` | (admin) delete a reservation |
| PATCH | `/api/reservations/{id}/status` | (admin) update status |
| POST | `/api/chat` | chatbot reply |

See `CBTL-Backend-Guideline.md` (the doc from our planning conversation) for full field-by-field JSON shapes and the reasoning behind each design choice.
