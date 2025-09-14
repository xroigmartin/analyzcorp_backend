package xroigmartin.analyzcorp.finance.account.domain.exception;

import java.io.Serial;

import xroigmartin.analyzcorp.finance.account.domain.events.AccountAction;
import xroigmartin.analyzcorp.shared.logging.domain.model.BusinessEvent;

public class AccountNameAlreadyExistsException extends BusinessException{

    @Serial
    private static final long serialVersionUID = 1484159171417159958L;

    public AccountNameAlreadyExistsException(AccountAction action) {
        super("Name of account already exists",
                BusinessEvent.builder()
                        .action(action.name())
                        .message("Name of account already exists")
                        .entityId("account")
                        .entityType(null)
                        .reason("ACCOUNT_NAME_ALREADY_EXISTS")
                        .build());
    }

}
