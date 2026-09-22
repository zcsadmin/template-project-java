# AGENTS.md - Template Project Java

## Project Overview
ZCS bootstrap template for new Java apps. Single Maven module in `app/`, Docker-based dev workflow. Lombok, SLF4J/Logback, JUnit 5. Compiles to Java 17 bytecode (`maven.compiler.source/target` in pom) but runs on `zcscompany/java:25-*` base images.

## Template Bootstrap
This repo is a template, not an app. Starting a new project means renaming these placeholders:
- `app/pom.xml`: `groupId`, `artifactId`, `name`, `description`
- Base Java package `dev.zcscloud.ml.tpj.app` ("tpj" = template project java) — rename folders under `app/src/main/java` and `app/src/test/java`
- `docker-compose.yml`: `APP_NAME`
- `README.md`: title
The README "How to start" section documents the full flow (clone → reset git → rename → `./run.sh`).

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
- Environment variables take precedence over property files (`Config.java`); `app.{env}.properties` is consulted first when `APP_ENVIRONMENT` is set
- Only the default `app.properties` ships in this repo (compose sets `APP_ENVIRONMENT=local`, so the env-specific lookup silently falls back)
- `app.properties` is Maven-filtered at build: `app.version=${project.version}` is substituted into the JAR resources
- Env vars set by `docker-compose.yml`: `APP_NAME`, `APP_PORT`, `APP_ENVIRONMENT`
- Logback levels: `APP_LOG_LEVEL` (zcsapp logger), `JAVA_LOG_LEVEL` (root) — defaults `INFO`

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
