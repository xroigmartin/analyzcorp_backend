# Changelog - Backend
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/).

## [Unreleased]

## [0.2.0] - 2025-09-07
### Added
- RBAC core schema:
    - `user_status` catalog with seeds and uppercase code check.
    - `app_user` (BIGINT PK + `public_id` UUID), auditing FKs (DEFERRABLE), soft delete, flags (`is_service`, `can_login`, `is_protected`), `version`.
    - `role`, `permission` with immutable uppercase `code`, i18n keys, auditing, `version`.
    - Relations `user_role`, `role_permission` with assigned/granted metadata.
    - View `v_user_effective_permissions`.
    - Guard trigger to prevent deleting protected users (override via `SET LOCAL app.allow_protected_delete='on'`).
    - `fn_touch_updated_at`, immutability triggers for `code`, username format check.
    - Extra indexes for N:M and `has_permission()` helper.
    - Seeds: roles, permissions, and two protected service accounts with fixed `public_id` and randomized username/password.

### Security
- Protected service accounts cannot be deleted unless an explicit transactional override is set.
- Login disabled by default for service accounts (`can_login=false`).

### Migration
- Apply Flyway migrations `V00.02.01` … `V00.02.15` on PostgreSQL 17.

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
