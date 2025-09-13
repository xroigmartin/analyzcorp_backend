package xroigmartin.analyzcorp.shared.logging.domain.model;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import xroigmartin.analyzcorp.shared.logging.domain.enums.EventCategory;
import xroigmartin.analyzcorp.shared.logging.domain.enums.Outcome;

/**
 * Strongly-typed business/security event matching the neutral JSON schema.
 * <p>
 * Keep messages short and PII-free; contextual details should be in MDC or normalized fields.
 * </p>
 */

@Value
@Builder
@With
public class BusinessEvent {

    /**
     * ISO-8601 timestamp with zone. Defaults to {@code OffsetDateTime.now()} if not set by the builder.
     */
    @Builder.Default OffsetDateTime timestamp = OffsetDateTime.now();

    /**
     * Event category (BUSINESS or SECURITY). SYSTEM is reserved for technical logs.
     */
    EventCategory category;

    /**
     * Action name (upper snake case recommended). Domain modules may supply enum.name().
     * Examples: RBAC_ROLE_ASSIGNED, LOGIN_BLOCKED, FINANCE_ACCOUNT_CREATED.
     */
    String action;

    /**
     * Short, PII-free message. Additional context should live in MDC or structured fields.
     */
    String message;

    /** Actor (avoid PII; prefer normalized/public identifiers). */
    String actorName;
    String actorId;

    /** Primary entity targeted by the action. */
    String entityType;
    String entityId;

    /** Optional secondary target id. */
    String targetId;

    /** Outcome classification. */
    Outcome outcome;

    /** Optional reason (e.g., policy code explaining a denied outcome). */
    String reason;
}