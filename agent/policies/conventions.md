# Project Conventions

## Naming & Packaging
- Java classes: `PascalCase`; fields/methods: `camelCase`; packages: `lowercase.dot.separated`.
- SQL: `snake_case` for tables/columns/indexes.
- Ports end with `*Port` (domain/application), Adapters end with `*Adapter` (infrastructure).

## Testing Policy
- Unit tests (domain & application) first; aim for TDD when feasible.
- Integration tests for infrastructure (DB, external clients).
- Contract/component tests for public APIs when applicable.
- Naming: `ClassNameTest` (unit), `ClassNameIT` (integration).

## Migrations & Schema
- {DB_MIGRATION_TOOL} files: `V<semver>__<short_description>.sql` or incremental `VNNN__...`.
- Prefer UUIDs for public identifiers; keep DB PK strategy per project needs.
- Include auditing: `created_at/by`, `updated_at/by`, `deleted_at/by`, `version`.

## Documentation & Versioning
- `CHANGELOG.md` (Keep a Changelog) + SemVer.
- Conventional commits: `feat:`, `fix:`, `refactor:`, `test:`, `docs:`, `build:`, `chore:`.

## Dependencies
- Add new libraries only with justification. Use standard libraries before third-party.
- Keep the **domain** free of framework dependencies.

## Security & Observability
- Validate inputs at boundaries; sanitize outputs.
- Structured logging (JSON or `key=value`) with correlation IDs; **no secrets** in logs.
- Map exceptions at adapter level; domain/application throw meaningful exceptions.
