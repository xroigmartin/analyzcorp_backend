package xroigmartin.analyzcorp.shared.logging.infrastructure.web;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xroigmartin.analyzcorp.shared.logging.domain.MdcKeys;
import xroigmartin.analyzcorp.shared.logging.domain.MdcSafe;

/**
 * Servlet {@link Filter} that ensures MDC carries a correlation id and a per-request id,
 * while sanitizing incoming header values and echoing the correlation id back to the client.
 */
public class CorrelationIdFilter implements Filter {
    private static final int RAW_MAX_LEN = 128;
    private static final Pattern PRINTABLE = Pattern.compile("[\\p{Print}]+");

    private final String headerName;
    private final boolean ensure;

    /**
     * @param headerName HTTP header to read the external correlation id from (e.g., {@code X-Correlation-Id})
     * @param ensure     when {@code true}, generate a correlation id if the header is absent
     */
    public CorrelationIdFilter(String headerName, boolean ensure) {
        this.headerName = headerName;
        this.ensure = ensure;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String corrId = null;
            if (request instanceof HttpServletRequest r) {
                corrId = r.getHeader(headerName);
                if (corrId != null) {
                    // Sanitize: printable + reasonable raw cap (before MDC-length rules)
                    if (!PRINTABLE.matcher(corrId).matches() || corrId.length() > RAW_MAX_LEN) {
                        corrId = null;
                    }
                }
            }
            if ((corrId == null || corrId.isBlank()) && ensure) {
                corrId = UUID.randomUUID().toString().replace("-", "");
            }
            if (corrId != null) {
                MdcSafe.put(MdcKeys.TRACE_CORRELATION_ID, corrId);
                if (response instanceof HttpServletResponse resp) {
                    resp.setHeader(headerName, corrId); // echo for clients
                }
            }
            MdcSafe.put(MdcKeys.REQUEST_ID, UUID.randomUUID().toString());
            chain.doFilter(request, response);
        } finally {
            MdcSafe.remove(MdcKeys.TRACE_CORRELATION_ID);
            MdcSafe.remove(MdcKeys.REQUEST_ID);
        }
    }
}
