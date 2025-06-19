package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountCreateRepository;

@Service
@AllArgsConstructor
public class CreateAccountUseCase {

    private AccountCreateRepository accountCreateRepository;

    public Account execute(Account account) {
        return accountCreateRepository.create(account);
    }
}
