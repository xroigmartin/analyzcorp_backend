package xroigmartin.analyzcorp.rbac.api;

import java.util.Set;

import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.rbac.domain.vo.UserPublicId;

public interface AuthorizationService {
    boolean hasPermission(UserPublicId userPublicId, PermissionCode permission);
    void requirePermission(UserPublicId userPublicId, PermissionCode permission);
    Set<PermissionCode> getEffectivePermissions(UserPublicId userPublicId);
}
