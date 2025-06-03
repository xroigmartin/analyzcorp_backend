package xroigmartin.analyzcorp.application.use_case;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xroigmartin.analyzcorp.domain.model.Company;
import xroigmartin.analyzcorp.domain.repository.CompanySearchRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCompaniesUseCaseTest {
    @Mock
    private CompanySearchRepository companySearchRepository;

    @InjectMocks
    private GetCompaniesUseCase getCompaniesUseCase;

    @Test
    void shouldReturnCompaniesMatchingSearchCriteria() {
        // given
        String name = "Apple";
        String ticker = "AAPL";
        int limit = 10;
        int offset = 0;

        List<Company> expectedCompanies = List.of(
                new Company("0000000001", "Apple Inc.", "AAPL"),
                new Company("0000000002", "Apple Hospitality", "APLE")
        );

        when(companySearchRepository.findByNameOrTicker(name, ticker, limit, offset))
                .thenReturn(expectedCompanies);

        // when
        List<Company> result = getCompaniesUseCase.execute(name, ticker, limit, offset);

        // then
        assertEquals(expectedCompanies, result);
        verify(companySearchRepository).findByNameOrTicker(name, ticker, limit, offset);
    }
}
