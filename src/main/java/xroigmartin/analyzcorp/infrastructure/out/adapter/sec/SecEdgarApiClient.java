package xroigmartin.analyzcorp.infrastructure.out.adapter.sec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
import xroigmartin.analyzcorp.domain.exception.SECAPIException;
import xroigmartin.analyzcorp.domain.model.Company;
import xroigmartin.analyzcorp.domain.repository.CompanyRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class SecEdgarApiClient implements CompanyRepository {

    private final WebClient webClient;

    public SecEdgarApiClient(
            @Value("${sec.api.companies-url}") String secApiUrl,
            @Value("${sec.api.user-agent}") String userAgent
    ) {
        int maxSizeInBytes = 10 * 1024 * 1024; // 10MB

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(maxSizeInBytes))
                .build();

        this.webClient = WebClient.builder()
                .baseUrl(secApiUrl)
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
                .exchangeStrategies(strategies)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(30))))
                .build();
    }

    @Override
    public List<Company> findByNameOrTicker(String name, String ticker, int limit, int offset) {
        log.info("Find company by name: {} and ticker: {}", name, ticker);
        return List.of();
    }

    @Override
    public List<Company> getAllCompanies() {
        log.info("Requesting company data from SEC API...");
        try {
            Map<String, Map<String, Object>> response = webClient.get()
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<Company> companies = new ArrayList<>();

            if(response != null){
                for(Map.Entry<String, Map<String, Object>> entry : response.entrySet()){
                    Map<String, Object> data = entry.getValue();

                    String cik = String.format("%010d", ((Number) data.get("cik_str")).intValue());
                    String name = ((String) data.get("title")).trim();
                    String ticker = ((String) data.get("ticker")).trim();

                    companies.add(new Company(cik, name, ticker));
                }
            }

            log.info("Company data sucessfully retrieved form SEC.");

            return companies;
        }
        catch (WebClientResponseException e) {
            log.error("HTTP error from SEC API: status {}", e.getRawStatusCode());
            throw new SECAPIException("Failed to fetch data from SEC (status " + e.getRawStatusCode() + ")", e);
        } catch (Exception e) {
            log.error("Unexpected error while retrieving company data", e);
            throw new SECAPIException("Unexpected error occurred", e);
        }
    }

    @Override
    public Company getCompanyByCik(String cik) {
        return null;
    }


}
