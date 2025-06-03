# ❓ Questions & Answers for Scratch Game Requirements

This document captures key clarifying questions and agreed-upon assumptions regarding the Scratch Game CLI app implementation.

---

### ❓ Assumptions on Optional Fields

**Q: The problem mentions optional fields (like columns, rows, win combinations). Should I handle missing values?**  
**A:** Yes, handle them gracefully. If missing:
- Use default matrix size: `3x3`
- Use default win combinations (e.g., `"same_symbol_3_times"`)

---

### ❓ Handling Missing Probabilities

**Q: If `standard_symbols` is missing for a cell, should I fallback to `standard_symbols[0][0]`?**  
**A:** Yes.  
**Q: What if `standard_symbols[0][0]` is also missing?**  
**A:** You should throw a configuration exception with a clear error message.

---

### ❓ Bonus Symbol Placement

**Q: How many bonus symbols can appear in a matrix?**  
**A:** Exactly one, per assumptions in the description.

**Q: Do bonus symbols have individual cell-wise probabilities?**  
**A:** No. They are selected from a global probability distribution: `bonus_symbols.symbols`.

---

### ❓ Winning Combinations Logic

**Q: If a symbol appears in multiple winning conditions (e.g., row and column), should both apply?**  
**A:** Yes, all applicable multipliers should apply.

**Q: If a symbol satisfies both 3-in-a-row and 4-in-a-row, should both apply?**  
**A:** Yes. Apply all matching win combinations.

---

### ❓ Output Format

**Q: What library should be used for JSON serialization?**  
**A:** You may choose freely. `Jackson` is recommended and used in this project.

---

### ❓ Testing

**Q: Which testing framework should be used?**  
**A:** JUnit 5, Mockito, and PowerMock for mocking.

**Q: How many test cases should be included?**  
**A:** Cover all win logic, bonus logic, invalid config, CLI edge cases.

---

### ❓ CLI Interface

**Q: Should any specific CLI library be used?**  
**A:** No, simple `String[] args` parsing is sufficient.

---

### ❓ Error Handling

**Q: How robust should the error handling be?**  
**A:** Very. Handle invalid config, CLI arguments, missing files.

**Q: Should the application continue or exit on errors?**  
**A:** Exit with a clear log message if unrecoverable.

---

### ❓ Code Style and Readability

**Q: Any specific code style guidelines?**  
**A:** Use clean Java 17 practices, JavaDoc comments, and clear separation of concerns.

---
