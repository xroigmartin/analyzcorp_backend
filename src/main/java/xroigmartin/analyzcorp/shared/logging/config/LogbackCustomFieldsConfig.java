package xroigmartin.analyzcorp.shared.logging.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.logstash.logback.composite.GlobalCustomFieldsJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xroigmartin.analyzcorp.shared.logging.infrastructure.config.LoggingProperties;

/**
 * Registers custom fields for all log events without relying on XML configuration.
 */
@Configuration
public class LogbackCustomFieldsConfig {

    private final LoggingProperties props;

    public LogbackCustomFieldsConfig(LoggingProperties props) {
        this.props = props;
    }

    @Bean
    public GlobalCustomFieldsJsonProvider<ILoggingEvent> globalCustomFieldsJsonProvider(ObjectMapper objectMapper) {
        GlobalCustomFieldsJsonProvider<ch.qos.logback.classic.spi.ILoggingEvent> provider =
                new GlobalCustomFieldsJsonProvider<>();

        ObjectNode node = objectMapper.createObjectNode();
        node.putObject("service")
                .put("name", props.getServiceName() != null ? props.getServiceName() : "analyzcorp-backend")
                .put("version", props.getServiceVersion() != null ? props.getServiceVersion() : "0.1.0");
        node.put("env", props.getEnv() != null ? props.getEnv() : "dev");

        provider.setCustomFieldsNode(node);
        return provider;
    }
}
