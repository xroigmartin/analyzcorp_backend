package xroigmartin.analyzcorp.shared.logging.application;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Marker;
import xroigmartin.analyzcorp.shared.logging.domain.AuditSink;
import xroigmartin.analyzcorp.shared.logging.domain.Markers;
import xroigmartin.analyzcorp.shared.logging.domain.mask.SensitiveDataMasker;
import xroigmartin.analyzcorp.shared.logging.domain.model.BusinessEvent;

/**
 * Application service that serializes structured business/security events to JSON
 * and emits them through an {@link AuditSink}. Provides both typed and map-based APIs.
 */
public class BusinessLogger {
    private final ObjectMapper mapper;
    private final SensitiveDataMasker masker; // kept for ad-hoc calls; universal masking also applies at encoder level
    private final AuditSink sink;

    public BusinessLogger(ObjectMapper mapper, SensitiveDataMasker masker, AuditSink sink) {
        this.mapper = mapper;
        this.masker = masker;
        this.sink = sink;
    }

    /** Typed API (recommended). */
    public void infoBusiness(BusinessEvent event) { emit(Markers.BUSINESS, event); }

    /** Typed API (recommended). */
    public void infoSecurity(BusinessEvent event) { emit(Markers.SECURITY, event); }

    /** Backward compatible ad-hoc map API (discouraged). */
    public void infoBusiness(Map<String, Object> event) { emit(Markers.BUSINESS, event); }

    /** Backward compatible ad-hoc map API (discouraged). */
    public void infoSecurity(Map<String, Object> event) { emit(Markers.SECURITY, event); }

    private void emit(Marker marker, Object event) {
        try {
            String json = mapper.writeValueAsString(event);
            String masked = masker.mask(json); // belt-and-suspenders; encoder-level masking also in place
            sink.emit(marker, masked);
        } catch (Exception ignored) {
            // Intentionally swallow logging failures: logging must never affect the business flow.
        }
    }
}