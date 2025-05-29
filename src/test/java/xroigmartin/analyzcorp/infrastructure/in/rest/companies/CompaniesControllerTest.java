package xroigmartin.analyzcorp.infrastructure.in.rest.companies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xroigmartin.analyzcorp.application.use_case.GetAllCompaniesUseCase;
import xroigmartin.analyzcorp.application.use_case.ImportCompaniesFromSecUseCase;
import xroigmartin.analyzcorp.domain.model.Company;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CompaniesControllerTest {

    @Mock
    private GetAllCompaniesUseCase getAllCompaniesUseCase;

    @Mock
    private ImportCompaniesFromSecUseCase importCompaniesFromSecUseCase;

    @InjectMocks
    private CompaniesController companiesController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(companiesController).build();
    }

    @Test
    void shouldReturnListOfCompanies() throws Exception {
        // given
        List<Company> companies = List.of(
                new Company("0000000001", "Apple Inc.", "AAPL"),
                new Company("0000000002", "Microsoft Corp.", "MSFT")
        );
        when(getAllCompaniesUseCase.execute()).thenReturn(companies);

        // when & then
        mockMvc.perform(get("/api/companies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].cik").value("0000000001"))
                .andExpect(jsonPath("$[0].name").value("Apple Inc."))
                .andExpect(jsonPath("$[0].ticker").value("AAPL"));

        verify(getAllCompaniesUseCase).execute();
    }

    @Test
    void shouldTriggerImportFromSec() throws Exception {
        // when & then
        mockMvc.perform(get("/api/companies/import"))
                .andExpect(status().isAccepted());

        verify(importCompaniesFromSecUseCase).execute();
    }
}