package xroigmartin.analyzcorp.rbac.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class UsernameTest {

    @Test
    void should_normalize_to_lowercase_and_trim() {
        Username u = Username.of("  Xavi_Roig  ");
        assertThat(u.value()).isEqualTo("xavi_roig");
    }

    @Test
    void should_reject_blank_or_out_of_bounds() {
        assertThatThrownBy(() -> Username.of("  "))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Username.of("ab"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Username.of("x".repeat(121)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Disabled("Enable when username whitelist pattern is active")
    @Test
    void should_reject_usernames_outside_whitelist() {
        assertThatThrownBy(() -> Username.of("has-dash")) // '-'
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Username.of("UpperCase"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Username.of("spa ce"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
