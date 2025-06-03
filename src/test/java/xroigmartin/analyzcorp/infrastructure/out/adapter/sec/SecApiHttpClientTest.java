package xroigmartin.analyzcorp.infrastructure.out.adapter.sec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import xroigmartin.analyzcorp.domain.exception.SECAPIException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecApiHttpClientTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private SecApiHttpClient secApiHttpClient;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(secApiHttpClient, "secApiUrl", "http://dummy-url.test");
        when(webClientBuilder.clone()).thenReturn(webClientBuilder);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.exchangeStrategies(any(ExchangeStrategies.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    void shouldFetchCompaniesJson() {
        Map<String, Map<String, Object>> expected = Map.of(
                "0", Map.of("cik_str", 1, "title", "Apple Inc.", "ticker", "AAPL")
        );

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(Map.class))).thenReturn(Mono.just(expected));

        Map<String, Map<String, Object>> result = secApiHttpClient.fetchCompaniesJson();

        assertEquals(expected, result);
    }

    @Test
    void shouldThrowSECAPIExceptionOnFailure() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(Map.class))).thenReturn(Mono.error(new RuntimeException("Boom")));

        assertThrows(SECAPIException.class, () -> secApiHttpClient.fetchCompaniesJson());
    }

    @Test
    void shouldThrowSECAPIExceptionOnFailureWithWebClientResponseException() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);

        WebClientResponseException exception = new WebClientResponseException(
                "Mocked error",
                500,
                "Internal Server Error",
                null,
                null,
                null
        );

        when(responseSpec.bodyToMono(eq(Map.class))).thenReturn(Mono.error(exception));

        assertThrows(SECAPIException.class, () -> secApiHttpClient.fetchCompaniesJson());
    }
}
