package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.events.AccountAction;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNameAlreadyExistsException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountCreateRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountExistsRepository;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountCreateRepository accountCreateRepository;
    private final AccountExistsRepository accountExistsRepository;

    public Account execute(Account account) {
        if(accountExistsRepository.existsAccountByName(account.name())){
            throw new AccountNameAlreadyExistsException(AccountAction.FINANCE_ACCOUNT_CREATED);
        }

        return accountCreateRepository.create(account);
    }
}
