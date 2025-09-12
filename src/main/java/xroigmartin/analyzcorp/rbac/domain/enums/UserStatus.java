package xroigmartin.analyzcorp.rbac.domain.enums;

/**
 * User status catalog.
 *
 * <p>Must stay in sync with {@code user_status.code} in the database.</p>
 *
 * @since 0.2.x
 */
public enum UserStatus {
    ACTIVE,
    INACTIVE,
    LOCKED,
    SUSPENDED,
    DELETED;

    /**
     * Determines whether a user with this status is allowed to authenticate, given {@code canLogin}.
     *
     * @param canLoginFlag user capability flag
     * @return {@code true} if login is allowed
     */
    public boolean isLoginAllowed(boolean canLoginFlag) {
        return this == ACTIVE && canLoginFlag;
    }
}