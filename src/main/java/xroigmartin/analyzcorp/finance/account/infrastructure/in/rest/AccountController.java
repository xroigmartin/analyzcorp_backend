package xroigmartin.analyzcorp.finance.account.infrastructure.in.rest;

import java.time.Instant;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xroigmartin.analyzcorp.finance.account.application.use_case.CreateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.DeleteAccountUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAccountByIdUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAllAccountsUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.UpdateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.AccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.CreateAccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.UpdateAccountRequestDTO;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDTO>>> getAccounts(){
        var accounts = getAllAccountsUseCase.execute();

        var accountDTOs = accounts.stream().map(this::toDto).toList();

        var apiResponse = ApiResponse.<List<AccountDTO>>builder()
                .data(accountDTOs)
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDTO>> getAccountById(@PathVariable Long id){
        var account = getAccountByIdUseCase.execute(id);

        var apiResponse = ApiResponse.<AccountDTO>builder()
                .data(toDto(account))
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDTO>> createAccount(@RequestBody CreateAccountDTO createAccountDTO){
        var newAccount = new Account(null, createAccountDTO.name());
        var account = createAccountUseCase.execute(newAccount);

        var apiResponse = ApiResponse.<AccountDTO>builder()
                .data(toDto(account))
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Long id){
        deleteAccountUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    private AccountDTO toDto(Account account){
        return new AccountDTO(account.id(), account.name());
    }
}
