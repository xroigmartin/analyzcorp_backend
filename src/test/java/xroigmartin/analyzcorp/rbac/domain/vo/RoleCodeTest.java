package xroigmartin.analyzcorp.rbac.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RoleCodeTest {

    @Test
    void should_normalize_to_uppercase_and_validate_pattern() {
        RoleCode c = RoleCode.of(" admin_user ");
        assertThat(c.value()).isEqualTo("ADMIN_USER");
    }

    @Test
    void should_reject_invalid_pattern_or_null() {
        assertThatThrownBy(() -> RoleCode.of(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> RoleCode.of("lower-case"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> RoleCode.of("too-long-".repeat(10)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
