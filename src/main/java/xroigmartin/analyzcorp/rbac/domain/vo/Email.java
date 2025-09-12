package xroigmartin.analyzcorp.rbac.domain.vo;

/**
 * Email (CITEXT-like). Normalized to lowercase and trimmed; basic validation + max length (≤ 254).
 *
 * @throws IllegalArgumentException if blank, invalid format, or too long
 */
public record Email(String value) {
    private static final String SIMPLE_RX = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

    public Email {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Email required");
        String norm = value.trim().toLowerCase();
        if (!norm.matches(SIMPLE_RX)) throw new IllegalArgumentException("Invalid email");
        value = norm;
        if (norm.length() > 254) throw new IllegalArgumentException("Email too long");
    }

    public static Email of(String raw) { return new Email(raw); }
}
