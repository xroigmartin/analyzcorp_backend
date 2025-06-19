package xroigmartin.analyzcorp.finance.account.application;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xroigmartin.analyzcorp.finance.account.application.use_case.UpdateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountUpdateRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateAccountUseCaseTest {

    private final Faker faker = new Faker();

    @Mock
    private AccountUpdateRepository updateRepository;

    @InjectMocks
    private UpdateAccountUseCase useCase;

    @Test
    void givenValidAccount_whenExecute_thenReturnsUpdatedAccount() {
        // Given
        String name = faker.name().firstName();
        Account input = new Account(1L, name);
        Account expected = new Account(1L, name);
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