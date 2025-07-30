package xroigmartin.analyzcorp.finance.account.application.use_case;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountDeleteRepository;

@Service
@RequiredArgsConstructor
public class DeleteAccountUseCase {

    private final AccountDeleteRepository accountDeleteRepository;

    public void execute(Long id){
        accountDeleteRepository.deleteById(id);
    }
}
