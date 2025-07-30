package xroigmartin.analyzcorp.finance.account.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAccountByIdUseCase;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetByIdRepository;

@ExtendWith(MockitoExtension.class)
class GetAccountByIdUseCaseTest extends BaseTest {

    @Mock
    private AccountGetByIdRepository accountGetByIdRepository;

    @InjectMocks
    private GetAccountByIdUseCase useCase;

    @Test
    void execute_whenAccountExists_returnsAccount() {
        // Given
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        Account expected = new Account(id, name);

        given(accountGetByIdRepository.findById(id)).willReturn(Optional.of(expected));

        // When
        Account result = useCase.execute(id);

        // Then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void execute_whenAccountDoesNotExist_throwsException() {
        // Given
        Long id = faker.number().randomNumber();
        given(accountGetByIdRepository.findById(id)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(AccountNotFoundByIdException.class)
                .hasMessageContaining(id.toString());
    }
}