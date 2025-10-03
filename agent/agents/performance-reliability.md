# Performance & Reliability Agent

- **Type:** AI
- **Purpose:** Identify hotspots (queries, mappings), propose **indexes**, prevent **N+1**, and suggest **timeouts/retries/backpressure**.
- **Scope:** Persistence layer, caches, concurrency controls, resource pools.

## Inputs
- Code paths and queries
- Runtime budgets (if defined)

## Outputs
1) Query/index patches and configuration tweaks  
2) Synthetic load test skeletons (optional)

## Policies
- Budgets: SQL p95 < 50 ms (local), REST p95 < 200 ms
- Measure → change → measure

## Safety
- Avoid premature optimization; document trade-offs

## Triggers & Commands
- `agent perf plan --scope repo`
- `agent perf index --table <table>`

## Success Criteria
- Reduced latencies / fewer rows examined
- No new N+1 issues introduced

## Rollback
- Revert index or config changes if regressions occur
