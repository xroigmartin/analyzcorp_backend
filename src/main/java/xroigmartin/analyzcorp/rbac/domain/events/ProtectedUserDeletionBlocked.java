package xroigmartin.analyzcorp.rbac.domain.events;

import java.time.OffsetDateTime;
import java.util.Objects;

import xroigmartin.analyzcorp.rbac.domain.vo.UserPublicId;
import xroigmartin.analyzcorp.shared_kernel.domain.enums.AuditCategory;

/**
 * Security event: deletion of a protected user was blocked by policy.
 */
public record ProtectedUserDeletionBlocked(
        UserPublicId userId,
        String actor,
        OffsetDateTime occurredAt
) implements AuditEvent {

    public ProtectedUserDeletionBlocked {
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(actor, "actor");
        occurredAt = occurredAt != null ? occurredAt : OffsetDateTime.now();
    }

    @Override public AuditCategory category() { return AuditCategory.SECURITY; }
    @Override public String action() { return "RBAC_PROTECTED_USER_DELETE_BLOCKED"; }

    public static ProtectedUserDeletionBlocked now(UserPublicId userId, String actor) {
        return new ProtectedUserDeletionBlocked(userId, actor, OffsetDateTime.now());
    }
}