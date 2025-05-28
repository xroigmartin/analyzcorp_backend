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

import java.time.Duration;
import java.util.Map;

@Component
@Slf4j
public class SecApiHttpClient {

    private final WebClient webClient;

    public SecApiHttpClient(
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

    public Map<String, Map<String, Object>> fetchCompaniesJson() {
        try {
            return webClient.get()
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
}
