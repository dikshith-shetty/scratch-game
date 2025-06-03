# 🔁 Feedback & Iteration Plan for Scratch Game

This document outlines how to collect, evaluate, and act on feedback to iteratively improve the Scratch Game CLI application.

---

## 🎯 Objectives

- Continuously enhance the codebase, documentation, and usability
- Align project improvements with reviewer or stakeholder feedback
- Ensure each iteration is purposeful and test-covered

---

## 🗣️ Feedback Collection Sources

| Source            | Type of Feedback                     |
|-------------------|--------------------------------------|
| Interviewers      | Architecture, clarity, performance   |
| Code reviewers    | Code style, test coverage, patterns  |
| Test users        | Usability, bugs, clarity in outputs  |
| CI/CD pipelines   | Build/test failures, code quality    |

---

## 🔁 Iteration Process

### 1. **Collect**
- Gather feedback from reviewers, test users, or automated tools

### 2. **Categorize**
- Group feedback into:
    - 🔧 Bug fixes
    - ✨ Enhancements
    - 🧪 Test gaps
    - 📚 Documentation

### 3. **Prioritize**
- Urgent: Game-breaking bugs, major misunderstandings
- High: Missing test cases, inconsistent outputs
- Medium: Style, logs, formatting
- Low: Cosmetic documentation issues

### 4. **Plan**
- Create a list of tasks in a TODO or issues file
- Assign milestones or deadlines

### 5. **Implement**
- Apply small atomic commits
- Ensure changes include tests and documentation updates

### 6. **Verify**
- Re-run all tests
- Get re-review or confirm acceptance
- Check logs and output behavior

---

## 📋 Example Iteration Log

| Iteration | Description                       | Status         |
|-----------|-----------------------------------|----------------|
| 1         | config validation                 | ✅ Complete     |
| 2         | Improve matrix win coverage logic | 🚧 In Progress |
| 3         | Refactor test structure           | ⏳ Planned      |


---

## 📌 Notes

- Keep a changelog or commit history
- Make sure bonus logic and output structure are well-tested in each change
- Always validate JSON output after changes

---

## ✅ Best Practices

- Get feedback early, especially on logic and usability
- Don’t iterate without test coverage
- Tie each change back to a documented issue

---
[Previous](07_documentation_plan.md)