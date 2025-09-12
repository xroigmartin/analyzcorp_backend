package xroigmartin.analyzcorp.rbac.domain.ports;

import java.util.List;

import xroigmartin.analyzcorp.rbac.domain.model.UserRole;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.rbac.domain.vo.UserId;

/**
 * Port for the User↔Role association.
 *
 * <p>By domain policy this is <em>append-only</em>; explicit revocations (deletes) may be supported.</p>
 */
public interface UserRoleRepository {
    UserRole save(UserRole userRole);
    void delete(UserId userId, RoleCode roleCode);
    List<UserRole> findByUser(UserId userId);
}
