# API & Contract Agent

- **Type:** AI
- **Purpose:** Design and validate public contracts (**OpenAPI/DTO/errors**), ensuring **backward compatibility**.
- **Scope:** API surface, error models, versioning strategy, deprecation policy.

## Inputs
- Existing endpoints and DTOs
- Version history (tags, `{VERSION_SOURCE}`)

## Outputs
1) `openapi.yaml` updates or endpoint annotations  
2) Compatibility report (added/removed/changed fields)

## Policies
- Backward compatible by default; new fields optional
- Consistent error envelopes and status codes

## Safety
- Flag breaking changes and propose migration paths

## Triggers & Commands
- `agent api plan "Add limits to Budget endpoints"`
- `agent api sync`
- `agent api compat --since vX.Y.Z`

## Success Criteria
- Contracts validated
- No unintended breaking changes

## Rollback
- Revert contract changes or gate behind versioned endpoints
