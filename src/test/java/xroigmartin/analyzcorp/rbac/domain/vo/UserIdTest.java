package xroigmartin.analyzcorp.rbac.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;

@DisplayName("UserId VO")
class UserIdTest {

    @Test
    void should_accept_positive_values() {
        assertThat(new UserId(1).value()).isEqualTo(1);
        assertThat(UserId.of(10).value()).isEqualTo(10);
    }

    @Test
    void should_reject_zero_or_negative() {
        assertThatThrownBy(() -> new UserId(0))
                .isInstanceOf(InvariantViolation.class);
        assertThatThrownBy(() -> UserId.of(-1))
                .isInstanceOf(InvariantViolation.class);
    }
}