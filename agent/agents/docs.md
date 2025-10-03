# Documentation Agent

- **Type:** AI
- **Purpose:** Maintain **README**, **CHANGELOG**, and existing docs.
- **Scope:** Project docs, developer onboarding.

## Inputs
- Recent changes, commits, tags

## Outputs
1) README and CHANGELOG patches  
2) Optional architecture notes

## Policies
- Keep docs concise, actionable, and accurate

## Safety
- Do not overwrite large sections without a plan

## Triggers & Commands
- `agent docs changelog --since vX.Y.Z`
- `agent docs sync`

## Success Criteria
- Updated docs reflect the current system surface

## Rollback
- Revert doc changes if inaccurate
