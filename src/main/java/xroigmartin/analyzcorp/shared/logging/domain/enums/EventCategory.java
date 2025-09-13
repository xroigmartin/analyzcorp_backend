package xroigmartin.analyzcorp.shared.logging.domain.enums;

/**
 * High-level event categories used to route logs and drive retention/policies.
 */
public enum EventCategory {
    SYSTEM,    // technical/system logs
    BUSINESS,  // domain business events
    SECURITY   // security/business-critical events
}