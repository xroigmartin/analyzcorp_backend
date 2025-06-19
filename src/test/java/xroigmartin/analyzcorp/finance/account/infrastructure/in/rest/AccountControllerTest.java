package xroigmartin.analyzcorp.finance.account.infrastructure.in.rest;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.CreateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAccountByIdUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAllAccountsUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.UpdateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.AccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.CreateAccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.UpdateAccountRequestDTO;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest extends BaseTest {

    @Mock
    private GetAllAccountsUseCase getAllAccountsUseCase;

    @Mock
    private GetAccountByIdUseCase getAccountByIdUseCase;

    @Mock
    private CreateAccountUseCase createAccountUseCase;

    @Mock
    private UpdateAccountUseCase updateUseCase;

    @InjectMocks
    private AccountController controller;

    @Test
    void getAccounts_shouldReturnListOfDtos() {
        // Given

        Account a1 = new Account(faker.number().randomNumber(), faker.name().firstName());
        Account a2 = new Account(faker.number().randomNumber(), faker.name().firstName());

        given(getAllAccountsUseCase.execute()).willReturn(Arrays.asList(a1, a2));

        // When
        ResponseEntity<List<AccountDTO>> response = controller.getAccounts();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<AccountDTO> dtos = response.getBody();
        assertThat(dtos).extracting(AccountDTO::id, AccountDTO::name)
                .containsExactlyInAnyOrder(
                        Tuple.tuple(a1.id(), a1.name()),
                        Tuple.tuple(a2.id(), a2.name())
                );
    }

    @Test
    void getAccountById_whenExists_thenReturnDto() {
        // Given
        long accountId = faker.number().randomNumber();
        String accountName = faker.name().firstName();
        Account a = new Account(accountId, accountName);
        given(getAccountByIdUseCase.execute(accountId)).willReturn(Optional.of(a));

        // When
        ResponseEntity<AccountDTO> response = controller.getAccountById(accountId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AccountDTO dto = response.getBody();
        assertThat(dto.id()).isEqualTo(accountId);
        assertThat(dto.name()).isEqualTo(accountName);
    }

    @Test
    void getAccountById_whenNotExists_thenReturnNotFound() {
        // Given
        long accountId = faker.number().randomNumber();
        given(getAccountByIdUseCase.execute(accountId)).willReturn(Optional.empty());

        // When
        ResponseEntity<AccountDTO> response = controller.getAccountById(accountId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void createAccount_shouldReturnCreatedDto() {
        // Given
        long accountId = faker.number().randomNumber();
        String accountName = faker.name().firstName();
        CreateAccountDTO req = new CreateAccountDTO(accountName);
        Account created = new Account(accountId, accountName);
        given(createAccountUseCase.execute(new Account(null, accountName)))
                .willReturn(created);

        // When
        ResponseEntity<AccountDTO> response = controller.createAccount(req);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        AccountDTO dto = response.getBody();
        assertThat(dto.id()).isEqualTo(accountId);
        assertThat(dto.name()).isEqualTo(accountName);
    }

    @Test
    void givenValidUpdate_whenPut_thenReturnsOkResponseWithData() {
        // Given
        Long id = faker.number().randomNumber();
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
        Long id = faker.number().randomNumber();
        UpdateAccountRequestDTO req = new UpdateAccountRequestDTO(faker.name().fullName());
        given(updateUseCase.execute(new Account(id, req.name())))
                .willThrow(new AccountNotFoundByIdException(id));

        // When / Then
        assertThrows(AccountNotFoundByIdException.class, () -> controller.updateAccount(id, req));
    }
}
