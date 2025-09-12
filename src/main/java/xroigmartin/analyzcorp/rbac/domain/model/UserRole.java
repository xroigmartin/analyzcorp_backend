package xroigmartin.analyzcorp.rbac.domain.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.rbac.domain.vo.UserId;

/**
 * User↔Role association, with assignment audit metadata.
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
public final class UserRole {

    private static final int ACTOR_MAX = 120;
    private static final String ACTOR_RX = "^[\\p{Print}&&[^\\p{Cntrl}]]+$";

    private final UserId userId;
    private final RoleCode roleCode;
    private final String assignedBy;        // username/email/principal
    private final OffsetDateTime assignedAt;


    /**
     * Validates the assignment actor:
     * <ul>
     *   <li>non-blank</li>
     *   <li>trimmed length ≤ 120</li>
     *   <li>printable characters only (no control chars)</li>
     * </ul>
     */
    private static String requireActor(String s) {
        if (s == null || s.isBlank()) throw new InvariantViolation("assignedBy/grantedBy required");
        String t = s.trim();
        if (t.length() > ACTOR_MAX) throw new InvariantViolation("actor too long");
        if (!t.matches(ACTOR_RX)) throw new InvariantViolation("actor contains invalid characters");
        return t;
    }

    /**
     * Creates an assignment, normalizing the actor and defaulting {@code assignedAt} to {@code now()} if null.
     *
     * <p>Actor constraints:
     * <ul>
     *   <li>Required, trimmed, max length 120.</li>
     *   <li>Printable characters only (no control characters).</li>
     * </ul>
     * </p>
     *
     * @throws InvariantViolation if actor is blank, too long, or contains invalid characters
     */
    public static UserRole create(UserId userId, RoleCode roleCode, String assignedBy, OffsetDateTime assignedAt) {
        return new UserRole(
                Objects.requireNonNull(userId, "userId"),
                Objects.requireNonNull(roleCode, "roleCode"),
                requireActor(assignedBy),
                assignedAt != null ? assignedAt : OffsetDateTime.now()
        );
    }
}

