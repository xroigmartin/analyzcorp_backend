package xroigmartin.analyzcorp.infrastructure.out.adapter.sec;

import org.junit.jupiter.api.Test;
import xroigmartin.analyzcorp.domain.model.Company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecCompanyJsonParserTest {

    private final SecCompanyJsonParser parser = new SecCompanyJsonParser();

    @Test
    void shouldParseValidJsonCorrectly() {
        Map<String, Map<String, Object>> rawData = Map.of(
                "0", Map.of("cik_str", 1, "title", "Apple Inc.", "ticker", "AAPL"),
                "1", Map.of("cik_str", 2, "title", "Microsoft Corp.", "ticker", "MSFT")
        );

        List<Company> result = parser.parseCompanyJsonToListOfCompanies(rawData);

        List<Company> expected = List.of(
                new Company("0000000001", "Apple Inc.", "AAPL"),
                new Company("0000000002", "Microsoft Corp.", "MSFT")
        );

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void shouldReturnEmptyListWhenInputIsNull() {
        List<Company> companies = parser.parseCompanyJsonToListOfCompanies(null);
        assertTrue(companies.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenInputIsEmpty() {
        List<Company> companies = parser.parseCompanyJsonToListOfCompanies(new HashMap<>());
        assertTrue(companies.isEmpty());
    }
}
