package xroigmartin.analyzcorp.rbac.domain.vo;

/**
 * Role code in UPPERCASE matching {@code A-Z0-9_}.
 *
 * @param value string length 2..64
 */
public record RoleCode(String value) {
    private static final String RX = "^[A-Z0-9_]{2,64}$";
    public RoleCode {
        if (value == null) throw new IllegalArgumentException("Role code required");
        String norm = value.trim().toUpperCase();
        if (!norm.matches(RX)) throw new IllegalArgumentException("Invalid role code");
        value = norm;
    }
    public static RoleCode of(String code) { return new RoleCode(code); }
}