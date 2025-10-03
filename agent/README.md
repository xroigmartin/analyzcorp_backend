# 🧠 Agent System 

> The AI Agent is modular and helps with code generation, migrations, testing, security reviews, docs, and PRs.


---

## 🚀 Quick Start

1) **Set project variables**  
   Fill in placeholders in [`agent/variables.md`](./variables.md) to describe this repo (runtime, framework, DB, etc.).

2) **Pick ONE repo profile (backend *or* frontend) and adjust config**  
   Open [`agent/config/agent.config.yaml`](./config/agent.config.yaml) and tune `paths.allow` to your repo type:
   - **Backend (Java)** – keep entries like:
     - `src/main/java/**`, `src/test/java/**`, `src/main/resources/**`
     - keep docs/config: `docs/**`, `README.md`, `CHANGELOG.md`, build files

3) **Understand the agent’s core behavior**  
   Read [`agent/core.md`](./core.md): role, output formats (diff/file/plan+patch/JSON), CLI & IDE rules, exit codes.

4) **Run commands** (CLI/IDE)  
   Examples (adjust to your repo type):
   ```bash
   # Plan a change
   agent plan "Add budget limit"

   # Backend (Java): generate a hexagonal use case
   agent code gen usecase AddTransaction

   # Backend: generate a DB migration
   agent db gen migration V1__add_transactions

   # Contracts: check API backward compatibility
   agent api compat --since v0.1.0
   ```


---

## 🧠 Agent Folder Map

```
agent/
├─ agent.md                  ← Index & navigation
├─ README.md                 ← (this file)
├─ core.md                   ← Core prompt (Section 1)
├─ variables.md              ← Project placeholders
├─ config/
│  └─ agent.config.yaml      ← Paths, language, security, CI policies
├─ policies/
│  ├─ conventions.md         ← Naming, testing, migrations
│  └─ layering.md           ← Hexagonal layering policy
├─ guides/
│  ├─ operational-guide.md   ← Orchestration, command matrix, profiles
│  ├─ compatibility.md
│  ├─ observability.md
│  └─ performance.md
└─ agents/
   ├─ _catalog.md            ← Overview of all sub-agents
   ├─ _template.md          ← Template for new sub-agents
   ├─ code.md, db-migrations.md, testing.md, ...
```


---

## 🧩 Sub‑Agents Overview (quick)

| Agent | Role |
|-------|------|
| **DB + Migrations** | Schema evolution & {DB_MIGRATION_TOOL} migrations |
| **Code** | Language‑agnostic code (Hexagonal + DDD), docs per language |
| **Testing** | TDD/BDD unit & integration tests |
| **Security** | OWASP Top 10 (optionally ASVS) |
| **API & Contract** | OpenAPI/DTO/errors, compat checks |
| **Observability & Logging** | Structured logs, tracing, masking |
| **Performance & Reliability** | Hotspots, indexes, N+1 detection |
| **Docs** | README, CHANGELOG, docs updates |
| **Git & PR** | Conventional commits, PR bodies |

See the [Sub‑Agent Catalog](./agents/_catalog.md) and each detailed page under `agent/agents/` for commands and outputs.


---

## 🛠 Profile Tips (Single‑Function Repo)

- **Backend repo** (Java or Python):
  - Keep only backend `paths.allow` in `agent.config.yaml`.
  - Use `Code Agent` to scaffold hexagonal use cases and adapters.
  - Use `DB + Migrations Agent` if the service has a database.
  - Use `API & Contract Agent` for OpenAPI sync and compat checks.

- **Frontend repo** (if applicable in a different repo):
  - Replace allows with your framework structure and add a `Code Agent` variant file (e.g., `agents/code-frontend.md`) using the provided `agents/_template.md`.
  - Add a testing sub‑agent for your framework (e.g., Jest/Cypress) if needed.

> One repo = one function. Avoid mixing backend and frontend paths or tooling in the same repository.


---

## 📎 Extended Guides

- [Compatibility](./guides/compatibility.md)  
- [Observability](./guides/observability.md)  
- [Performance](./guides/performance.md)  


---

## 📜 License

{LICENSE_INFO}
