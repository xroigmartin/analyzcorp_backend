package xroigmartin.analyzcorp.rbac.domain.ports;

import java.util.Optional;

import xroigmartin.analyzcorp.rbac.domain.model.Permission;
import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;

/** Persistence port for {@link Permission}. */
public interface PermissionRepository {
    Permission save(Permission permission);
    Optional<Permission> findByCode(PermissionCode code);
    boolean existsByCode(PermissionCode code);
    void deleteByCode(PermissionCode code);
}