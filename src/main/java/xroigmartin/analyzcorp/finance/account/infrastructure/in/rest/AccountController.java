package xroigmartin.analyzcorp.finance.account.infrastructure.in.rest;

import java.time.Instant;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.UpdateAccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.open_api_schemas.AccountListResponse;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.open_api_schemas.AccountResponse;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.AnalyzCorpApiResponse;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.open_api_schema.EmptyResponse;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Operations related to user financial accounts")
public class AccountController {

    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    @Operation(summary = "Retrieve all accounts", description = "Fetches the list of all user accounts.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "List of accounts successfully returned",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountListResponse.class)
            )
        )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyzCorpApiResponse<List<AccountDTO>>> getAccounts(){
        var accounts = getAllAccountsUseCase.execute();

        var accountDTOs = accounts.stream().map(this::toDto).toList();

        var apiResponse = AnalyzCorpApiResponse.<List<AccountDTO>>builder()
                .data(accountDTOs)
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get account by ID", description = "Returns the account with the given ID.")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Account found and returned",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountResponse.class)
                )
        ),
        @ApiResponse(responseCode = "404", description = "Account with specified ID was not found")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyzCorpApiResponse<AccountDTO>> getAccountById(@PathVariable Long id){
        var account = getAccountByIdUseCase.execute(id);

        var apiResponse = AnalyzCorpApiResponse.<AccountDTO>builder()
                .data(toDto(account))
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Create new account", description = "Creates a new financial account with the provided information.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Account created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AccountResponse.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid request payload or account already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyzCorpApiResponse<AccountDTO>> createAccount(@RequestBody CreateAccountDTO createAccountDTO){
        var newAccount = new Account(null, createAccountDTO.name());
        var account = createAccountUseCase.execute(newAccount);

        var apiResponse = AnalyzCorpApiResponse.<AccountDTO>builder()
                .data(toDto(account))
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "Update existing account", description = "Updates the name or details of an existing account.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Account updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AccountResponse.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PutMapping(value="/{id}",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyzCorpApiResponse<AccountDTO>> updateAccount(@PathVariable Long id, @RequestBody UpdateAccountDTO request){
        var accountToUpdate = Account.builder()
                .id(id)
                .name(request.name())
                .build();

        Account accountUpdated = updateAccountUseCase.execute(accountToUpdate);

        var apiResponse = AnalyzCorpApiResponse.<AccountDTO>builder()
                .data(toDto(accountUpdated))
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Delete account", description = "Deletes the account with the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Account deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmptyResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AnalyzCorpApiResponse<Void>> deleteAccount(@PathVariable Long id){
        deleteAccountUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    private AccountDTO toDto(Account account){
        return new AccountDTO(account.id(), account.name());
    }
}
