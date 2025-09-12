package xroigmartin.analyzcorp.rbac.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class UserPublicIdTest {

    @Test
    void random_should_generate_non_null_uuid() {
        assertThat(UserPublicId.random().value()).isNotNull();
    }

    @Test
    void of_string_should_parse_uuid() {
        UUID id = UUID.randomUUID();
        assertThat(UserPublicId.of(id.toString()).value()).isEqualTo(id);
    }

    @Test
    void constructor_should_reject_null_uuid() {
        assertThatThrownBy(() -> new UserPublicId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be null");
    }
}