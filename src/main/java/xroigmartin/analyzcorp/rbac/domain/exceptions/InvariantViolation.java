package xroigmartin.analyzcorp.rbac.domain.exceptions;

import xroigmartin.analyzcorp.shared_kernel.domain.exceptions.DomainException;

/**
 * Domain invariant violation or invalid state transition.
 *
 * <p>Often mapped to HTTP 422 (Unprocessable Entity) in the web layer.</p>
 */
public final class InvariantViolation extends DomainException {
    public InvariantViolation(String message) { super(message); }
}
