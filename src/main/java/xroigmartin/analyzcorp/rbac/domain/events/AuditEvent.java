package xroigmartin.analyzcorp.rbac.domain.events;

import java.time.OffsetDateTime;

import xroigmartin.analyzcorp.shared_kernel.domain.enums.AuditCategory;

/**
 * Base type for RBAC audit events.
 *
 * <p>Events should avoid PII; prefer public identifiers and normalized codes.
 * Application/infra can enrich with request/correlation IDs via MDC at publish time.</p>
 */
public sealed interface AuditEvent
        permits UserRoleAssigned, UserRoleRevoked, RolePermissionGranted, RolePermissionRevoked,
        LoginBlocked, ProtectedUserDeletionBlocked {

    /** Category used to route events to business vs security audit sinks. */
    AuditCategory category();

    /** Machine-readable action name (e.g. {@code RBAC_ROLE_ASSIGNED}). */
    String action();

    /** UTC-ish timestamp of when the fact occurred (not publish time). */
    OffsetDateTime occurredAt();
}