package xroigmartin.analyzcorp.investment.domain.exception;

import java.io.Serial;

public class SECAPIException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3811930662006057917L;

    public SECAPIException(String message) {
        super(message);
    }

    public SECAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
