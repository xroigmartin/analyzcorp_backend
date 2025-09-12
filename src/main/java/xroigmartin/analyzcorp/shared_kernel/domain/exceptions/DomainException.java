package xroigmartin.analyzcorp.shared_kernel.domain.exceptions;

/**
 * Root for domain-specific exceptions.
 *
 * <p>Use specific subclasses to enable clear mapping to HTTP/application errors.</p>
 */
public abstract class DomainException extends RuntimeException {
    protected DomainException(String message) { super(message); }
    protected DomainException(String message, Throwable cause) { super(message, cause); }
}
