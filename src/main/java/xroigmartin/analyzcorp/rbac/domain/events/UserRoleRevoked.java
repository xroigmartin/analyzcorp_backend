package xroigmartin.analyzcorp.rbac.domain.events;

import java.time.OffsetDateTime;
import java.util.Objects;

import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.rbac.domain.vo.UserPublicId;
import xroigmartin.analyzcorp.shared_kernel.domain.enums.AuditCategory;

/** Business/audit fact: a role was revoked from a user. */
public record UserRoleRevoked(
        UserPublicId userId,
        RoleCode role,
        String actor,
        OffsetDateTime occurredAt
) implements AuditEvent {

    public UserRoleRevoked {
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(actor, "actor");
        occurredAt = occurredAt != null ? occurredAt : OffsetDateTime.now();
    }

    @Override public AuditCategory category() { return AuditCategory.BUSINESS; }
    @Override public String action() { return "RBAC_ROLE_REVOKED"; }

    public static UserRoleRevoked now(UserPublicId userId, RoleCode role, String actor) {
        return new UserRoleRevoked(userId, role, actor, OffsetDateTime.now());
    }
}