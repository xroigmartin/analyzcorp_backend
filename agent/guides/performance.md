# Performance & Reliability

- Budgets: SQL p95 < 50ms (local), REST p95 < 200ms.
- Prefer indexes to improve selectivity; avoid full scans on large tables.
- Detect/prevent N+1 queries; batch or prefetch relationships when appropriate.
- Apply timeouts, retries (bounded), and backpressure strategies for remote calls.
