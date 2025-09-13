package xroigmartin.analyzcorp.shared.logging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.shared.logging.domain.mask.SensitiveDataMasker;

/**
 * Unit tests for SensitiveDataMasker using random values from Faker.
 */
class SensitiveDataMaskerTest extends BaseTest {

    @Test
    void masksEmailsTokensIbanCardsPhones() {
        var m = SensitiveDataMasker.defaultMasker();

        // Email
        String email = faker.internet().emailAddress();
        assertThat(m.mask("Contact me at " + email))
                .doesNotContain(email)
                .contains(SensitiveDataMasker.DEFAULT_MASK);

        // Bearer/JWT token (approximate)
        String token = "Bearer " + faker.regexify("[A-Za-z0-9]{10}\\.[A-Za-z0-9]{10}\\.[A-Za-z0-9]{5}");
        assertThat(m.mask("Authorization: " + token))
                .doesNotContain(token)
                .contains(SensitiveDataMasker.DEFAULT_MASK);

        // IBAN (generic format)
        String iban = faker.finance().iban();
        assertThat(m.mask("iban is " + iban))
                .doesNotContain(iban)
                .contains(SensitiveDataMasker.DEFAULT_MASK);

        // Credit card number
        String cc = faker.finance().creditCard(); // may contain dashes, strip them
        cc = cc.replaceAll("[^0-9]", "");
        String visaLike = "4111111111111111"; // ensure we test against VISA pattern
        assertThat(m.mask("VISA " + visaLike))
                .doesNotContain(visaLike)
                .contains(SensitiveDataMasker.DEFAULT_MASK);

        // Phone number (E.164-ish)
        String phone = "+1" + faker.number().digits(10);
        assertThat(m.mask("Call me at " + phone))
                .doesNotContain(phone)
                .contains(SensitiveDataMasker.DEFAULT_MASK);
    }
}