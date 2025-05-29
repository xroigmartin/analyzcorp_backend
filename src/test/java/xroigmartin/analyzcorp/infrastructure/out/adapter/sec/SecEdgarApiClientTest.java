package xroigmartin.analyzcorp.infrastructure.out.adapter.sec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xroigmartin.analyzcorp.domain.model.Company;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecEdgarApiClientTest {

    @Mock
    private SecApiHttpClient secApiHttpClient;

    @Mock
    private SecCompanyJsonParser secCompanyJsonParser;

    @InjectMocks
    private SecEdgarApiClient secEdgarApiClient;

    private Map<String, Map<String, Object>> rawJsonResponse;
    private List<Company> parsedCompanies;

    @BeforeEach
    void setup() {
        rawJsonResponse = new LinkedHashMap<>();
        rawJsonResponse.put("0", Map.of("cik_str", 1, "title", "Apple Inc.", "ticker", "AAPL"));
        rawJsonResponse.put("1", Map.of("cik_str", 2, "title", "Microsoft Corp.", "ticker", "MSFT"));

        parsedCompanies = List.of(
                new Company("0000000001", "Apple Inc.", "AAPL"),
                new Company("0000000002", "Microsoft Corp.", "MSFT")
        );
    }

    @Test
    void shouldReturnParsedCompaniesFromSecApi() {
        // Given
        when(secApiHttpClient.fetchCompaniesJson()).thenReturn(rawJsonResponse);
        when(secCompanyJsonParser.parseCompanyJsonToListOfCompanies(rawJsonResponse)).thenReturn(parsedCompanies);

        // When
        List<Company> result = secEdgarApiClient.getAllCompanies();

        // Then
        assertThat(result).containsExactlyInAnyOrderElementsOf(parsedCompanies);
        verify(secApiHttpClient).fetchCompaniesJson();
        verify(secCompanyJsonParser).parseCompanyJsonToListOfCompanies(rawJsonResponse);
    }
}