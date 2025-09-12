package xroigmartin.analyzcorp.rbac.domain.ports;

import java.util.Optional;

import xroigmartin.analyzcorp.rbac.domain.model.Role;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;

/** Persistence port for {@link Role}. */
public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findByCode(RoleCode code);
    boolean existsByCode(RoleCode code);
    void deleteByCode(RoleCode code);
}