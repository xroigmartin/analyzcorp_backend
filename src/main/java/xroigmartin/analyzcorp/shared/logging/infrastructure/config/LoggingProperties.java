package xroigmartin.analyzcorp.shared.logging.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Externalized configuration for AnalyzCorp logging behavior.
 * <p>
 * These properties tune masking, correlation header handling, MDC limits,
 * and security stacktrace emission. They are intentionally minimal and vendor-agnostic.
 * </p>
 */
@Data
@ConfigurationProperties(prefix = "analyzcorp.logging")
public class LoggingProperties {

    /** When {@code true}, masking utilities are enabled by default. */
    private boolean maskingEnabled = true;

    /** Replacement token used by the masker when redacting sensitive values. */
    private String mask = "***";

    /** When {@code true}, generate a correlation id if the incoming header is missing. */
    private boolean ensureCorrelationId = true;

    /** HTTP header used to read/write the external correlation id. */
    private String correlationHeader = "X-Correlation-Id";

    /** When {@code true}, allow stacktraces in SECURITY channel (off by default). */
    private boolean securityStacktraces = false;

    /**
     * Maximum allowed length for MDC values. Values exceeding the limit are truncated.
     * <p>Purpose: protect against log injection / amplification and keep log events small.</p>
     */
    private int mdcMaxLen = 256;
}
