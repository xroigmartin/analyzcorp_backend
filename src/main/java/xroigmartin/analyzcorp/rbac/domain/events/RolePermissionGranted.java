package xroigmartin.analyzcorp.rbac.domain.events;

import java.time.OffsetDateTime;
import java.util.Objects;

import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.shared_kernel.domain.enums.AuditCategory;

/** Business/audit fact: a permission was granted to a role. */
public record RolePermissionGranted(
        RoleCode role,
        PermissionCode permission,
        String actor,
        OffsetDateTime occurredAt
) implements AuditEvent {

    public RolePermissionGranted {
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(permission, "permission");
        Objects.requireNonNull(actor, "actor");
        occurredAt = occurredAt != null ? occurredAt : OffsetDateTime.now();
    }

    @Override public AuditCategory category() { return AuditCategory.BUSINESS; }
    @Override public String action() { return "RBAC_PERMISSION_GRANTED_TO_ROLE"; }

    public static RolePermissionGranted now(RoleCode role, PermissionCode permission, String actor) {
        return new RolePermissionGranted(role, permission, actor, OffsetDateTime.now());
    }
}
