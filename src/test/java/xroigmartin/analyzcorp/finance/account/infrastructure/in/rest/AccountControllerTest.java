package xroigmartin.analyzcorp.finance.account.infrastructure.in.rest;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAccountByIdUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.UpdateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.AccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.UpdateAccountRequestDTO;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private final Faker faker = new Faker();

    @Mock
    private GetAccountByIdUseCase getAccountByIdUseCase;

    @Mock
    private UpdateAccountUseCase updateUseCase;

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

    @Test
    void givenValidUpdate_whenPut_thenReturnsOkResponseWithData() {
        // Given
        Long id = 1L;
        String name = faker.name().fullName();
        UpdateAccountRequestDTO req = new UpdateAccountRequestDTO(name);
        Account updated = new Account(id, name);
        given(updateUseCase.execute(new Account(id, req.name()))).willReturn(updated);

        // When
        var response = controller.updateAccount(id, req);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ApiResponse<AccountDTO> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getError()).isNull();
        assertThat(body.getData()).extracting(AccountDTO::id, AccountDTO::name)
                .containsExactly(id, name);
        assertThat(body.getTimestamp()).isNotNull();
    }

    @Test
    void givenNonExistingUpdate_whenPut_thenThrowsAccountNotFoundByIdException() {
        // Given
        Long id = 2L;
        UpdateAccountRequestDTO req = new UpdateAccountRequestDTO(faker.name().fullName());
        given(updateUseCase.execute(new Account(id, req.name())))
                .willThrow(new AccountNotFoundByIdException(id));

        // When / Then
        assertThrows(AccountNotFoundByIdException.class, () -> controller.updateAccount(id, req));
    }
}
