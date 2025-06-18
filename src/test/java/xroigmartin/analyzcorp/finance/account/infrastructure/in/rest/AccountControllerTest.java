package xroigmartin.analyzcorp.finance.account.infrastructure.in.rest;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAccountByIdUseCase;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.AccountDTO;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private final Faker faker = new Faker();

    @Mock
    private GetAccountByIdUseCase getAccountByIdUseCase;

    @InjectMocks
    private AccountController controller;

    @Test
    void givenExistingAccount_whenGetAccountById_thenReturnsOkAndDto() {
        // Given
        Long id = faker.number().randomNumber(5, false);
        String name = faker.name().fullName();
        Account account = new Account(id, name);
        given(getAccountByIdUseCase.execute(id)).willReturn(Optional.of(account));

        // When
        ResponseEntity<AccountDTO> response = controller.getAccountById(id);

        // Then
        then(getAccountByIdUseCase).should().execute(id);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody())
                .extracting(AccountDTO::id, AccountDTO::name)
                .containsExactly(id, name);
    }

    @Test
    void givenNonExistingAccount_whenGetAccountById_thenReturnsNotFound() {
        // Given
        Long id = faker.number().randomNumber(5, false);
        given(getAccountByIdUseCase.execute(id)).willReturn(Optional.empty());

        // When
        ResponseEntity<AccountDTO> response = controller.getAccountById(id);

        // Then
        then(getAccountByIdUseCase).should().execute(id);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isNull();
    }
}
