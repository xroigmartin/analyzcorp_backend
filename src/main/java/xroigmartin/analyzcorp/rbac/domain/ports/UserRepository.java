package xroigmartin.analyzcorp.rbac.domain.ports;

import java.util.Optional;

import xroigmartin.analyzcorp.rbac.domain.model.User;
import xroigmartin.analyzcorp.rbac.domain.vo.Email;
import xroigmartin.analyzcorp.rbac.domain.vo.UserId;
import xroigmartin.analyzcorp.rbac.domain.vo.UserPublicId;
import xroigmartin.analyzcorp.rbac.domain.vo.Username;

/**
 * Persistence port for {@link User}.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Save (create/update) while honoring optimistic versioning.</li>
 *   <li>Find by internal PK, public UUID, username, or email.</li>
 *   <li>Delete honoring the protected-user rule; the application layer must call
 *   {@code user.requireDeletable(override)} before {@link #deleteById(UserId, boolean)}.</li>
 * </ul>
 * </p>
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByPublicId(UserPublicId publicId);
    Optional<User> findByUsername(Username username);
    Optional<User> findByEmail(Email email);
    boolean existsByUsername(Username username);
    boolean existsByEmail(Email email);

    /**
     * Physical or logical deletion depends on the implementation.
     * The application layer must validate the protected-user rule beforehand.
     *
     * @param id user identifier
     * @param overrideProtected authorize deletion of protected users
     */
    void deleteById(UserId id, boolean overrideProtected);
}