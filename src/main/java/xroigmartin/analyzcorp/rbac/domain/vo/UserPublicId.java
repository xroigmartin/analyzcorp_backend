package xroigmartin.analyzcorp.rbac.domain.vo;

import java.util.UUID;

/**
 * Publicly exposed user identifier (UUID).
 *
 * @param value non-null UUID
 */
public record UserPublicId(UUID value) {
    public UserPublicId {
        if (value == null) throw new IllegalArgumentException("UserPublicId cannot be null");
    }
    /** Generates a random UUID. */
    public static UserPublicId random() { return new UserPublicId(UUID.randomUUID()); }
    /** Parses a UUID from String. */
    public static UserPublicId of(String uuid) { return new UserPublicId(UUID.fromString(uuid)); }
}
