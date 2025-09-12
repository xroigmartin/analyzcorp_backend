package xroigmartin.analyzcorp.rbac.domain.ports;

import java.util.List;

import xroigmartin.analyzcorp.rbac.domain.model.RolePermission;
import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;

/**
 * Port for the Role↔Permission association.
 *
 * <p>By domain policy this is <em>append-only</em>; explicit revocations (deletes) may be supported.</p>
 */
public interface RolePermissionRepository {
    RolePermission save(RolePermission rp);
    void delete(RoleCode roleCode, PermissionCode perm);
    List<RolePermission> findByRole(RoleCode roleCode);
}
