package xroigmartin.analyzcorp.rbac.domain.vo;

import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;

/**
 * Internal (BIGINT PK) user identifier.
 *
 * @param value integer {@code > 0}
 */
public record UserId(long value) {
    public UserId {
        if (value <= 0) throw new InvariantViolation("UserId must be > 0");
    }
    /** Readable factory method. */
    public static UserId of(long value) { return new UserId(value); }
}
