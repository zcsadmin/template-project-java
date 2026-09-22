# AGENTS.md - Template Project Java

## Project Overview
Java 17 Maven project with Docker-based development workflow. Uses SLF4J/Logback, Lombok, JUnit 5.

## Key Commands

### Via `run.sh` (recommended)
```bash
./run.sh              # Run app (default)
./run.sh -t           # Run tests
./run.sh -d           # Run in debug mode (port 5005)
./run.sh -s           # Shell into container
./run.sh -i           # Build dist Docker image
./run.sh -c           # Stop docker environment
./run.sh -b           # Run in background
./run.sh -v           # Verbose output
```

### Direct Maven (inside container)
```bash
mvn clean test                    # Run tests
mvn clean compile exec:java       # Run app
mvn clean package                 # Build fat JAR (shade plugin)
mvn versions:display-dependency-updates
mvn versions:display-plugin-updates
```

## Project Structure
```
app/                    # Maven module
  src/main/java/.../App.java         # Entry point (dev.zcscloud.ml.tpj.app.App)
  src/main/java/.../config/          # Config loading (env vars + properties)
  src/main/resources/app.properties  # Default config
  src/test/.../AppTest.java          # JUnit 5 tests
  pom.xml                            # Maven config
```

## Configuration
- Environment variables take precedence over `app.properties`
- Environment-specific configs: `app.{env}.properties` (e.g., `app.local.properties`)
- Key env vars: `APP_NAME`, `APP_VERSION`, `APP_PORT`, `APP_ENVIRONMENT`, `APP_LOG_LEVEL`

## Docker Workflow
- `docker-compose.yml` defines `app` service (dev target)
- Multi-stage Dockerfile: `dev` → `package` → `dist`
- Source mounted at `/app`, Maven cache at `/home/bob/.m2`
- Debug port 5005 exposed via `DEBUG_OPTS`

## Testing
- JUnit 5 (`junit-jupiter-api`)
- Run single test: `mvn test -Dtest=AppTest#shouldAnswerWithTrue`

## Build Artifacts
- Fat JAR at `app/target/app.jar` (shade plugin, main class: `dev.zcscloud.ml.tpj.app.App`)
- Dist image: `docker build -f .build/dockerfiles/Dockerfile -t dist .` (target: dist)

## VSCode Debug
Create `.vscode/launch.json` with attach config (port 5005), then `./run.sh -d`
