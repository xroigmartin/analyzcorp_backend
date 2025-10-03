# Compatibility Checklist (API & DB)

- API changes are **backward compatible** by default (new fields optional).
- Breaking changes are versioned (new endpoints or versioned routes).
- DB migrations are **forward-only**. For renames, use transitional views/columns.
- Constraints and indexes are added online when possible.
