package xroigmartin.analyzcorp.finance.account.application;

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
import xroigmartin.analyzcorp.finance.account.application.use_case.DeleteAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountDeleteRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountExistsRepository;

@ExtendWith(MockitoExtension.class)
public class DeleteAccountUseCaseTest extends BaseTest {

    @Mock
    private AccountDeleteRepository accountDeleteRepository;

    @Mock
    private AccountExistsRepository accountExistsRepository;

    @InjectMocks
    private DeleteAccountUseCase useCase;

    @Test
    void execute_whenAccountExists_deletesSuccessfully() {
        // Given
        Long id = faker.number().randomNumber();
        given(accountExistsRepository.existsAccountById(id)).willReturn(true);

        // When
        useCase.execute(id);

        // Then
        verify(accountDeleteRepository).deleteById(id);
    }

    @Test
    void execute_whenAccountDoesNotExist_throwsException() {
        // Given
        Long id = faker.number().randomNumber();
        given(accountExistsRepository.existsAccountById(id)).willReturn(false);

        // When / Then
        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(AccountNotFoundByIdException.class)
                .hasMessageContaining(id.toString());

        verify(accountDeleteRepository, never()).deleteById(any());
    }
}
