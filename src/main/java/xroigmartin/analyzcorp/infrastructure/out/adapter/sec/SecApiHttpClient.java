package xroigmartin.analyzcorp.infrastructure.out.adapter.sec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import xroigmartin.analyzcorp.domain.exception.SECAPIException;

import java.util.Map;

@Component
@Slf4j
public class SecApiHttpClient {

    @Value("${sec.api.companies-url}")
    private String secApiUrl;

    private final WebClient.Builder webClientBuilder;

    public SecApiHttpClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Map<String, Map<String, Object>> fetchCompaniesJson() {
        try {
            return getWebClient().get()
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        }
        catch (WebClientResponseException e) {
            log.error("HTTP error from SEC API: status {}", e.getStatusCode());
            throw new SECAPIException("Failed to fetch data from SEC (status " + e.getStatusCode() + ")", e);
        } catch (Exception e) {
            log.error("Unexpected error while retrieving company data", e);
            throw new SECAPIException("Unexpected error occurred", e);
        }
    }

    private WebClient getWebClient() {
        int maxSizeInBytes = 10 * 1024 * 1024; // 10MB

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(maxSizeInBytes))
                .build();

        return webClientBuilder
                .clone()
                .baseUrl(secApiUrl)
                .exchangeStrategies(strategies)
                .build();
    }
}
