package xroigmartin.analyzcorp.rbac.domain.events;

import java.time.OffsetDateTime;
import java.util.Objects;

import xroigmartin.analyzcorp.rbac.domain.enums.LoginBlockReason;
import xroigmartin.analyzcorp.rbac.domain.enums.UserStatus;
import xroigmartin.analyzcorp.rbac.domain.vo.UserPublicId;
import xroigmartin.analyzcorp.shared_kernel.domain.enums.AuditCategory;

/**
 * Security event: a login attempt was blocked by domain rules.
 *
 * <p>PII note: avoid logging emails/IPs here; prefer publicId and reason.</p>
 */
public record LoginBlocked(
        UserPublicId userId,
        UserStatus status,
        boolean canLoginFlag,
        LoginBlockReason reason,
        OffsetDateTime occurredAt
) implements AuditEvent {

    public LoginBlocked {
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(status, "status");
        Objects.requireNonNull(reason, "reason");
        occurredAt = occurredAt != null ? occurredAt : OffsetDateTime.now();
    }

    @Override public AuditCategory category() { return AuditCategory.SECURITY; }
    @Override public String action() { return "RBAC_LOGIN_BLOCKED"; }

    public static LoginBlocked now(UserPublicId userId, UserStatus status, boolean canLoginFlag, LoginBlockReason reason) {
        return new LoginBlocked(userId, status, canLoginFlag, reason, OffsetDateTime.now());
    }
}