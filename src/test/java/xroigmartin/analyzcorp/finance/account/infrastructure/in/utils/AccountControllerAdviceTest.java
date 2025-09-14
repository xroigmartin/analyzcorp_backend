package xroigmartin.analyzcorp.finance.account.infrastructure.in.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.domain.events.AccountAction;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.AnalyzCorpApiError;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.AnalyzCorpApiResponse;

class AccountControllerAdviceTest extends BaseTest {

    private final AccountControllerAdvice advice = new AccountControllerAdvice();

    @Test
    void whenAccountNotFound_thenReturnsNotFoundResponse() {
        // Given
        AccountNotFoundByIdException ex = new AccountNotFoundByIdException(faker.number().randomNumber(), AccountAction.FINANCE_ACCOUNT_UPDATED);

        // When
        ResponseEntity<AnalyzCorpApiResponse<Void>> response = advice.handleNotFound(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        AnalyzCorpApiResponse<Void> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getData()).isNull();
        AnalyzCorpApiError error = body.getError();
        assertThat(error.getCode()).isEqualTo("ACCOUNT_NOT_FOUND");
        assertThat(error.getMessage()).isEqualTo(ex.getMessage());
        // Timestamp should be ISO-8601 UTC string
        assertThat(body.getTimestamp()).matches(
                Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+Z?"));
    }
}
