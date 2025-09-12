package xroigmartin.analyzcorp.rbac.domain.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;
import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;

/**
 * Role↔Permission association, with grant audit metadata.
 *
 * <p>By domain policy, this is treated as <em>append-only</em>.
 * Explicit revocations may be supported by the repository implementation.</p>
 *
 * @since 0.2.x
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@With
public final class RolePermission {

    private static final int ACTOR_MAX = 120;
    private static final String ACTOR_RX = "^[\\p{Print}&&[^\\p{Cntrl}]]+$";

    private final RoleCode roleCode;
    private final PermissionCode permissionCode;
    private final String grantedBy;
    private final OffsetDateTime grantedAt;

    private static String requireActor(String s) {
        if (s == null || s.isBlank()) throw new InvariantViolation("assignedBy/grantedBy required");
        String t = s.trim();
        if (t.length() > ACTOR_MAX) throw new InvariantViolation("actor too long");
        if (!t.matches(ACTOR_RX)) throw new InvariantViolation("actor contains invalid characters");
        return t;
    }

    /**
     * Creates a grant, normalizing the actor and defaulting {@code grantedAt} to {@code now()} if null.
     *
     * <p>Actor constraints mirror {@link UserRole}.</p>
     *
     * @throws InvariantViolation if actor is blank, too long, or contains invalid characters
     */
    public static RolePermission create(RoleCode roleCode, PermissionCode permissionCode, String grantedBy, OffsetDateTime grantedAt) {
        return new RolePermission(
                Objects.requireNonNull(roleCode, "roleCode"),
                Objects.requireNonNull(permissionCode, "permissionCode"),
                requireActor(grantedBy),
                grantedAt != null ? grantedAt : OffsetDateTime.now()
        );
    }
}