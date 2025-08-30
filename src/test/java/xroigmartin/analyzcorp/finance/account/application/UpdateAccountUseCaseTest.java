package xroigmartin.analyzcorp.finance.account.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.UpdateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNameAlreadyExistsException;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountExistsRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountUpdateRepository;

@ExtendWith(MockitoExtension.class)
class UpdateAccountUseCaseTest extends BaseTest {

    @Mock
    private AccountUpdateRepository accountUpdateRepository;

    @Mock
    private AccountExistsRepository accountExistsRepository;

    @InjectMocks
    private UpdateAccountUseCase useCase;

    @Test
    void execute_whenAccountExistsAndNameIsAvailable_updatesSuccessfully() {
        // Given
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        Account input = new Account(id, name);
        Account updated = new Account(id, name + " updated");

        given(accountExistsRepository.existsAccountById(id)).willReturn(true);
        given(accountExistsRepository.existsAccountByName(name)).willReturn(false);
        given(accountUpdateRepository.update(input)).willReturn(updated);

        // When
        Account result = useCase.execute(input);

        // Then
        assertThat(result).isEqualTo(updated);
    }

    @Test
    void execute_whenAccountDoesNotExist_throwsAccountNotFoundException() {
        // Given
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        Account input = new Account(id, name);

        given(accountExistsRepository.existsAccountById(id)).willReturn(false);

        // When / Then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(AccountNotFoundByIdException.class)
                .hasMessageContaining(id.toString());

        verify(accountUpdateRepository, never()).update(any());
    }

    @Test
    void execute_whenAccountNameAlreadyExists_throwsAccountNameAlreadyExistsException() {
        // Given
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        Account input = new Account(id, name);

        given(accountExistsRepository.existsAccountById(id)).willReturn(true);
        given(accountExistsRepository.existsAccountByName(name)).willReturn(true);

        // When / Then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(AccountNameAlreadyExistsException.class)
                .hasMessageContaining(name);

        verify(accountUpdateRepository, never()).update(any());
    }
}