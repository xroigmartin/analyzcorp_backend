package xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.entity.AccountEntity;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.repository.AccountRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountPersistenceAdapterTest extends BaseTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountPersistenceAdapter adapter;

    @Test
    void getAllAccounts_whenEntitiesExist_thenReturnsDtos() {
        // Given
        AccountEntity e1 = new AccountEntity(faker.number().randomNumber(), faker.name().firstName());
        AccountEntity e2 = new AccountEntity(faker.number().randomNumber(), faker.name().firstName());
        given(accountRepository.findAll()).willReturn(Arrays.asList(e1, e2));

        // When
        List<Account> result = adapter.getAllAccounts();

        // Then
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
    void create_whenValidAccount_thenSavesAndReturnsDto() {
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
    void findById_whenEntityExists_thenReturnsDto() {
        // Given
        long accountId = faker.number().randomNumber();
        String accountName = faker.name().firstName();
        AccountEntity entity = new AccountEntity(accountId, accountName);
        given(accountRepository.findById(accountId)).willReturn(Optional.of(entity));

        // When
        Optional<Account> result = adapter.findById(accountId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(accountId);
        assertThat(result.get().name()).isEqualTo(accountName);
    }

    @Test
    void findById_whenEntityNotExists_thenReturnsEmpty() {
        // Given
        long accountId = faker.number().randomNumber();
        given(accountRepository.findById(accountId)).willReturn(Optional.empty());

        // When
        Optional<Account> result = adapter.findById(accountId);

        // Then
        assertThat(result).isNotPresent();
    }

    @Test
    void update_whenEntityExists_thenUpdatesAndReturnsDto() {
        // Given
        long accountId = faker.number().randomNumber();
        String accountOldName = faker.name().firstName();
        String accountNewName = faker.name().firstName();

        AccountEntity entity = new AccountEntity(accountId, accountOldName);
        given(accountRepository.findById(accountId)).willReturn(Optional.of(entity));
        AccountEntity saved = new AccountEntity(accountId, accountNewName);
        given(accountRepository.save(entity)).willReturn(saved);

        // When
        Account result = adapter.update(new Account(accountId, accountNewName));

        // Then
        assertThat(result.id()).isEqualTo(accountId);
        assertThat(result.name()).isEqualTo(accountNewName);
    }

    @Test
    void update_whenEntityNotExists_thenThrowsException() {
        // Given
        long accountId = faker.number().randomNumber();
        given(accountRepository.findById(accountId)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> adapter.update(new Account(accountId, faker.name().firstName())))
                .isInstanceOf(AccountNotFoundByIdException.class)
                .hasMessageContaining(String.format("Account with id %d not found", accountId));
    }
}

