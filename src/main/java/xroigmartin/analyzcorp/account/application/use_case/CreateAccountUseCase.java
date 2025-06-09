package xroigmartin.analyzcorp.account.application.use_case;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.account.domain.model.Account;
import xroigmartin.analyzcorp.account.domain.repository.AccountCreateRepository;

@Service
@AllArgsConstructor
public class CreateAccountUseCase {

    private AccountCreateRepository accountCreateRepository;

    public Account execute(Account account) {
        return accountCreateRepository.save(account);
    }
}
