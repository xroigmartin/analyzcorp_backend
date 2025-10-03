# Layering Policy (Hexagonal)

| From \ To | domain | application | infrastructure | adapters |
|---|---:|---:|---:|---:|
| **domain** | ✅ | 🚫 | 🚫 | 🚫 |
| **application** | ✅ | ✅ | 🚫 | 🚫 |
| **infrastructure** | ✅ (via ports) | ✅ (DTOs/ports only) | ✅ | ✅ |
| **adapters** | ✅ (via application) | ✅ | ✅ | ✅ |

- Domain: Entities, Value Objects, Domain Events, Aggregates, invariants. **No framework annotations**.
- Application: Use cases, DTOs, transactions, orchestration, validation. **No persistence or web** concerns.
- Infrastructure: Repository implementations, mappers, external clients. **No business rules**.
- Input Adapters: REST controllers, message consumers (thin; delegate to application).
