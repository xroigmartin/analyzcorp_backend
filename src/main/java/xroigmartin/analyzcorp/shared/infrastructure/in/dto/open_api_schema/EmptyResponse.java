package xroigmartin.analyzcorp.shared.infrastructure.in.dto.open_api_schema;

import io.swagger.v3.oas.annotations.media.Schema;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.AnalyzCorpApiResponse;

@Schema(description = "API response with no data, used for empty responses or messages")
public class EmptyResponse extends AnalyzCorpApiResponse<Void> {
}
