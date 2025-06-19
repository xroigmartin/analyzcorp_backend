package xroigmartin.analyzcorp.finance.account.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.CreateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountCreateRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseTest extends BaseTest {

    @Mock
    private AccountCreateRepository accountCreateRepository;

    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    @Test
    void givenValidAccount_whenExecute_thenReturnsCreatedAccount() {
        // Given
        String accountName = faker.name().firstName();
        Account input = new Account(null, accountName);
        Account created = new Account(1L, accountName);
        given(accountCreateRepository.create(input)).willReturn(created);

        // When
        Account result = createAccountUseCase.execute(input);

        // Then
        assertThat(result).isEqualTo(created);
    }

    @Test
    void whenRepositoryThrowsException_thenPropagateException() {
        // Given
        Account input = new Account(null, faker.name().firstName());
        given(accountCreateRepository.create(input))
                .willThrow(new RuntimeException("Creation failed"));

        // When / Then
        assertThatThrownBy(() -> createAccountUseCase.execute(input))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Creation failed");
    }
}

