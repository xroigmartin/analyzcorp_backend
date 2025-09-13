package shared.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base class for unit tests providing shared utilities like {@link Faker}
 * and a preconfigured {@link ObjectMapper} with JavaTime support.
 */
public abstract class BaseTest {

    protected static Faker faker;
    protected static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        faker = new Faker();
        mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

}
