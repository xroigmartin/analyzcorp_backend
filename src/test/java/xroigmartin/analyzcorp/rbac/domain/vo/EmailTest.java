package xroigmartin.analyzcorp.rbac.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void should_normalize_to_lowercase_and_trim() {
        Email e = Email.of("  John.DOE@Example.COM  ");
        assertThat(e.value()).isEqualTo("john.doe@example.com");
    }

    @Test
    void should_reject_invalid_emails() {
        assertThatThrownBy(() -> Email.of("no-at-symbol"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Email.of("a@b"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Email.of("a@b."))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_reject_too_long_email() {
        String local = "a".repeat(64);
        String domain = "b".repeat(185); // 64 + 1 + 185 + 4(".com") = 254 OK; 255 KO
        String email255 = local + "@" + domain + ".com" + "x"; // 255+
        assertThatThrownBy(() -> Email.of(email255))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("too long");
    }

}