# DB + Migrations Agent

- **Type:** AI / Script
- **Purpose:** Design and evolve the database schema; generate {DB_MIGRATION_TOOL} migrations.
- **Scope:** Schema definition, DDL, indexes, constraints, naming policies.

## Inputs
- Domain/use-case requirements
- Current schema and migration folder
- Project naming and indexing policies

## Outputs
1) Migration files `V<semver>__<short_desc>.sql` (or `VNNN__...`)  
2) Optional plan with backward/rollback notes

## Policies
- Forward-only migrations; use transitional views for renames
- `snake_case` for table/column/index names

## Safety
- Never drop without a safe migration path/backup notes
- Do not execute migrations unless explicitly allowed

## Triggers & Commands
- `agent db plan "<change>"`
- `agent db gen migration <name>`

## Success Criteria
- Valid DDL; naming/indexing consistent with policy
- Backward-compatible schema changes

## Rollback
- Provide reverse DDL or clear manual steps
