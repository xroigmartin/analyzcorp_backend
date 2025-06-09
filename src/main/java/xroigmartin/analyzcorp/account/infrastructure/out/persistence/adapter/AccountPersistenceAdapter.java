package xroigmartin.analyzcorp.account.infrastructure.out.persistence.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.account.domain.model.Account;
import xroigmartin.analyzcorp.account.domain.repository.AccountCreateRepository;
import xroigmartin.analyzcorp.account.domain.repository.AccountGetAllRepository;
import xroigmartin.analyzcorp.account.infrastructure.out.persistence.entity.AccountEntity;
import xroigmartin.analyzcorp.account.infrastructure.out.persistence.repository.AccountRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountPersistenceAdapter implements AccountGetAllRepository, AccountCreateRepository {

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
}
