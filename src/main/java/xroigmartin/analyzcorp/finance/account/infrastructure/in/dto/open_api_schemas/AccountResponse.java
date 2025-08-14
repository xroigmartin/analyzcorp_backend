package xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.open_api_schemas;

import io.swagger.v3.oas.annotations.media.Schema;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.AccountDTO;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.AnalyzCorpApiResponse;

@Schema(description = "API response containing a single account")
public class AccountResponse extends AnalyzCorpApiResponse<AccountDTO> {
}
