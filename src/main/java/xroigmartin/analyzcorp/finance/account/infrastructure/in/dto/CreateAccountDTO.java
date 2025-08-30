package xroigmartin.analyzcorp.finance.account.infrastructure.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO used to create a new account")
public record CreateAccountDTO(
        @Schema(description = "Name of the account", example = "Personal Savings", required = true)
        @NotBlank(message = "Account name must not be empty")
        @Size(max = 100, message = "Account name must be less than 100 characters")
        String name
) {}
