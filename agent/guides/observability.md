# Observability Guidelines

- Structured logs with minimum fields: `ts, level, logger, trace_id, span_id, event, business_key, safe_message`.
- **Mask** sensitive data: emails, JWT, IBAN, credit cards.
- Propagate correlation IDs through all layers (adapters, application, infrastructure).
- Expose health and metrics endpoints according to project standards.
