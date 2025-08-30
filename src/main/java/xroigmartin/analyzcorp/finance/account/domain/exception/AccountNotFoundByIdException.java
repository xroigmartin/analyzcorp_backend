package xroigmartin.analyzcorp.finance.account.domain.exception;

import java.io.Serial;

public class AccountNotFoundByIdException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2566767014626296311L;

    public AccountNotFoundByIdException(Long accountId) {
        super(String.format("Account with id %d not found", accountId));
    }
}
