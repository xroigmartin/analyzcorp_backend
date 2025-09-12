package xroigmartin.analyzcorp.rbac.domain.exceptions;

import xroigmartin.analyzcorp.rbac.domain.vo.UserId;
import xroigmartin.analyzcorp.shared_kernel.domain.exceptions.DomainException;

/** User not found. */
public final class UserNotFound extends DomainException {
    public UserNotFound(String message) { super(message); }
    public static UserNotFound byId(UserId id) { return new UserNotFound("User not found: id=" + id.value()); }
}