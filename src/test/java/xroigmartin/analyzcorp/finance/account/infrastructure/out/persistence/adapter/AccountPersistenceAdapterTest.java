package xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.adapter;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.entity.AccountEntity;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @Test
    void givenExistingEntity_whenUpdate_thenReturnsUpdatedAccount() {
        // Given
        Long id = 1L;
        String oldName = "Old Name";
        String newName = "New Name";
        AccountEntity entity = new AccountEntity();
        entity.setId(id);
        entity.setName(oldName);
        AccountEntity savedEntity = new AccountEntity();
        savedEntity.setId(id);
        savedEntity.setName(newName);
        given(accountRepository.findById(id)).willReturn(Optional.of(entity));
        given(accountRepository.save(entity)).willReturn(savedEntity);

        // When
        Account result = adapter.update(new Account(id, newName));

        // Then
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.name()).isEqualTo(newName);
    }

    @Test
    void givenNonExistingEntity_whenUpdate_thenThrowsAccountNotFoundByIdException() {
        // Given
        Long id = faker.number().randomNumber();
        given(accountRepository.findById(id)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> adapter.update(new Account(id, "Name")))
                .isInstanceOf(AccountNotFoundByIdException.class)
                .hasMessageContaining(String.valueOf(id));
    }
}

