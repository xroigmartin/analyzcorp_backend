package xroigmartin.analyzcorp.finance.account.domain.exception;

import java.io.Serial;

import xroigmartin.analyzcorp.finance.account.domain.events.AccountAction;
import xroigmartin.analyzcorp.shared.logging.domain.model.BusinessEvent;

public class AccountNotFoundByIdException extends BusinessException {
    @Serial
    private static final long serialVersionUID = 2566767014626296311L;

    public AccountNotFoundByIdException(Long accountId, AccountAction action) {
        super(String.format("Account with id %d not found", accountId),
                BusinessEvent.builder()
                        .action(action.name())
                        .message(String.format("Account with id %d not found", accountId))
                        .entityId("account")
                        .entityType(null)
                        .reason("ACCOUNT_NOT_FOUND")
                        .build());
    }
}
