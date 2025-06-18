package xroigmartin.analyzcorp;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.adapter.AccountPersistenceAdapter;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.entity.AccountEntity;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AccountPersistenceAdapterTest {

    private final Faker faker = new Faker();

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountPersistenceAdapter adapter;

    @Test
    void givenExistingEntity_whenGetById_thenReturnsDomainAccount() {
        // Given
        Long id = faker.number().randomNumber(5, false);
        String name = faker.name().fullName();
        AccountEntity entity = new AccountEntity();
        entity.setId(id);
        entity.setName(name);
        given(accountRepository.findById(id)).willReturn(Optional.of(entity));

        // When
        Optional<Account> result = adapter.findById(id);

        // Then
        then(accountRepository).should().findById(id);
        assertThat(result).isPresent();
        Account account = result.get();
        assertThat(account.id()).isEqualTo(id);
        assertThat(account.name()).isEqualTo(name);
    }

    @Test
    void givenNonExistingEntity_whenGetById_thenReturnsEmpty() {
        // Given
        Long id = faker.number().randomNumber(5, false);
        given(accountRepository.findById(id)).willReturn(Optional.empty());

        // When
        Optional<Account> result = adapter.findById(id);

        // Then
        then(accountRepository).should().findById(id);
        assertThat(result).isNotPresent();
    }
}

