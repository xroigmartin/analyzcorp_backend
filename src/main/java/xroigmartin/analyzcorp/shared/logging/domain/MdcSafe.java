package xroigmartin.analyzcorp.shared.logging.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

/**
 * Safe MDC helper to guard against overly long or non-printable values.
 */
@UtilityClass
public class MdcSafe {

    private static final int DEFAULT_MAX = 256;
    private static final Pattern PRINTABLE = Pattern.compile("\\P{Cntrl}+");
    private static int maxLen = DEFAULT_MAX;

    /** Globally set a maximum MDC value length (defaults to 256). */
    public static void setMaxLen(int len) {
        maxLen = Math.max(32, Math.min(len, 4096));
    }

    public static void put(String key, String value) {
        if (key == null || value == null) return;
        if (!PRINTABLE.matcher(value).matches()) return;
        if (value.length() > maxLen) value = value.substring(0, maxLen);
        MDC.put(key, value);
    }

    public static void remove(String key) {
        MDC.remove(key);
    }

    public static void clear(String... keys) {
        for (String k : Objects.requireNonNullElse(keys, new String[0])) {
            MDC.remove(k);
        }
    }
}
