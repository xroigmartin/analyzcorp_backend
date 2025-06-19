package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountUpdateRepository;

@Service
@AllArgsConstructor
public class UpdateAccountUseCase {

    private final AccountUpdateRepository accountUpdateRepository;

    public Account execute(Account accountToUpdate) {
        return accountUpdateRepository.update(accountToUpdate);
    }
}
