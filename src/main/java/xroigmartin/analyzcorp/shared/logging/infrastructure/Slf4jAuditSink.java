package xroigmartin.analyzcorp.shared.logging.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import xroigmartin.analyzcorp.shared.logging.domain.AuditSink;

/**
 * SLF4J-based {@link AuditSink} that forwards events to a dedicated logger name.
 * <p>
 * Logback uses marker-based filters to route these events to the BUSINESS appender,
 * keeping them fully separate from system/technical logs.
 * </p>
 */
public class Slf4jAuditSink implements AuditSink {

    /**
     * Dedicated logger for business/security channel. Must match the name configured
     * in {@code logback-spring.xml}.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger("xroigmartin.analyzcorp.audit.Business");

    @Override
    public void emit(Marker marker, String jsonMasked) {
        LOGGER.info(marker, jsonMasked);
    }
}