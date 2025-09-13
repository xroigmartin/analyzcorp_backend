package xroigmartin.analyzcorp.shared.logging.domain;

import lombok.experimental.UtilityClass;

/**
 * Canonical MDC key names used across the application to populate structured logs.
 * <p>
 * Keeping MDC keys centralized ensures consistent field naming across services
 * and appenders, and avoids typos or divergent conventions.
 * </p>
 */
@UtilityClass
public final class MdcKeys {

    /** Correlation identifier spanning the request journey. */
    public static final String TRACE_CORRELATION_ID = "trace.correlation_id";

    /** Unique identifier for the current request/operation unit. */
    public static final String REQUEST_ID = "request.id";

    /** Logical service name (e.g., analyzcorp-backend). */
    public static final String SERVICE_NAME = "service.name";

    /** Service version (e.g., SemVer or build identifier). */
    public static final String SERVICE_VERSION = "service.version";

    /** Deployment environment (dev, staging, prod, ...). */
    public static final String ENV = "env";

    // Business-related MDC keys (avoid storing PII whenever possible)
    public static final String ACTOR_ID = "actor.id";
    public static final String ACTOR_NAME = "actor.name";
    public static final String ENTITY_TYPE = "entity.type";
    public static final String ENTITY_ID = "entity.id";
    public static final String TARGET_ID = "target.id";
    public static final String EVENT_ACTION = "event.action";
    public static final String EVENT_CATEGORY = "event.category";
    public static final String OUTCOME = "outcome";
    public static final String REASON = "reason";

}
