package xroigmartin.analyzcorp.rbac.domain.exceptions;

import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.shared_kernel.domain.exceptions.DomainException;

/** Permission not found. */
public final class PermissionNotFound extends DomainException {
    public PermissionNotFound(String message) { super(message); }
    public static PermissionNotFound byCode(PermissionCode code) { return new PermissionNotFound("Permission not found: code=" + code.value()); }
}
