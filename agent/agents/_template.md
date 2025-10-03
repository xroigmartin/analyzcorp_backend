# Sub-Agent Template

> Use this file as a starting point to define a new sub-agent. Copy it to a new file inside `agent/agents/` and customize.

### [Sub-Agent Name]
- **Type:** (AI / Human / Script / Hybrid)
- **Purpose:** [Short description]
- **Scope:** [Layers/domains: DB schema / Application / Adapters]

## Inputs
- [Paths or files]
- [Policies/conventions]
- [Optional: user story / brief]

## Outputs
1) Unified diffs (modifications)  
2) Full files (creations)  
3) Optional plan before patch  
- [Exact target paths]

## Policies
- Naming, layering, logging/security, testing.

## Safety
- Rollback required for destructive actions.
- No external calls unless allowed.
- No secrets in logs.

## Triggers & Commands
- `agent <name> plan "<goal>"`
- `agent <name> gen <artifact>`
- `agent <name> review <scope>`

## Success Criteria
- Compiles, tests pass, respects architecture, backward-compatible.

## Rollback
- How to revert or mitigate.
