package xroigmartin.analyzcorp.finance.account.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.finance.account.application.use_case.GetAllAccountsUseCase;
import xroigmartin.analyzcorp.finance.account.domain.model.Account;
import xroigmartin.analyzcorp.finance.account.domain.repository.AccountGetAllRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetAllAccountsUseCaseTest extends BaseTest {

    @Mock
    private AccountGetAllRepository accountGetAllRepository;

    @InjectMocks
    private GetAllAccountsUseCase useCase;

    @Test
    void givenAccountsExist_whenExecute_thenReturnsListOfAccounts() {
        // Given

        Account a1 = new Account(faker.number().randomNumber(), faker.name().firstName());
        Account a2 = new Account(faker.number().randomNumber(), faker.name().firstName());
        List<Account> expected = Arrays.asList(a1, a2);
        given(accountGetAllRepository.getAllAccounts()).willReturn(expected);

        // When
        List<Account> result = useCase.execute();

        // Then
        assertThat(result).hasSize(2)
                .containsExactly(a1, a2);
    }

    @Test
    void givenNoAccounts_whenExecute_thenReturnsEmptyList() {
        // Given
        given(accountGetAllRepository.getAllAccounts()).willReturn(Collections.emptyList());

        // When
        List<Account> result = useCase.execute();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void whenRepositoryThrowsException_thenPropagateException() {
        // Given
        given(accountGetAllRepository.getAllAccounts())
                .willThrow(new RuntimeException("DB error"));

        // When / Then
        assertThatThrownBy(() -> useCase.execute())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("DB error");
    }
}
