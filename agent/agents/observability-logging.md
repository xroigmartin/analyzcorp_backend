# Observability & Logging Agent

- **Type:** AI
- **Purpose:** Enforce **structured logging**, correlation IDs, metrics/tracing, and **sensitive data masking**.
- **Scope:** Logging configuration, filters, MDC/Tracing, metrics exposure.

## Inputs
- Current logging setup
- Business events to log

## Outputs
1) Logging config patches (JSON/key=value)  
2) Example business events and masking filters

## Policies
- Minimum log fields: `ts, level, logger, trace_id, span_id, event, business_key, safe_message`
- Never log secrets or sensitive data

## Safety
- Performance-aware logging (avoid excessive I/O)

## Triggers & Commands
- `agent obs review`
- `agent obs setup`

## Success Criteria
- Structured logs with correlation IDs
- Sensitive data masked

## Rollback
- Revert logging config or reduce verbosity
