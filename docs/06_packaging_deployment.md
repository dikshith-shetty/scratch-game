# 📦 Packaging and Deployment Guide for Scratch Game

This document describes how to build, package, and deploy the Scratch Game CLI application using Java 17 and Maven.

---

## 🧱 Build Tool

- **Language**: Java 17
- **Build System**: Maven 3.9.9
- **Packaging Type**: Fat JAR (Uber JAR)
- **Plugin Used**: `maven-shade-plugin`

---

## 🛠️ Build & Package Instructions

Ensure you have Java 17 and Maven 3.9.9 installed.

### 1. Clean and Build

```bash
mvn clean package
```

This command will:
- Compile Java code
- Run all unit and integration tests
- Package the application into a single executable JAR

### 2. Output

The packaged JAR will be available at:

```
target/scratch-game-1.0.0.jar # Normal jar
target/scratch-game.jar # Uber/Fat jar which inclued all the dependencies. use this to run the cli
```

---

## 🚀 Running the Application

Use the following command:

```bash
java -jar target/scratch-game.jar --config path/to/config.json --betting-amount 100
```

You must supply:
- `--config`: path to the config file (e.g. `src/test/resources/config.json`)
- `--betting-amount`: amount to bet on the scratch

---

## 🔁 Deployment Options

### 🖥️ Local Machine

1. Build the JAR as above
2. Copy it to your target machine
3. Run using `java -jar`

---

### 💻 CI/CD Pipelines

Integrate into CI/CD using GitHub Actions, GitLab CI, Jenkins, or similar:

```yaml
- name: Build with Maven
  run: mvn clean package

- name: Run Scratch Game
  run: java -jar target/scratch-game.jar --config config.json --betting-amount 50
```

---

### 📦 Docker (Optional)

Create a Docker image to package the app:

```dockerfile
FROM eclipse-temurin:17-jdk
COPY target/scratch-game.jar app.jar
# to copy config file to container image
COPY src/test/resources/config-3x3.json config.json 

ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:

```bash
mvn clean package
docker build -t scratch-game .
docker run --rm scratch-game --config src/test/resources/config.json --betting-amount 100
# or to give local file to running container
docker run --rm -v /absolute/path/to/config.json:/app/config-xyz.json scratch-game --config /app/config.json --betting-amount 100 
```

---

## 🧪 Post-Deployment Verification

- Ensure correct matrix output
- Validate logs are generated
- Confirm reward and bonus logic works as expected

---

## 📌 Notes

- Ensure `config.json` is always available at runtime
- For consistent outputs in CI, fix random seed or use mocks in tests

---
[Previous](05_testing_strategy.md) | [Next](07_documentation_plan.md)