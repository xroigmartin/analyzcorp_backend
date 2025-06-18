package xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountCreateRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetAllRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetByIdRepository;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.entity.AccountEntity;
import xroigmartin.analyzcorp.finance.account.infrastructure.out.persistence.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountPersistenceAdapter implements AccountGetAllRepository, AccountCreateRepository, AccountGetByIdRepository {

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
    public Account save(Account account) {
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
}
