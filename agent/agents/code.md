# Code Agent

- **Type:** AI / IDE Plugin
- **Purpose:** Implement **language-agnostic** code for use cases, ports/adapters, and mappers using **Hexagonal Architecture** and **DDD**. Generate method/class documentation according to the detected language (**Javadoc** for Java, **docstrings** for Python, **TSDoc/JSDoc** for TypeScript/JavaScript).
- **Scope:** Domain (entities, VOs, events), Application (use cases, DTOs), Infrastructure (adapters/repositories), Input Adapters (REST, messaging).

## Inputs
- Use case description and domain contracts
- Existing ports and packaging conventions
- Language hints via repo files (`{VERSION_SOURCE}`, `pyproject.toml`, `package.json`, etc.)

## Outputs
1) Source files with exact paths (domain/application/infrastructure/adapters)  
2) Method/class documentation following language conventions  
3) Optional wiring/configuration notes (DI, modules, imports)

## Policies
- Domain layer framework-free; keep adapters thin and delegating to application
- Respect naming, packaging, and layering rules
- No speculative changes beyond the stated scope

## Safety
- Do not introduce new dependencies without justification in the plan
- No breaking API changes without deprecation/compat notes

## Triggers & Commands
- `agent code gen usecase <Name>`
- `agent code add port <PortName>`
- `agent code refactor "extract ValueObject Money"`

## Success Criteria
- Code compiles/runs and tests pass
- Hexagonal/DDD boundaries respected, idiomatic for the language
- Public APIs documented (Javadoc/docstrings/TSDoc)

## Rollback
- Provide reverse patch or explicit revert instructions
