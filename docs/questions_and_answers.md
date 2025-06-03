# ❓ Questions & Answers for Scratch Game Requirements

This document captures key clarifying questions and agreed-upon assumptions regarding the Scratch Game CLI app implementation.

---

### ❓ Assumptions on Optional Fields

**Q: The problem mentions optional fields (like columns, rows, win combinations). Should I handle missing values?**  
**A:** No, throw exception. We assumed config will have minimal required attributes and its value.

---

### ❓ Handling Missing Probabilities

**Q: If `probabilities.standard_symbols` is missing for a cell, should I fall back to `probabilities.standard_symbols[0]`?**  
**A:** Yes.  
**Q: What if `standard_symbols[0]` is also missing?**  
**A:** Use random selection of symbol for the cell.

*note*: `standard_symbols[0][0]` mentioned in the problem statement. Looking at the config json, which is assumed that the `probablities.standard_symbols[0]`. 

---

### ❓ Bonus Symbol Placement

**Q: How many bonus symbols can appear in a matrix?**  
**A:** Exactly one, per assumptions in the description.

**Q: Do bonus symbols have individual cell-wise probabilities?**  
**A:** No. They are selected from a global probability distribution: `bonus_symbols.symbols`.

---

### ❓ Winning Combinations Logic

**Q: If a symbol appears in multiple winning conditions (e.g., row and column), should both apply?**  
**A:** Yes, all applicable win rule should apply based on the group. select one with the highest multiplier win rule per group. 

**Q: If a multiple symbol satisfies same rule, should both apply?**  
**A:** Yes. Apply all matching win combinations. Keep in mind that per group win rule per symbol.

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
