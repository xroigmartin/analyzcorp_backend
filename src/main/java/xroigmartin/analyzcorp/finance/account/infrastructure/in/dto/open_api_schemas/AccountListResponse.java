package xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.open_api_schemas;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import xroigmartin.analyzcorp.finance.account.infrastructure.in.dto.AccountDTO;
import xroigmartin.analyzcorp.shared_kernel.infrastructure.in.dto.AnalyzCorpApiResponse;

@Schema(description = "API response containing a list of accounts")
public class AccountListResponse extends AnalyzCorpApiResponse<List<AccountDTO>> {
}
