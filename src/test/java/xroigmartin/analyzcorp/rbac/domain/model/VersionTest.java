package xroigmartin.analyzcorp.rbac.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class VersionTest {

    @Test
    void zero_and_next_should_work() {
        Version v0 = Version.zero();
        assertThat(v0.value()).isZero();

        Version v1 = v0.next();
        assertThat(v1.value()).isEqualTo(1);
        assertThat(v0.value()).isZero(); // immutable
    }

    @Test
    void should_reject_negative_values() {
        assertThatThrownBy(() -> new Version(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}