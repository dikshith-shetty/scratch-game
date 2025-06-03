# Concurrency Design

## Objective

This document outlines concurrency opportunities, design patterns, and safety considerations for optimizing performance in the Scratch Game application.

---

## 1. Design Goals

- Improve performance during win rule evaluation and matrix generation.
- Maintain deterministic behavior and thread safety.
- Support scalability for large board sizes or batch simulations.

---

## 2. Concurrency Opportunities

| Component           | Can Be Parallelized? | Justification                                               |
|---------------------|----------------------|-------------------------------------------------------------|
| Matrix Generation   | ✅ Yes                | Each cell generation is independent; ideal for parallelism  |
| Win Rule Evaluation | ✅ Yes                | Each rule is evaluated independently using strategy pattern |
| Bonus Application   | ❌ No                 | Only one bonus is applied post rule-evaluation              |
| Config Loading      | ❌ No                 | Happens once and is read-only                               |

---

## 3. Parallel Matrix Generation

Implemented using `IntStream.range().parallel()`:

```java
IntStream.range(0, rows).parallel().forEach(r -> {
    for (int c = 0; c < cols; c++) {
        matrix[r][c] = pickRandomSymbol(config, r, c);
    }
});
```

- Uses `ThreadLocalRandom` for safe concurrent access.
- Requires config to be immutable or read-only.

---

## 4. Parallel Win Rule Evaluation

Implemented using `parallelStream()` in `WinRuleEngine`:

```java
return rules.parallelStream()
    .map(rule -> evaluateRule(matrix, rule))
    .flatMap(Optional::stream)
    .collect(Collectors.toList());
```

- `WinRuleEvaluator` implementations must be stateless.
- Evaluation results are collected safely via streams.

---

## 5. Thread Safety Practices

- Use `ThreadLocalRandom` over shared `Random`.
- Avoid mutable shared state across threads.
- Loggers are per-class using `java.util.logging` (thread-safe by default).

---

## 6. Future Enhancements

| Feature                  | Benefit                              |
|--------------------------|--------------------------------------|
| Custom `ExecutorService` | Thread control and tuning            |
| ForkJoinPool tuning      | Better resource utilization          |
| Batch simulations        | Multiple games evaluated in parallel |
| CompletableFuture chains | Asynchronous matrix → rule → reward  |

---

## 7. Notes

- Parallelism is optional and configurable.
- Defaults to sequential for small board sizes (≤ 4×4).