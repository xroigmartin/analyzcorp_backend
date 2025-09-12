package xroigmartin.analyzcorp.rbac.domain.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;

/**
 * System role (e.g., ADMIN, AUDITOR).
 *
 * <p>Invariants:
 * <ul>
 *   <li>{@link #code} is uppercase and matches {@code A-Z0-9_}.</li>
 *   <li>{@link #displayKey} and {@link #descriptionKey} are non-blank i18n keys.</li>
 * </ul>
 * </p>
 *
 * @since 0.2.x
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@With
public final class Role {

    private static final int KEY_MAX = 128;

    private final RoleCode code;
    private final String displayKey;
    private final String descriptionKey;
    private final Version version;

    /**
     * Validates a non-blank i18n key and enforces a maximum length (≤ 128).
     *
     * @throws InvariantViolation if blank or exceeds the maximum length
     */
    private static String requireKey(String key, String field) {
        if (key == null || key.isBlank()) throw new InvariantViolation(field + " required");
        String t = key.trim();
        if (t.length() > KEY_MAX) throw new InvariantViolation(field + " too long");
        return t;
    }

    /**
     * Creates a role with {@code version=0}.
     */
    public static Role create(RoleCode code, String displayKey, String descriptionKey) {
        return new Role(
                Objects.requireNonNull(code, "code"),
                requireKey(displayKey, "displayKey"),
                requireKey(descriptionKey, "descriptionKey"),
                Version.zero()
        );
    }

    /**
     * Rehydrates a role from persistence (no defaults applied).
     */
    public static Role rehydrate(RoleCode code, String displayKey, String descriptionKey, Version version) {
        return new Role(
                Objects.requireNonNull(code, "code"),
                requireKey(displayKey, "displayKey"),
                requireKey(descriptionKey, "descriptionKey"),
                Objects.requireNonNull(version, "version")
        );
    }

    /** Returns a copy with incremented version. */
    public Role nextVersion() { return withVersion(version.next()); }


}
