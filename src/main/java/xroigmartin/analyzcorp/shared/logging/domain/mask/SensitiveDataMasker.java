package xroigmartin.analyzcorp.shared.logging.domain.mask;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Configurable masking utility to redact sensitive data from log messages.
 * <p>
 * This implementation performs regex-based replacements intended to be fast and
 * vendor-agnostic. It is not a full DLP solution; adjust patterns to your needs.
 * </p>
 */
public class SensitiveDataMasker {

    /** Replacement token used when masking sensitive values. */
    public static final String DEFAULT_MASK = "***";

    private final List<Pattern> patterns;
    private final String mask;

    /**
     * Creates a new masker with custom patterns and a replacement token.
     *
     * @param patterns regex patterns to redact (entire match is replaced)
     * @param mask     replacement token (e.g., "***")
     */
    public SensitiveDataMasker(List<Pattern> patterns, String mask) {
        this.patterns = patterns;
        this.mask = (mask == null || mask.isBlank()) ? DEFAULT_MASK : mask;
    }

    /**
     * Applies masking to the provided input string.
     *
     * @param input source text (nullable)
     * @return redacted text, or the original input if null/empty
     */
    public String mask(String input) {
        if (input == null || input.isEmpty()) return input;
        String out = input;
        for (Pattern p : patterns) {
            out = p.matcher(out).replaceAll(mask);
        }
        return out;
    }

    /**
     * Factory for a global, neutral default set of masking patterns.
     * <ul>
     *   <li>Emails</li>
     *   <li>Bearer/JWT tokens (approximate)</li>
     *   <li>Generic secrets (apiKey/secret/token/password)</li>
     *   <li>IBAN (generic)</li>
     *   <li>Payment cards (broad, non-Luhn)</li>
     *   <li>Phone numbers (E.164-like)</li>
     * </ul>
     */
    public static SensitiveDataMasker defaultMasker() {
        return new SensitiveDataMasker(List.of(
                // Email
                Pattern.compile("([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)"),

                // Bearer / JWT (approximate)
                Pattern.compile("(?i)bearer\\s+[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*"),

                // Generic secrets in key[:=]value (apiKey/secret/token/password)
                Pattern.compile("(?i)(api[_-]?key|secret|token|password)\\s*[:=]\\s*[A-Za-z0-9\\-._~+/]+=*"),

                // IBAN (generic)
                Pattern.compile("\\b[A-Z]{2}\\d{2}[A-Z0-9]{11,30}\\b"),

                // Payment cards (broad patterns; no Luhn for performance)
                Pattern.compile("\\b4\\d{12}(\\d{3})?\\b"),              // VISA
                Pattern.compile("\\b5[1-5]\\d{14}\\b"),                   // MasterCard
                Pattern.compile("\\b3[47]\\d{13}\\b"),                    // AMEX
                Pattern.compile("\\b3(?:0[0-5]|[68]\\d)\\d{11}\\b"),      // Diners
                Pattern.compile("\\b6(?:011|5\\d{2})\\d{12}\\b"),         // Discover
                Pattern.compile("\\b(?:2131|1800|35\\d{3})\\d{11}\\b"),   // JCB

                // Phone numbers (E.164-ish)
                Pattern.compile("\\+?[1-9]\\d{1,14}\\b")
        ), DEFAULT_MASK);
    }
}