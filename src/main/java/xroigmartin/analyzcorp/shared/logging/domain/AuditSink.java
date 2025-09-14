package xroigmartin.analyzcorp.shared.logging.domain;

import org.slf4j.Marker;

/**
 * Abstraction for emitting business/security audit events to a target backend.
 * <p>
 * This is a domain-level contract (hexagonal port) and must remain free of framework
 * dependencies. Infrastructure adapters (e.g., SLF4J, Kafka, NoSQL) should implement it.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Accept a pre-serialized, masked JSON payload.</li>
 *   <li>Use the given SLF4J {@link Marker} to distinguish BUSINESS vs SECURITY categories.</li>
 *   <li>Remain non-throwing whenever possible to avoid impacting request flow.</li>
 * </ul>
 */
public interface AuditSink {

    /**
     * Emits a masked JSON audit event to the underlying sink.
     *
     * @param marker     routing marker (e.g., BUSINESS, SECURITY)
     * @param jsonMasked pre-masked JSON payload (vendor-agnostic schema)
     */
    void emit(Marker marker, String jsonMasked);
}
