package xroigmartin.analyzcorp.rbac.domain.exceptions;

import xroigmartin.analyzcorp.shared_kernel.domain.exceptions.DomainException;

/** Uniqueness conflict (e.g., username/email/role code already in use). */
public final class UniqueConflict extends DomainException {
    public UniqueConflict(String message) { super(message); }
}