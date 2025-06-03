# Implementation Plan

This document outlines the structured and phased plan for implementing the Scratch Game application based on the finalized low-level and high-level design.

---

## 1. Objective

Provide a clear roadmap and modular breakdown of the development activities. This plan will ensure maintainability, testability, and alignment with the design artifacts.

---

## 2. Environment Setup

- **Language**: Java 17
- **Build Tool**: Maven
- **Libraries**:
  - Jackson (for JSON serialization/deserialization)
  - JUnit 5 (for unit testing)
  - JaCoCo (for code coverage)
  - JMH (for micro-benchmarks)
  - lombok (for boiler plate code generation)
- **Printing**: Java `System.out.println`

---

## 3. Directory Structure

```plaintext
scratch-game/
├── src/
│   ├── main/
│   │   └── java/com/scratchgame/
│   │   
│   └── test/
│       ├── java/com/scratchgame/
│       └── resources/
├── pom.xml
├── README.md
├── SSD.md
└── docs/
    ├── diagrams/
    └── concurrency_design.md
    .
    .
    .
```

---

## 4. Module-by-Module Implementation Plan

### Phase 1: Core Models & Config

- [ ] `Symbol`, `StandardSymbol`, `BonusSymbol`
- [ ] `SymbolFactory`
- [ ] `Config` classes (mapped from JSON)
- [ ] Config loader utility

### Phase 2: Matrix and Rules

- [ ] `MatrixGenerator`
- [ ] `WinRule` and `WinRuleEvaluator` interface
- [ ] At least one rule evaluator (e.g., `SameSymbolRuleEvaluator`)

### Phase 3: Game Logic

- [ ] `RewardEngine` with rule evaluation and bonus strategy
- [ ] `BonusStrategy`, `BonusStrategyFactory`
- [ ] `GameEngine` to orchestrate the game

### Phase 4: CLI & Integration

- [ ] CLI Main Runner
- [ ] Parse arguments, read config, run game

---

## 5. Testing Strategy

- JUnit 5 for all modules
- Use mocks where appropriate
- Test config scenarios (valid, invalid, edge cases)
- Code coverage target: 80% minimum (JaCoCo)

---

## 6. Logging Plan

- Use `java.util.logging.Logger` in:
  - ConfigLoader
  - MatrixGenerator
  - RewardEngine
  - GameEngine
- Log startup, errors, game results

---

## 7. Additional Tasks

- [ ] Maven build with shaded jar (`maven-shade-plugin`)
- [ ] Generate test coverage report
- [ ] Prepare README instructions
- [ ] Track performance benchmark with JMH (optional)

---

## 8. Documentation Sync

- Update SSD and README as new classes and logic are implemented
- All design and implementation assumptions must be reflected in the docs

---
[Previous](./03_low_level_design.md) | [Next](./05_testing_strategy.md)