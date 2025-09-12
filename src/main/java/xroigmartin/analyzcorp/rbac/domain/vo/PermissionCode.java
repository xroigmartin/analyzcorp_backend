package xroigmartin.analyzcorp.rbac.domain.vo;

/**
 * Permission code in UPPERCASE matching {@code A-Z0-9_}.
 *
 * @param value string length 2..96
 */
public record PermissionCode(String value) {
    private static final String RX = "^[A-Z0-9_]{2,96}$";
    public PermissionCode {
        if (value == null) throw new IllegalArgumentException("Permission code required");
        String norm = value.trim().toUpperCase();
        if (!norm.matches(RX)) throw new IllegalArgumentException("Invalid permission code");
        value = norm;
    }
    public static PermissionCode of(String code) { return new PermissionCode(code); }
}
