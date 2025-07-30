package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetByIdRepository;


@Service
@RequiredArgsConstructor
public class GetAccountByIdUseCase {

    private final AccountGetByIdRepository accountGetByIdRepository;

    public Account execute(Long id) {
        return accountGetByIdRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundByIdException(id));
    }
}
