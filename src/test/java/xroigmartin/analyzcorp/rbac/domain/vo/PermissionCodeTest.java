package xroigmartin.analyzcorp.rbac.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PermissionCodeTest {

    @Test
    void should_normalize_to_uppercase_and_validate_pattern() {
        PermissionCode p = PermissionCode.of(" account_read ");
        assertThat(p.value()).isEqualTo("ACCOUNT_READ");
    }

    @Test
    void should_reject_invalid_pattern_or_null() {
        assertThatThrownBy(() -> PermissionCode.of(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PermissionCode.of("with-dash"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PermissionCode.of("a"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}