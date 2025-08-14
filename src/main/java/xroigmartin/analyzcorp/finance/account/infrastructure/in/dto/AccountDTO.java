package xroigmartin.analyzcorp.finance.account.infrastructure.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing an account in the system")
public record AccountDTO (
        @Schema(description = "Unique identifier of the account", example = "1")
        Long id,
        @Schema(description = "Name of the account", example = "My Personal Account")
        String name
){}
