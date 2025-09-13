package xroigmartin.analyzcorp.shared.logging;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.shared.logging.domain.MdcKeys;
import xroigmartin.analyzcorp.shared.logging.infrastructure.web.CorrelationIdFilter;

class CorrelationIdFilterTest extends BaseTest {

    private static final String HEADER = "X-Correlation-Id";

    @Test
    void generatesCorrelationIdWhenMissing_andEchoesBackHeader_andCleansMdc() throws Exception {
        var filter = new CorrelationIdFilter(HEADER, true);
        var req = new MockHttpServletRequest();
        req.setRequestURI("/api/" + faker.internet().slug());
        req.setQueryString("q=" + faker.random().hex(8));

        var resp = new MockHttpServletResponse();

        FilterChain chain = (request, response) -> {
            assertThat(MDC.get(MdcKeys.TRACE_CORRELATION_ID)).isNotBlank();
            assertThat(MDC.get(MdcKeys.REQUEST_ID)).isNotBlank();
        };

        filter.doFilter(req, resp, chain);

        assertThat(resp.getHeader(HEADER)).isNotBlank();

        assertThat(MDC.get(MdcKeys.TRACE_CORRELATION_ID)).isNull();
        assertThat(MDC.get(MdcKeys.REQUEST_ID)).isNull();
    }

    @Test
    void dropsSuspiciousCorrelationId_andReplacesWithNew() throws Exception {
        var filter = new CorrelationIdFilter(HEADER, true);

        var req = new MockHttpServletRequest();
        var resp = new MockHttpServletResponse();

        var tooLong = faker.lorem().characters(1024); // > RAW_MAX_LEN (128)
        req.addHeader(HEADER, tooLong);

        FilterChain chain = (request, response) -> { /* no-op */ };

        filter.doFilter(req, resp, chain);

        var echoed = resp.getHeader(HEADER);
        assertThat(echoed).isNotBlank();
        assertThat(echoed.length()).isLessThan(128);

        assertThat(MDC.get(MdcKeys.TRACE_CORRELATION_ID)).isNull();
        assertThat(MDC.get(MdcKeys.REQUEST_ID)).isNull();
    }

    @Test
    void respectsProvidedCorrelationId_whenValidPrintableAndShort() throws Exception {
        var filter = new CorrelationIdFilter(HEADER, true);

        var validCorr = faker.internet().uuid().replace("-", ""); // printable y corto
        var req = new MockHttpServletRequest();
        req.addHeader(HEADER, validCorr);
        var resp = new MockHttpServletResponse();

        FilterChain chain = (request, response) -> {
            assertThat(MDC.get(MdcKeys.TRACE_CORRELATION_ID)).isEqualTo(validCorr);
        };

        filter.doFilter(req, resp, chain);

        assertThat(resp.getHeader(HEADER)).isEqualTo(validCorr);

        assertThat(MDC.get(MdcKeys.TRACE_CORRELATION_ID)).isNull();
        assertThat(MDC.get(MdcKeys.REQUEST_ID)).isNull();
    }
}
