package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountDeleteRepository;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountExistsRepository;

@Service
@RequiredArgsConstructor
public class DeleteAccountUseCase {

    private final AccountDeleteRepository accountDeleteRepository;
    private final AccountExistsRepository accountExistsRepository;

    public void execute(Long id){

        if(!accountExistsRepository.existsAccountById(id)){
            throw new AccountNotFoundByIdException(id);
        }

        accountDeleteRepository.deleteById(id);
    }
}
