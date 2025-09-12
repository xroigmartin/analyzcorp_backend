package xroigmartin.analyzcorp.rbac.domain.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;
import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;

/**
 * System permission (e.g., ACCOUNT_READ, USER_DELETE).
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
public final class Permission {

    private final PermissionCode code;
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
        return key.trim();
    }

    /** Creates a permission with {@code version=0}. */
    public static Permission create(PermissionCode code, String displayKey, String descriptionKey) {
        return new Permission(
                Objects.requireNonNull(code, "code"),
                requireKey(displayKey, "displayKey"),
                requireKey(descriptionKey, "descriptionKey"),
                Version.zero()
        );
    }

    /** Rehydrates a permission from persistence (no defaults applied). */
    public static Permission rehydrate(PermissionCode code, String displayKey, String descriptionKey, Version version) {
        return new Permission(
                Objects.requireNonNull(code, "code"),
                requireKey(displayKey, "displayKey"),
                requireKey(descriptionKey, "descriptionKey"),
                Objects.requireNonNull(version, "version")
        );
    }

    /** Returns a copy with incremented version. */
    public Permission nextVersion() { return withVersion(version.next()); }
}
