package xroigmartin.analyzcorp.rbac.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.rbac.domain.vo.UserId;

class UserRoleTest {

    @Test
    void create_should_default_assignedAt_when_null_and_trim_actor() {
        OffsetDateTime before = OffsetDateTime.now();
        UserRole ur = UserRole.create(UserId.of(1), RoleCode.of("ADMIN"), "  system  ", null);
        OffsetDateTime after = OffsetDateTime.now();

        assertThat(ur.getAssignedBy()).isEqualTo("system");
        assertThat(ur.getAssignedAt()).isNotNull();
        assertThat(ur.getAssignedAt()).isBetween(before.minusSeconds(1), after.plusSeconds(1));
    }

    @Test
    void create_should_keep_assignedAt_when_provided() {
        OffsetDateTime ts = OffsetDateTime.parse("2025-01-01T10:00:00Z");
        UserRole ur = UserRole.create(UserId.of(1), RoleCode.of("ADMIN"), "system", ts);
        assertThat(ur.getAssignedAt()).isEqualTo(ts);
    }

    @Test
    void create_should_fail_when_actor_blank() {
        assertThatThrownBy(() -> UserRole.create(UserId.of(1), RoleCode.of("ADMIN"), "  ", null))
                .isInstanceOf(InvariantViolation.class);
    }

    @Test
    void create_should_reject_too_long_actor_or_invalid_chars() {
        String tooLong = "x".repeat(121);
        assertThatThrownBy(() -> UserRole.create(UserId.of(1), RoleCode.of("ADMIN"), tooLong, null))
                .isInstanceOf(InvariantViolation.class);

        String withControl = "sys\t\t"; // control chars should be rejected by your ACTOR_RX
        assertThatThrownBy(() -> UserRole.create(UserId.of(1), RoleCode.of("ADMIN"), withControl, null))
                .isInstanceOf(InvariantViolation.class);
    }
}