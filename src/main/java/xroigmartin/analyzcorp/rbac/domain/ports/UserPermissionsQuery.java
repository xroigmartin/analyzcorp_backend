package xroigmartin.analyzcorp.rbac.domain.ports;

import java.util.Set;

import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.rbac.domain.vo.UserId;

/**
 * Read port for effective permissions of a user.
 *
 * <p>Typical implementation: query the {@code v_user_effective_permissions} database view.</p>
 *
 * @apiNote Other bounded contexts should consume this through an application service
 * (or a separate {@code rbac-api} module) rather than depending on the internal domain.
 */
public interface UserPermissionsQuery {
    Set<PermissionCode> findEffectivePermissions(UserId userId);
}