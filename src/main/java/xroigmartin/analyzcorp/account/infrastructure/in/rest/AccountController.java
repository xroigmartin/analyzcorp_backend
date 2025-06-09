package xroigmartin.analyzcorp.account.infrastructure.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xroigmartin.analyzcorp.account.application.use_case.CreateAccountUseCase;
import xroigmartin.analyzcorp.account.application.use_case.GetAllAccountsUseCase;
import xroigmartin.analyzcorp.account.domain.model.Account;
import xroigmartin.analyzcorp.account.infrastructure.in.dto.AccountDTO;
import xroigmartin.analyzcorp.account.infrastructure.in.dto.CreateAccountDTO;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts(){
        var accounts = getAllAccountsUseCase.execute();
        return ResponseEntity.ok(accounts.stream().map(this::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO){
        var newAccount = new Account(null, createAccountDTO.name());
        var account = createAccountUseCase.execute(newAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(account));
    }

    private AccountDTO toDto(Account account){
        return new AccountDTO(account.id(), account.name());
    }
}
