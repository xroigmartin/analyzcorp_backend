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
import xroigmartin.analyzcorp.finance.account.application.use_case.CreateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNameAlreadyExistsException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountCreateRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountExistsRepository;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseTest extends BaseTest {

    @Mock
    private AccountCreateRepository accountCreateRepository;

    @Mock
    private AccountExistsRepository accountExistsRepository;

    @InjectMocks
    private CreateAccountUseCase useCase;

    @Test
    void execute_whenAccountDoesNotExist_createsAccountSuccessfully() {
        // Given
        String name = faker.name().firstName();
        Account input = new Account(null, name);
        Account created = new Account(1L, name);

        given(accountExistsRepository.existsAccountByName(name)).willReturn(false);
        given(accountCreateRepository.create(input)).willReturn(created);

        // When
        Account result = useCase.execute(input);

        // Then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo(name);
    }

    @Test
    void execute_whenAccountNameAlreadyExists_throwsException() {
        // Given
        String name = faker.name().firstName();
        Account input = new Account(null, name);

        given(accountExistsRepository.existsAccountByName(name)).willReturn(true);

        // When / Then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(AccountNameAlreadyExistsException.class)
                .hasMessageContaining(name);

        verify(accountCreateRepository, never()).create(any());
    }
}

