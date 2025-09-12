package xroigmartin.analyzcorp.rbac.domain.vo;

/**
 * Username (CITEXT-like). Normalized to lowercase and trimmed.
 *
 * @param value string length 3..120
 */
public record Username(String value) {

    private static final String RX = "^[a-z0-9._]{3,120}$";

    public Username {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Username required");
        String norm = value.trim().toLowerCase();
        if (norm.length() < 3 || norm.length() > 120) throw new IllegalArgumentException("Username length 3..120");
        value = norm;
        if (!norm.matches(RX)) throw new IllegalArgumentException("Invalid username pattern");
    }
    public static Username of(String raw) { return new Username(raw); }
}
