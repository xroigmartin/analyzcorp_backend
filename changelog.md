# Changelog - Backend
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/).

## [Unreleased]

## [Unreleased]
### Added
- **RBAC core schema (PostgreSQL 17)**:
    - `user_status` catalog with seeds and uppercase `code` check.
    - `app_user` (BIGINT PK + `public_id` UUID), auditing FKs (DEFERRABLE), soft delete, flags (`is_service`, `can_login`, `is_protected`), `version`.
    - `role`, `permission` with immutable uppercase `code`, i18n keys, auditing, `version`.
    - Relations `user_role`, `role_permission` with `assigned_by/at` and `granted_by/at`.
    - View `v_user_effective_permissions`.
    - Guard trigger preventing deletion of protected users (override via `SET LOCAL app.allow_protected_delete='on'`).
    - `fn_touch_updated_at`, immutability triggers for `code`, username format check.
    - Extra indexes for N:M and `has_permission()` helper.
    - Seeds: roles, permissions, and two protected service accounts with fixed `public_id` and randomized username/password.
- **RBAC domain module (pure, framework-free)**:
    - Entities: `User`, `Role`, `Permission`, `UserRole`, `RolePermission`; enum `UserStatus`; VO `Version`.
    - Value Objects: `UserId`, `UserPublicId`, `Username`, `Email`, `RoleCode`, `PermissionCode` (normalization & validation).
    - Ports: `UserRepository`, `RoleRepository`, `PermissionRepository`, `UserRoleRepository`, `RolePermissionRepository`, `UserPermissionsQuery`.
    - Domain errors: `DomainException`, `InvariantViolation`, `UniqueConflict`, `UserNotFound`, `RoleNotFound`, `PermissionNotFound`.
- **Documentation**: JavaDoc for model, VOs, ports, errors.
- **Unit tests** VO normalization/validation and domain rules (login allowed, protected deletion, optimistic version, audit timestamps).

### Security
- DB trigger blocks deletion of protected users unless an explicit transactional override is set.
- Domain guard mirrors DB rule: `User.requireDeletable(false)` prevents deletions without override.
- Service accounts seeded with `can_login=false` by default.

### Migration
- Apply Flyway migrations **V00.02.01 … V00.02.15** (RBAC schema baseline) on PostgreSQL 17.
- No new migrations beyond the RBAC schema baseline at this stage.

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
