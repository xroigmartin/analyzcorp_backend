package xroigmartin.analyzcorp.shared_kernel.infrastructure.in.dto;

import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "Generic wrapper for API responses")
public class AnalyzCorpApiResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -502208910028423569L;

    @Schema(description = "Response payload containing the actual data")
    private T data;

    @Schema(description = "Error details if the request failed")
    private AnalyzCorpApiError error;

    @Schema(description = "ISO 8601 timestamp of the response", example = "2025-07-31T08:45:00Z")
    private String timestamp;
}
