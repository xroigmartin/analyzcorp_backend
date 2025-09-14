package xroigmartin.analyzcorp.shared.logging.domain.mask;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import xroigmartin.analyzcorp.shared.logging.application.BusinessLogger;

/**
 * Utility to redact sensitive information from log messages.
 * <p>
 * This masker is vendor-agnostic and can be used both in unit tests and when
 * sending events through the {@link BusinessLogger}.
 * </p>
 */
public class SensitiveDataMasker {

    private final String mask;

    private final List<Pattern> patterns = Arrays.asList(
            // Email
            Pattern.compile("(?i)\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b"),
            // Bearer / JWT
            Pattern.compile("(?i)\\bbearer\\s+[A-Za-z0-9\\-_]+\\.[A-Za-z0-9\\-_]+(?:\\.[A-Za-z0-9\\-_]+)?\\b"),
            // Secrets (apiKey/secret/token/password)
            Pattern.compile("(?i)\\b(api[_-]?key|secret|token|password)\\b\\s*[:=]\\s*[A-Za-z0-9\\-._~/+=]{6,}"),
            // IBAN
            Pattern.compile("\\b[A-Z]{2}\\d{2}[A-Z0-9]{11,30}\\b"),
            // Credit cards (major brands)
            Pattern.compile("\\b4\\d{12}(\\d{3})?\\b"),            // VISA
            Pattern.compile("\\b5[1-5]\\d{14}\\b"),                 // MasterCard
            Pattern.compile("\\b3[47]\\d{13}\\b"),                  // AMEX
            Pattern.compile("\\b3(?:0[0-5]|[68]\\d)\\d{11}\\b"),    // Diners
            Pattern.compile("\\b6(?:011|5\\d{2})\\d{12}\\b"),       // Discover
            Pattern.compile("\\b(?:2131|1800|35\\d{3})\\d{11}\\b"), // JCB
            // Phone numbers (E.164)
            Pattern.compile("\\+[1-9]\\d{1,14}\\b")
    );

    private SensitiveDataMasker(String mask) {
        this.mask = mask;
    }

    /**
     * Factory method with default mask "***".
     */
    public static SensitiveDataMasker defaultMasker() {
        return new SensitiveDataMasker("***");
    }

    /**
     * Factory method with a custom mask string.
     *
     * @param mask replacement token, e.g. "***" or "[REDACTED]"
     */
    public static SensitiveDataMasker withMask(String mask) {
        return new SensitiveDataMasker(mask);
    }

    /**
     * Apply redaction to all sensitive patterns.
     *
     * @param input log message or JSON
     * @return masked string
     */
    public String mask(String input) {
        if (input == null) {
            return null;
        }
        String result = input;
        for (Pattern p : patterns) {
            result = p.matcher(result).replaceAll(mask);
        }
        return result;
    }
}