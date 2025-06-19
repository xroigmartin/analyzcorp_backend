package xroigmartin.analyzcorp.finance.account.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.UpdateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountUpdateRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateAccountUseCaseTest extends BaseTest {

    @Mock
    private AccountUpdateRepository updateRepository;

    @InjectMocks
    private UpdateAccountUseCase useCase;

    @Test
    void givenValidAccount_whenExecute_thenReturnsUpdatedAccount() {
        // Given
        long accountId = faker.number().randomNumber();
        String name = faker.name().firstName();
        Account input = new Account(accountId, name);
        Account expected = new Account(accountId, name);
        given(updateRepository.update(input)).willReturn(expected);

        // When
        Account result = useCase.execute(input);

        // Then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenNonExistingAccount_whenExecute_thenThrowsException() {
        // Given
        long id = faker.number().randomNumber();
        Account input = new Account(id, faker.name().firstName());

        given(updateRepository.update(input)).willThrow(new RuntimeException(String.format("Account not found: %d", id)));

        // When / Then
        assertThrows(RuntimeException.class, () -> useCase.execute(input));
    }
}