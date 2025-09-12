package xroigmartin.analyzcorp.rbac.domain.exceptions;

import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.shared_kernel.domain.exceptions.DomainException;

/** Role not found. */
public final class RoleNotFound extends DomainException {
    public RoleNotFound(String message) { super(message); }
    public static RoleNotFound byCode(RoleCode code) { return new RoleNotFound("Role not found: code=" + code.value()); }
}