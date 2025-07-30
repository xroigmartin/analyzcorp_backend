package xroigmartin.analyzcorp.finance.account.infrastructure.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.CreateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.DeleteAccountUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAccountByIdUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAllAccountsUseCase;
import xroigmartin.analyzcorp.finance.account.application.use_case.UpdateAccountUseCase;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNameAlreadyExistsException;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.CreateAccountDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.UpdateAccountRequestDTO;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.utils.AccountControllerAdvice;

@WebMvcTest(AccountController.class)
@Import(AccountControllerAdvice.class)
class AccountControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAllAccountsUseCase getAllAccountsUseCase;

    @MockBean
    private CreateAccountUseCase createAccountUseCase;

    @MockBean
    private GetAccountByIdUseCase getAccountByIdUseCase;

    @MockBean
    private UpdateAccountUseCase updateAccountUseCase;

    @MockBean
    private DeleteAccountUseCase deleteAccountUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAccounts_returnsListOfAccounts() throws Exception {
        List<Account> accounts = List.of(
                new Account(faker.number().randomNumber(), faker.name().firstName()),
                new Account(faker.number().randomNumber(), faker.name().firstName())
        );

        given(getAllAccountsUseCase.execute()).willReturn(accounts);

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void getAccountById_returnsAccount() throws Exception {
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        Account account = new Account(id, name);

        given(getAccountByIdUseCase.execute(id)).willReturn(account);

        mockMvc.perform(get("/api/accounts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void getAccountById_whenNotFound_returns404() throws Exception {
        Long id = faker.number().randomNumber();
        String message = String.format("Account with id %d not found", id);

        given(getAccountByIdUseCase.execute(id)).willThrow(new AccountNotFoundByIdException(id));

        mockMvc.perform(get("/api/accounts/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value("ACCOUNT_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value(message))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void createAccount_whenValid_returnsCreated() throws Exception {
        String name = faker.name().firstName();
        Account created = new Account(faker.number().randomNumber(), name);
        CreateAccountDTO dto = new CreateAccountDTO(name);

        given(createAccountUseCase.execute(new Account(null, name))).willReturn(created);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(created.id()))
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void createAccount_whenNameAlreadyExists_returnsConflict() throws Exception {
        String name = faker.name().firstName();
        CreateAccountDTO dto = new CreateAccountDTO(name);
        String message = String.format("Account name already exists: %s", name);

        given(createAccountUseCase.execute(any())).willThrow(new AccountNameAlreadyExistsException(name));

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value("ACCOUNT_NAME_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.error.message").value(message))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void updateAccount_whenValid_returnsUpdated() throws Exception {
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        UpdateAccountRequestDTO request = new UpdateAccountRequestDTO(name);
        Account updated = new Account(id, name);

        given(updateAccountUseCase.execute(new Account(id, name))).willReturn(updated);

        mockMvc.perform(put("/api/accounts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void updateAccount_whenNameConflict_returns409() throws Exception {
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        UpdateAccountRequestDTO request = new UpdateAccountRequestDTO(name);
        String message = String.format("Account name already exists: %s", name);;

        given(updateAccountUseCase.execute(any())).willThrow(new AccountNameAlreadyExistsException(name));

        mockMvc.perform(put("/api/accounts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value("ACCOUNT_NAME_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.error.message").value(message))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void updateAccount_whenNotFound_returns404() throws Exception {
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        UpdateAccountRequestDTO request = new UpdateAccountRequestDTO(name);
        String message = String.format("Account with id %d not found", id);

        given(updateAccountUseCase.execute(any())).willThrow(new AccountNotFoundByIdException(id));

        mockMvc.perform(put("/api/accounts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value("ACCOUNT_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value(message))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void deleteAccount_whenExists_returns204() throws Exception {
        Long id = faker.number().randomNumber();

        mockMvc.perform(delete("/api/accounts/{id}", id))
                .andExpect(status().isNoContent());

        verify(deleteAccountUseCase).execute(id);
    }

    @Test
    void deleteAccount_whenNotFound_returns404() throws Exception {
        Long id = faker.number().randomNumber();
        String message = String.format("Account with id %d not found", id);

        doThrow(new AccountNotFoundByIdException(id)).when(deleteAccountUseCase).execute(id);

        mockMvc.perform(delete("/api/accounts/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error.code").value("ACCOUNT_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value(message))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
