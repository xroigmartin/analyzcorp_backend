package xroigmartin.analyzcorp.rbac.domain.model;

/**
 * Value Object for optimistic versioning (mapped to an {@code INT} column).
 *
 * @param value integer {@code >= 0}
 * @since 0.2.x
 */
public record Version(int value) {
    public Version {
        if (value < 0) throw new IllegalArgumentException("Version must be >= 0");
    }

    /** @return initial version (0) */
    public static Version zero() { return new Version(0); }

    /** @return new instance with value incremented by 1 */
    public Version next() { return new Version(value + 1); }
}