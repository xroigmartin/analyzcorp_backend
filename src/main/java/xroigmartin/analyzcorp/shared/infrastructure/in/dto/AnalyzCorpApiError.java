package xroigmartin.analyzcorp.shared.infrastructure.in.dto;

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
@Schema(description = "Details of an error returned by the API")
public class AnalyzCorpApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = -1257222738380732558L;

    @Schema(description = "Machine-readable error code", example = "ACCOUNT_NOT_FOUND")
    private String code;

    @Schema(description = "Human-readable error message", example = "Account with ID 5 was not found")
    private String message;
}
