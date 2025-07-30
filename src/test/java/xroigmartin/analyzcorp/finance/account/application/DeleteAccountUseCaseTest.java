package xroigmartin.analyzcorp.finance.account.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.DeleteAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountDeleteRepository;

@ExtendWith(MockitoExtension.class)
public class DeleteAccountUseCaseTest extends BaseTest {

    @Mock
    private AccountDeleteRepository repository;

    @InjectMocks
    private DeleteAccountUseCase useCase;

    @Test
    void execute_whenAccountExists_thenDeletesSuccessfully() {
        // Given
        Long accountId = 1L;

        // When
        useCase.execute(accountId);

        // Then
        verify(repository).deleteById(accountId);
    }

    @Test
    void execute_whenAccountDoesNotExist_thenThrowsException() {
        // Given
        Long accountId = 99L;
        AccountNotFoundByIdException exception = new AccountNotFoundByIdException(accountId);
        willThrow(exception).given(repository).deleteById(accountId);

        // When / Then
        assertThatThrownBy(() -> useCase.execute(accountId))
                .isInstanceOf(AccountNotFoundByIdException.class)
                .hasMessageContaining(accountId.toString());
    }
}
