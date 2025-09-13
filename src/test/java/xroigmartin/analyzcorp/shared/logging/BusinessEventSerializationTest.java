package xroigmartin.analyzcorp.shared.logging;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.shared.logging.domain.enums.EventCategory;
import xroigmartin.analyzcorp.shared.logging.domain.enums.Outcome;
import xroigmartin.analyzcorp.shared.logging.domain.model.BusinessEvent;

class BusinessEventSerializationTest extends BaseTest {

    @Test
    void builderProducesSerializableSchema_withRandomData() throws Exception {
        String action = "TEST_" + faker.number().digits(4);
        String message = faker.lorem().sentence(4);
        String actorName = faker.internet().domainWord();      // evita PII real
        String actorId = faker.internet().uuid();
        String entityType = faker.options().option("USER", "ROLE", "ACCOUNT", "TRANSACTION");
        String entityId = faker.internet().uuid();
        String targetId = faker.options().option("ADMIN", "VIEWER", "EDITOR", null);

        var event = BusinessEvent.builder()
                .category(EventCategory.BUSINESS)
                .action(action)
                .message(message)
                .actorName(actorName)
                .actorId(actorId)
                .entityType(entityType)
                .entityId(entityId)
                .targetId(targetId)
                .outcome(Outcome.SUCCESS)
                .build();

        String json = mapper.writeValueAsString(event);

        assertThat(json).contains("\"category\":\"BUSINESS\"");
        assertThat(json).contains("\"action\":\"" + action + "\"");
        assertThat(json).contains("\"message\":\"" + message + "\"");

        assertThat(json).contains("\"actorName\":\"" + actorName + "\"");
        assertThat(json).contains("\"actorId\":\"" + actorId + "\"");

        assertThat(json).contains("\"entityType\":\"" + entityType + "\"");
        assertThat(json).contains("\"entityId\":\"" + entityId + "\"");

        if (targetId != null) {
            assertThat(json).contains("\"targetId\":\"" + targetId + "\"");
        }

        assertThat(json).contains("\"outcome\":\"SUCCESS\"");
        assertThat(json).contains("\"timestamp\"");
    }
}
