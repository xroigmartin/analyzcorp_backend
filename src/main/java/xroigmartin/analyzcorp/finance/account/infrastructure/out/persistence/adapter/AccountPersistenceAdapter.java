package xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountCreateRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountDeleteRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountExistsRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetAllRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetByIdRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountUpdateRepository;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.entity.AccountEntity;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.repository.AccountRepository;

@Service
@AllArgsConstructor
public class AccountPersistenceAdapter implements AccountGetAllRepository, AccountCreateRepository,
        AccountGetByIdRepository, AccountUpdateRepository, AccountDeleteRepository, AccountExistsRepository {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        var accounts =  accountRepository.findAll();
        return accounts.stream().map(this::toDto).toList();
    }

    private Account toDto(AccountEntity accountEntity) {
        return new Account(accountEntity.getId(), accountEntity.getName());
    }

    @Override
    public Account create(Account account) {
        var accountEntity = new AccountEntity(null, account.name());
        accountRepository.save(accountEntity);
        return toDto(accountEntity);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository
                .findById(id)
                .map(this::toDto);
    }

    @Override
    public Account update(Account account) {
        Long id = account.id();

        AccountEntity accountEntity = accountRepository.getReferenceById(id);

        accountEntity.setName(account.name());

        AccountEntity updatedAccountEntity = accountRepository.save(accountEntity);

        return toDto(updatedAccountEntity);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public boolean existsAccountByName(String name) {
        return accountRepository.existsByName(name);
    }

    @Override
    public boolean existsAccountById(Long id) {
        return accountRepository.existsById(id);
    }
}
