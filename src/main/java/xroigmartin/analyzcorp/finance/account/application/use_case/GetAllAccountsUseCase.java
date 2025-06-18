package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetAllRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllAccountsUseCase {

    private final AccountGetAllRepository accountGetAllRepository;

    public List<Account> execute() {
        return accountGetAllRepository.getAllAccounts();
    }
}
