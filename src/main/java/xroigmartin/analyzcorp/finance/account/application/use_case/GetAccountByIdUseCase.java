package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetByIdRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAccountByIdUseCase {

    private final AccountGetByIdRepository accountGetByIdRepository;

    public Optional<Account> execute(Long id) {
        return accountGetByIdRepository.findById(id);
    }
}
