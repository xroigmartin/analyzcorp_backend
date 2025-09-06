# Changelog - Backend
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/).

## [Unreleased]
### Added
- Pending: use cases for budgets and transactions.
- Pending: integration with advanced auditing.

---

## [0.2.0] - 2025-09-06
### Added
- `EmptyResponse` for endpoints that return no data.
- `AccountErrorResponse` and `AccountControllerAdvice` for error handling.
- Duplicate validation applied in use cases (`account_entity_name_key`).
- Unit tests for `DeleteAccountUseCase` and `AccountController`.

### Changed
- Refactor of `ApiResponse` and `ApiError` to unify response format.
- `AccountController` endpoints now declare `produces` and `consumes = application/json`.
- Refactor of validations: moved to use cases (persistence layer without validations).
- SQL scripts split into multiple files with Flyway.

### Fixed
- Duplicate key error when creating accounts (`duplicate key value violates unique constraint`).
- Fixed unit tests that threw exceptions when mocking `void` methods.

---

## [0.1.0] - 2025-06-01
### Added
- Initial project setup with Spring Boot 3 + Java 21.
- PostgreSQL 17 configuration with Flyway.
- `Account` entity with basic CRUD.
- Use cases for creating, listing, updating, and deleting accounts.
