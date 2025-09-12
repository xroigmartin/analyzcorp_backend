package xroigmartin.analyzcorp.rbac.domain.ports;

import xroigmartin.analyzcorp.rbac.domain.events.AuditEvent;

/**
 * Domain port to publish audit events to an external sink.
 *
 * <p>Infrastructure will implement this to route events to the appropriate channel
 * (e.g., business vs security audit log). The domain remains framework-free.</p>
 */
public interface AuditEventPublisher {

    /**
     * Publish a single audit event. Implementations should be resilient and non-blocking
     * from a caller perspective (e.g., queue/async where appropriate).
     */
    void publish(AuditEvent event);
}
