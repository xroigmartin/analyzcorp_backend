package xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.entity.AccountEntity;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
class AccountPersistenceAdapterTest extends BaseTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountPersistenceAdapter adapter;

    @Test
    void getAllAccounts_returnsListOfAccounts() {
        AccountEntity e1 = new AccountEntity(faker.number().randomNumber(), faker.name().firstName());
        AccountEntity e2 = new AccountEntity(faker.number().randomNumber(), faker.name().firstName());
        given(accountRepository.findAll()).willReturn(Arrays.asList(e1, e2));

        List<Account> result = adapter.getAllAccounts();

        assertThat(result)
                .extracting(Account::id, Account::name)
                .containsExactly(
                        org.assertj.core.groups.Tuple.tuple(e1.getId(), e1.getName()),
                        org.assertj.core.groups.Tuple.tuple(e2.getId(), e2.getName())
                );
    }

    @Test
    void getAllAccounts_whenNoEntities_thenReturnsEmptyList() {
        // Given
        given(accountRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<Account> result = adapter.getAllAccounts();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void create_savesAndReturnsAccount() {
        // Given
        long accountId = faker.number().randomNumber();
        String accountName = faker.name().firstName();
        given(accountRepository.save(any(AccountEntity.class)))
                .willAnswer(inv -> {
                    AccountEntity arg = inv.getArgument(0);
                    arg.setId(accountId);
                    return arg;
                });

        // When
        Account result = adapter.create(new Account(null, accountName));

        // Then
        assertThat(result.id()).isEqualTo(accountId);
        assertThat(result.name()).isEqualTo(accountName);
    }

    @Test
    void findById_returnsAccount_whenFound() {
        long accountId = faker.number().randomNumber();
        String accountName = faker.name().firstName();
        AccountEntity entity = new AccountEntity(accountId, accountName);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(entity));

        Optional<Account> result = adapter.findById(accountId);

        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(accountId);
        assertThat(result.get().name()).isEqualTo(accountName);
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        long accountId = faker.number().randomNumber();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        Optional<Account> result = adapter.findById(accountId);

        assertThat(result).isEmpty();
    }

    @Test
    void update_updatesAndReturnsAccount() {
        long accountId = faker.number().randomNumber();
        String accountOldName = faker.name().firstName();
        String accountNewName = faker.name().firstName();
        Account input = new Account(accountId, accountNewName);

        AccountEntity existing = new AccountEntity(accountId, accountOldName);
        AccountEntity updated = new AccountEntity(accountId, accountNewName);

        when(accountRepository.getReferenceById(accountId)).thenReturn(existing);
        when(accountRepository.save(existing)).thenReturn(updated);

        Account result = adapter.update(input);

        assertThat(result.id()).isEqualTo(accountId);
        assertThat(result.name()).isEqualTo(accountNewName);
    }

    @Test
    void deleteById_deletesAccount() {
        Long id = faker.number().randomNumber();

        adapter.deleteById(id);

        verify(accountRepository).deleteById(id);
    }

    @Test
    void existsAccountByName_returnsTrue() {
        String name = faker.name().firstName();
        when(accountRepository.existsByName(name)).thenReturn(true);

        boolean result = adapter.existsAccountByName(name);

        assertThat(result).isTrue();
    }

    @Test
    void existsAccountByName_returnsFalse() {
        String name = faker.name().firstName();
        when(accountRepository.existsByName(name)).thenReturn(false);

        boolean result = adapter.existsAccountByName(name);

        assertThat(result).isFalse();
    }

    @Test
    void existsAccountById_returnsFalse() {
        Long id = faker.number().randomNumber();

        when(accountRepository.existsById(id)).thenReturn(false);

        boolean result = adapter.existsAccountById(id);

        assertThat(result).isFalse();
    }

    @Test
    void existsAccountById_returnsTrue() {
        Long id = faker.number().randomNumber();

        when(accountRepository.existsById(id)).thenReturn(true);

        boolean result = adapter.existsAccountById(id);

        assertThat(result).isTrue();
    }
}

