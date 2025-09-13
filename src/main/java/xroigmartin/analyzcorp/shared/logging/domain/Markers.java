package xroigmartin.analyzcorp.shared.logging.domain;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Centralized SLF4J {@link Marker} definitions to route log events to dedicated channels.
 * <p>
 * Logback configuration uses these markers to accept/deny events per appender, effectively
 * separating system logs from business/security logs.
 * </p>
 */
public final class Markers {

    /** Marker name used for business events. */
    public static final String BUSINESS_NAME = "BUSINESS";

    /** Marker name used for security events. */
    public static final String SECURITY_NAME = "SECURITY";

    /** SLF4J marker instance for business events. */
    public static final Marker BUSINESS = MarkerFactory.getMarker(BUSINESS_NAME);

    /** SLF4J marker instance for security events. */
    public static final Marker SECURITY = MarkerFactory.getMarker(SECURITY_NAME);

    private Markers() { /* utility class */ }
}
