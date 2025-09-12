package xroigmartin.analyzcorp.rbac.domain.events;

import java.time.OffsetDateTime;
import java.util.Objects;

import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.rbac.domain.vo.UserPublicId;
import xroigmartin.analyzcorp.shared_kernel.domain.enums.AuditCategory;

/**
 * Business/audit fact: a role was assigned to a user.
 *
 * <p>PII note: actor should be sanitized (no control chars, reasonable length) by the application layer.</p>
 */
public record UserRoleAssigned(
        UserPublicId userId,
        RoleCode role,
        String actor,
        OffsetDateTime occurredAt
) implements AuditEvent {

    public UserRoleAssigned {
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(actor, "actor");
        occurredAt = occurredAt != null ? occurredAt : OffsetDateTime.now();
    }

    @Override public AuditCategory category() { return AuditCategory.BUSINESS; }
    @Override public String action() { return "RBAC_ROLE_ASSIGNED"; }

    /** Convenience factory using {@code now()}. */
    public static UserRoleAssigned now(UserPublicId userId, RoleCode role, String actor) {
        return new UserRoleAssigned(userId, role, actor, OffsetDateTime.now());
    }
}