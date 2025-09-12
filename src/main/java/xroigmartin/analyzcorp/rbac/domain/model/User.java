package xroigmartin.analyzcorp.rbac.domain.model;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import xroigmartin.analyzcorp.rbac.domain.enums.UserStatus;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;
import xroigmartin.analyzcorp.rbac.domain.vo.Email;
import xroigmartin.analyzcorp.rbac.domain.vo.UserId;
import xroigmartin.analyzcorp.rbac.domain.vo.UserPublicId;
import xroigmartin.analyzcorp.rbac.domain.vo.Username;

/**
 * User aggregate root within the RBAC bounded context.
 *
 * <p>Core invariants:
 * <ul>
 *   <li>{@link #username} and {@link #email} are required and normalized (CITEXT semantics).</li>
 *   <li>{@link #status} together with {@link #canLogin} governs authentication capability.</li>
 *   <li>{@link #isProtected} requires {@link #requireDeletable(boolean)} with override to allow deletion.</li>
 * </ul>
 * </p>
 *
 * <p>Creation vs rehydration:
 * <ul>
 *   <li>{@link #createNew(Username, Email, boolean, boolean, boolean)} applies domain defaults
 *   (random public UUID, {@code status=ACTIVE}, {@code version=0}).</li>
 *   <li>{@link #rehydrate(UserId, UserPublicId, Username, Email, UserStatus, boolean, boolean, boolean, Version)}
 *   reconstructs an instance exactly as persisted.</li>
 * </ul>
 * </p>
 *
 * <p>PII note: {@code toString()} intentionally excludes sensitive fields (e.g. email, flags)
 * to avoid leaking data in logs.</p>
 *
 * ...
 *
 * @see UserStatus
 * @see Version
 * @since 0.2.x
 */
@Getter
@EqualsAndHashCode
@ToString(exclude = {"email", "canLogin", "isProtected", "id", "publicId"})
@With
public final class User {

    private final UserId id;               // null if not yet persisted
    private final UserPublicId publicId;   // externally exposed UUID
    private final Username username;
    private final Email email;
    private final UserStatus status;
    private final boolean isService;
    private final boolean canLogin;
    private final boolean isProtected;
    private final Version version;

    private User(UserId id,
                 UserPublicId publicId,
                 Username username,
                 Email email,
                 UserStatus status,
                 boolean isService,
                 boolean canLogin,
                 boolean isProtected,
                 Version version) {
        this.id = id;
        this.publicId = Objects.requireNonNull(publicId, "publicId");
        this.username = Objects.requireNonNull(username, "username");
        this.email = Objects.requireNonNull(email, "email");
        this.status = Objects.requireNonNull(status, "status");
        this.isService = isService;
        this.canLogin = canLogin;
        this.isProtected = isProtected;
        this.version = Objects.requireNonNull(version, "version");
    }

    /**
     * Creates a brand-new user with domain defaults (random public UUID, ACTIVE, version=0).
     *
     * @param username normalized username (CITEXT)
     * @param email normalized email (CITEXT)
     * @param isService whether this is a service/technical user
     * @param canLogin whether the user is allowed to log in
     * @param isProtected whether deletion requires override
     * @return immutable {@code User} instance
     * @since 0.2.x
     */
    public static User createNew(Username username, Email email,
                                 boolean isService, boolean canLogin, boolean isProtected) {
        return new User(
                null,
                UserPublicId.random(),
                username,
                email,
                UserStatus.ACTIVE,
                isService,
                canLogin,
                isProtected,
                Version.zero()
        );
    }

    /**
     * Reconstructs a persisted user exactly as stored (no domain defaults applied).
     *
     * @implNote Intended to be used by repository adapters when mapping DB rows to domain.
     */
    public static User rehydrate(UserId id, UserPublicId publicId, Username username, Email email,
                                 UserStatus status, boolean isService, boolean canLogin, boolean isProtected,
                                 Version version) {
        return new User(id, publicId, username, email, status, isService, canLogin, isProtected, version);
    }

    /**
     * Validates whether the current state allows authentication.
     *
     * @throws InvariantViolation if login is not allowed for the current state/flags
     */
    public void requireLoginAllowed() {
        if (!status.isLoginAllowed(canLogin)) {
            throw new InvariantViolation("User not allowed to login with current status/flags");
        }
    }

    /**
     * Validates whether this user can be deleted considering the protection flag.
     *
     * @param overrideProtected {@code true} to authorize deletion of protected users
     * @throws InvariantViolation if the user is protected and override is not granted
     */
    public void requireDeletable(boolean overrideProtected) {
        if (isProtected && !overrideProtected) {
            throw new InvariantViolation("Protected user cannot be deleted without override");
        }
    }

    /**
     * Returns a copy with {@link Version} incremented by 1.
     *
     * @return immutable copy with {@code version = version + 1}
     */
    public User nextVersion() {
        return withVersion(version.next());
    }
}