package xroigmartin.analyzcorp.rbac.domain.exceptions;

import xroigmartin.analyzcorp.shared_kernel.domain.exceptions.DomainException;

/**
 * Signals an optimistic-locking conflict (stale version) on update.
 *
 * <p>Infrastructure should map database concurrency errors to this exception,
 * enabling the application layer to return a suitable response (e.g., HTTP 409).</p>
 */
public final class OptimisticLockConflict extends DomainException {
    public OptimisticLockConflict(String message) { super(message); }
}