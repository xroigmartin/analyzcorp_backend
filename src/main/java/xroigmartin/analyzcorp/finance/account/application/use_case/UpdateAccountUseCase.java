package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNameAlreadyExistsException;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountExistsRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountUpdateRepository;

@Service
@AllArgsConstructor
public class UpdateAccountUseCase {

    private final AccountUpdateRepository accountUpdateRepository;
    private final AccountExistsRepository accountExistsRepository;

    public Account execute(Account accountToUpdate) {

        if(!accountExistsRepository.existsAccountById(accountToUpdate.id())){
            throw new AccountNotFoundByIdException(accountToUpdate.id());
        }

        if(accountExistsRepository.existsAccountByName(accountToUpdate.name())){
            throw new AccountNameAlreadyExistsException(accountToUpdate.name());
        }

        return accountUpdateRepository.update(accountToUpdate);
    }
}
