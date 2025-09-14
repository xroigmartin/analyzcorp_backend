package xroigmartin.analyzcorp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xroigmartin.analyzcorp.shared.logging.infrastructure.config.LoggingProperties;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class AnalyzCorpLoggingConfig {
}
