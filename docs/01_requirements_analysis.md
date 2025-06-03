# 1. Requirements Analysis

This section describes the core problem to be solved, outlines the assumptions, and defines the functional and non-functional requirements for the Scratch Game CLI application.

[Problem_description](../assignment/problem_description.md)

---

## 📌 Problem Statement

Develop a CLI-based scratch game that:
- Reads a configuration file (`config.json`) defining symbols, win rules, and probabilities.
- Accepts a betting amount input via CLI.
- Generates a matrix (n × m) filled with standard symbols based cell probabilities mentioned in config.
- Injects exactly one bonus symbol at a random cell in the matrix.
- Evaluates the matrix based on win rules and calculates a reward.
- Print the result in the console containing the matrix, applied winning rules, bonus symbol, and total reward in the json string format.

---
## 🤔 Questions Asked and Clarified 
Note: These are the questions I had during the requirement analysis. I couldn't connect with the moderator, 
so I have assumed the answers using my best of my knowledge and interpretation of the problem. Development is made based on these answers.
[please look this link for detailed question discussion and derided assumptions](questions_and_answers.md)

1. Should we support only square matrices? → No, support non-square `(n × m)` matrices with `n >= 1` and `m >= 3`.
1. Is the config file guaranteed to be valid? → Yes, all required configs will be present without fail.
1. Can the standard symbol be absent → No, at least 1 will be defined.
1. Can the bonus symbol be absent? → No, at least 1 will be defined.
1. Are more win rules expected in the future? → Yes, the system should support it.
1. How many bonus symbols should be injected? → Exactly one.
1. Is betting amount a float? → Yes, we use `double`.
1. Do symbols place by weightage in the matrix → No, symbol weights are not mentioned in the config with respect to full matrix, so no weights are considered.
1. What to do if Symbol cell probability and/or default cell probability and/or Bonus symbol probability is not defined in config?  → Symbol is selected randomly.  

---
## ✅ Functional Requirements

- Accept config file and betting amount from CLI as a parameter.
- Read and parse `config.json`.
- Generate a scratch matrix of configurable size.
- Apply pseudo-random selection using defined probabilities to fill symbols.
- Inject a single bonus symbol into the matrix. only one bonus symbol is allowed in the matrix.
- Evaluate winning conditions.
- Apply bonus logic only if a win occurs.
- Output the full result including matrix, win details, and reward in json string to the console.

---

## ❌ Out of Scope

- GUI or web interface.
- Multiplayer support.
- Persistence/database storage.
- Min-Max Optimization 
- External API integrations.

---

## 🔧 Assumptions

1. Betting amount should be whole i.e `Integer`. This assumed by looking into given examples.
1. Configuration file is **always a valid json**.
1. The scratch matrix can be non-square (`n × m`).
1. Matrix is generated pseudo-randomly, and the symbol for each cell is determined independently, using symbol probabilities. if the probabilities not present we consider standard_symbols[0] probabilities values are default. If that also not present, we select symbol by randomly. 
1. Bonus symbol always presents and is placed **after** the matrix is generated and placed at exactly one random position in the matrix based on the given probability or randomly selecting on if probability not present.
1. Bonus symbol placement is independent of weight.
1. At least one win rule will be presented in config. Win rules may expand in the future — the system is expected to be extensible.

---

## 📥 Input Specification

- `--config <path>`: Path to config JSON file.
- `--betting-amount <value>`: User-specified amount (integer).

 
Note: look out config.json section of the [problem statement](../assignment/problem_description.md) for clear spec of config file or [Config schema file](config_schema.json)

---

## 📤 Output Specification

- Printed output JSON to the console:
  - The final matrix.
  - Winning combination(s), if any.
  - Bonus symbol used.
  - Calculated reward.

Note: look out output section of the [problem statement](../assignment/problem_description.md) for output json string.

---

## 📈 Non-Functional Requirements

- Must run via command line.
- Should handle large matrices (e.g., 5×5 or more) efficiently.
- Maintain modular and testable code structure.
- Output should be human-readable json string.

---

## 🛠 Technology Stack

- Java 17
- Maven 
- Jackson (for JSON processing)
- JUnit, Mockito (for unit testing)
- lombok (to avoid boilerplate code)

---

[Previous](../SSD.md) | [Next](02_high_level_design.md)
