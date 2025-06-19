package xroigmartin.analyzcorp.finance.account.infrastructure.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xroigmartin.analyzcorp.finance.account.application.use_case.CreateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAccountByIdUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAllAccountsUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.UpdateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.AccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.CreateAccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.UpdateAccountRequestDTO;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts(){
        var accounts = getAllAccountsUseCase.execute();
        return ResponseEntity.ok(accounts.stream().map(this::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id){
        var accountOptional = getAccountByIdUseCase.execute(id);
        return accountOptional
                .map(account -> ResponseEntity.ok(toDto(account)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO){
        var newAccount = new Account(null, createAccountDTO.name());
        var account = createAccountUseCase.execute(newAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDTO>> updateAccount(@PathVariable Long id, @RequestBody UpdateAccountRequestDTO request){
        var accountToUpdate = Account.builder()
                .id(id)
                .name(request.name())
                .build();

        Account accountUpdated = updateAccountUseCase.execute(accountToUpdate);
        var apiResponse = ApiResponse.<AccountDTO>builder()
                .data(toDto(accountUpdated))
                .timestamp(Instant.now().toString())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    private AccountDTO toDto(Account account){
        return new AccountDTO(account.id(), account.name());
    }
}
