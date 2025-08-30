package xroigmartin.analyzcorp.finance.account.domain.exception;

import java.io.Serial;

public class AccountNameAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1484159171417159958L;

    public AccountNameAlreadyExistsException(String name) {
        super("Account name already exists: " + name);
    }

}
