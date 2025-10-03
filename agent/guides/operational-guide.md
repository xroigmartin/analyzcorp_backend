# Operational Guide

## 4.1 Orchestration Flow
Typical sequence when implementing a new feature or reviewing a change:

1. **DB + Migrations Agent** — Plan and generate database changes (schema/migrations).
2. **Code Agent** — Implement domain, application, and adapter code.
3. **Testing Agent** — Add/update unit and integration tests (TDD/BDD).
4. **Security Agent** — Review changes against OWASP Top 10 and patch if needed.
5. **API & Contract Agent** — Sync and validate API contracts.
6. **Observability & Logging Agent** — Review logs, tracing, and masking setup.
7. **Performance & Reliability Agent** — Review queries, indexes, and runtime budgets.
8. **Docs Agent** — Update README, CHANGELOG, and relevant docs.
9. **Git & PR Agent** — Generate conventional commits and PR body.

> This flow can be automated (CI pipeline) or executed manually using the CLI/IDE integration.

## 4.2 Command Matrix

| Command | Purpose | Output | Notes |
|---------|---------|--------|-------|
| `agent plan <goal>` | High-level plan | plan+patch | Never writes files |
| `agent db gen migration <name>` | Generate DB migration | file | Uses naming policy |
| `agent code gen usecase <Name>` | Generate hexagonal use case | files | Language autodetected |
| `agent test fix` | Diagnose & patch tests | diff | Honors coverage thresholds |
| `agent sec review <scope>` | OWASP review | report+diff | Fails on secrets |
| `agent api compat --since vX.Y.Z` | Backward compatibility check | report | API changes summary |
| `agent obs review` | Observability/logging review | patch+report | Checks masking & IDs |
| `agent perf plan` | Performance/index suggestions | plan+patch | Detects N+1, slow queries |
| `agent docs changelog --since vX.Y.Z` | Generate changelog | file | Conventional commits |
| `agent pr create` | Generate PR body | markdown file | Requires Git context |

## 4.3 Execution Profiles
- **local** — Verbose logs, dry-run enabled by default, relaxed checks.  
- **ci** — Strict policy checks, coverage enforcement, deny-list enforced.  
- **strict** — Deny-list + JSON output required + policy enforcement on all layers.

## 4.4 Backward Compatibility Checklist
See detailed checklist: [compatibility.md](./compatibility.md).

## 4.5 Observability Guidelines
See detailed guidelines: [observability.md](./observability.md).

## 4.6 Performance Budgets
See detailed budgets & practices: [performance.md](./performance.md).

## 4.7 Concurrency Control
- Use `.agent.lock` to prevent multiple agents from writing patches simultaneously in CI.
- Agents should fail gracefully if another process holds the lock.

## 4.8 Known Limitations
- Agents do not execute migrations or run tests unless explicitly allowed.
- No external network calls by default.
- Cannot modify files outside `paths.allow` in `{CONFIG_FILE}`.
- Language detection may need hints if multiple runtimes coexist.
