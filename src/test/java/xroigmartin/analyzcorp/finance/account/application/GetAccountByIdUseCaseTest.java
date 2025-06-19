package xroigmartin.analyzcorp.finance.account.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAccountByIdUseCase;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetByIdRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class GetAccountByIdUseCaseTest extends BaseTest {

    @Mock
    private AccountGetByIdRepository repository;

    @InjectMocks
    private GetAccountByIdUseCase useCase;

    @Test
    void givenExistingAccount_whenExecute_thenReturnsAccount() {
        // Given
        Long id = faker.number().randomNumber(5, false);
        String name = faker.name().fullName();
        Account account = new Account(id, name);
        given(repository.findById(id)).willReturn(Optional.of(account));

        // When
        Optional<Account> result = useCase.execute(id);

        // Then
        then(repository).should().findById(id);
        assertThat(result).isPresent()
                .contains(account);
    }

    @Test
    void givenNonExistingAccount_whenExecute_thenReturnsEmpty() {
        // Given
        Long id = faker.number().randomNumber(5, false);
        given(repository.findById(id)).willReturn(Optional.empty());

        // When
        Optional<Account> result = useCase.execute(id);

        // Then
        then(repository).should().findById(id);
        assertThat(result).isNotPresent();
    }
}