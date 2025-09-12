package xroigmartin.analyzcorp.shared_kernel.domain.enums;

/**
 * High-level classification for audit events.
 *
 * <ul>
 *   <li>{@link #BUSINESS}: domain facts relevant for compliance and functional audit trails.</li>
 *   <li>{@link #SECURITY}: access control denials, policy blocks, and security-sensitive facts.</li>
 * </ul>
 */
public enum AuditCategory {
    BUSINESS,
    SECURITY
}
