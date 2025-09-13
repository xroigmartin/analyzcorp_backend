package xroigmartin.analyzcorp.shared.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;

import java.util.List;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.shared.logging.application.BusinessLogger;
import xroigmartin.analyzcorp.shared.logging.domain.Markers;
import xroigmartin.analyzcorp.shared.logging.domain.enums.EventCategory;
import xroigmartin.analyzcorp.shared.logging.domain.enums.Outcome;
import xroigmartin.analyzcorp.shared.logging.domain.mask.SensitiveDataMasker;
import xroigmartin.analyzcorp.shared.logging.domain.model.BusinessEvent;
import xroigmartin.analyzcorp.shared.logging.infrastructure.Slf4jAuditSink;

class BusinessRoutingAndMaskingTest extends BaseTest {

    @Test
    void businessEventsReachRootAndAreMasked_withBusinessMarker() {
        var ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
        var root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);

        var listAppender = new ListAppender<ILoggingEvent>();
        listAppender.start();
        root.addAppender(listAppender);

        // SUT
        var sink = new Slf4jAuditSink();
        var bl = new BusinessLogger(mapper, SensitiveDataMasker.defaultMasker(), sink);

        var event = BusinessEvent.builder()
                .category(EventCategory.BUSINESS)
                .action("TEST_" + faker.number().digits(4))
                .message("contact john.doe@example.com, token Bearer abc.def.ghi")
                .actorName("system")
                .actorId(faker.internet().uuid())
                .entityType("USER")
                .entityId(faker.internet().uuid())
                .outcome(Outcome.SUCCESS)
                .build();

        bl.infoBusiness(event);

        List<ILoggingEvent> all = listAppender.list;
        var businessEvents = all.stream()
                .filter(e -> "xroigmartin.analyzcorp.audit.Business".equals(e.getLoggerName()))
                .toList();

        assertThat(businessEvents)
                .withFailMessage("No se capturó ningún evento del logger de negocio. Eventos capturados: %s", all)
                .hasSize(1);

        var logged = businessEvents.get(0);

        assertThat(logged.getMarker()).isEqualTo(Markers.BUSINESS);

        var formatted = logged.getFormattedMessage();
        assertThat(formatted).contains("***");
        assertThat(formatted).doesNotContain("john.doe@example.com");
        assertThat(formatted).doesNotContain("abc.def.ghi");
    }
}