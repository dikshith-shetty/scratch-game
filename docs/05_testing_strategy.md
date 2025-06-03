# 🧪 Testing Strategy for Scratch Game

This document outlines the complete testing strategy for the Scratch Game CLI application.

---

## 🎯 Objectives

- Ensure correctness of core game logic (matrix generation, reward calculation, bonus application)
- Verify configuration parsing and validation
- Cover both unit and integration testing
- Maintain high code quality and reliability with automation

---

## 🧱 Test Types

### ✅ Unit Tests

- Focus on individual classes and methods
- Use mocking (Mockito, PowerMock) where dependencies exist
- Fast to execute, isolated, no file I/O

**Examples:**
- `MatrixGeneratorTest`
- `WinEvaluatorTest`
- `ConfigLoaderTest`

### ✅ Integration Tests

- Combine real config with engine execution
- Simulate full end-to-end game flow
- Validate console result correctness

**Examples:**
- `GameEngineIntegrationTest`

---

## 🧪 Tools Used

| Tool        | Purpose                      |
|-------------|------------------------------|
| JUnit 5     | Unit and integration testing |
| Mockito     | Mocking collaborators        |


---

## 🧪 Test Execution

Run all tests using Maven:

```bash
mvn test
```

---

## ✅ Test Coverage Goals

| Component             | Coverage Goal |
|-----------------------|---------------|
| Game Engine           | 100% logic    |
| Config Parser         | 100% fields   |
| Bonus & Win Evaluator | 90%+ branches |
| CLI Handler           | 80%+          |

---

## 🧹 Test Best Practices

- Use descriptive test method names
- Use `@BeforeEach` to initialize shared setup
- Avoid depending on random output in tests — inject mock `Random`
- Validate both success and edge cases

---

## 🧪 CI Readiness (Optional)

To integrate into CI:

```bash
mvn clean verify
```

Consider adding plugins:
- `jacoco-maven-plugin` for test coverage
- `surefire-report` for HTML test reports

---

## 📁 Test File Layout

```
src/
├── main/java/com/scratchgame/...
└── test/java/com/scratchgame/
    ├── core/
    │   ├── winrule/WinRuleEvaluatorTest.java 
    │   ├── GameEngineTest.java
    │   ├── MatrixGeneratorTest.java
    │   ├── RewardCalculatorTest.java
    │   └── ...
    └── integration/
        ├── GameEngineIntegrationTest.java
        └── ...    
    
```

---

## 📌 Notes

- Prefer dependency injection over static references
- Use fixed seeds in Random or stub values in mocks
- Keep test data small but meaningful

---
[Previous](./04_implementation_plan.md) | [Next](06_packaging_deployment.md)