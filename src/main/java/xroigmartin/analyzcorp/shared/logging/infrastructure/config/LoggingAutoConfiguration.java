package xroigmartin.analyzcorp.shared.logging.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import xroigmartin.analyzcorp.shared.logging.application.BusinessLogger;
import xroigmartin.analyzcorp.shared.logging.domain.AuditSink;
import xroigmartin.analyzcorp.shared.logging.domain.MdcSafe;
import xroigmartin.analyzcorp.shared.logging.domain.mask.SensitiveDataMasker;
import xroigmartin.analyzcorp.shared.logging.infrastructure.Slf4jAuditSink;
import xroigmartin.analyzcorp.shared.logging.infrastructure.web.CorrelationIdFilter;

/**
 * Spring Boot auto-configuration for AnalyzCorp logging.
 * <p>
 * Wires defaults for masking, audit sink, the business logger, and registers the servlet filter
 * that maintains correlation ids in MDC. All beans are replaceable by user-defined beans.
 * </p>
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@AllArgsConstructor
public class LoggingAutoConfiguration {

    private final LoggingProperties props;

    /** Initialize global MDC guard rails (max value length). */
    @PostConstruct
    void initMdcLimit() {
        MdcSafe.setMaxLen(props.getMdcMaxLen());
    }

    /**
     * Provides a default masking utility. Override with your own {@link SensitiveDataMasker} bean
     * to customize patterns and replacement token.
     */
    @Bean
    public SensitiveDataMasker sensitiveDataMasker() {
        return SensitiveDataMasker.withMask(props.getMask());
    }

    /**
     * Default audit sink that routes events to SLF4J using the dedicated business logger name.
     * Replace with a NoSQL or message-bus adapter by exposing another {@link AuditSink} bean.
     */
    @Bean
    public AuditSink auditSink() {
        return new Slf4jAuditSink();
    }

    /**
     * Business logger that serializes structured events to JSON, masks sensitive content,
     * and forwards to the configured {@link AuditSink}.
     */
    @Bean
    public BusinessLogger businessLogger(ObjectMapper mapper,
                                         SensitiveDataMasker masker,
                                         AuditSink sink) {
        return new BusinessLogger(mapper, masker, sink);
    }

    /**
     * Registers the early servlet filter that ensures MDC holds correlation and request ids.
     */
    @Bean
    public FilterRegistrationBean<CorrelationIdFilter> correlationIdFilter() {
        var frb = new FilterRegistrationBean<CorrelationIdFilter>();
        frb.setFilter(new CorrelationIdFilter(props.getCorrelationHeader(), props.isEnsureCorrelationId()));
        frb.setOrder(Integer.MIN_VALUE + 100); // run very early
        return frb;
    }
}