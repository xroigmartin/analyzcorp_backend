package xroigmartin.analyzcorp.rbac.domain.events;

import java.time.OffsetDateTime;
import java.util.Objects;

import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.shared_kernel.domain.enums.AuditCategory;

/** Business/audit fact: a permission was revoked from a role. */
public record RolePermissionRevoked(
        RoleCode role,
        PermissionCode permission,
        String actor,
        OffsetDateTime occurredAt
) implements AuditEvent {

    public RolePermissionRevoked {
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(permission, "permission");
        Objects.requireNonNull(actor, "actor");
        occurredAt = occurredAt != null ? occurredAt : OffsetDateTime.now();
    }

    @Override public AuditCategory category() { return AuditCategory.BUSINESS; }
    @Override public String action() { return "RBAC_PERMISSION_REVOKED_FROM_ROLE"; }

    public static RolePermissionRevoked now(RoleCode role, PermissionCode permission, String actor) {
        return new RolePermissionRevoked(role, permission, actor, OffsetDateTime.now());
    }
}