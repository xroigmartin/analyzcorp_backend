package xroigmartin.analyzcorp.rbac.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;
import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;

class RolePermissionTest {

    @Test
    void create_should_default_grantedAt_when_null_and_trim_actor() {
        OffsetDateTime before = OffsetDateTime.now();
        RolePermission rp = RolePermission.create(RoleCode.of("ADMIN"), PermissionCode.of("ACCOUNT_READ"), " system ", null);
        OffsetDateTime after = OffsetDateTime.now();

        assertThat(rp.getGrantedBy()).isEqualTo("system");
        assertThat(rp.getGrantedAt()).isNotNull();
        assertThat(rp.getGrantedAt()).isBetween(before.minusSeconds(1), after.plusSeconds(1));
    }

    @Test
    void create_should_keep_grantedAt_when_provided() {
        OffsetDateTime ts = OffsetDateTime.parse("2025-01-01T10:00:00Z");
        RolePermission rp = RolePermission.create(RoleCode.of("ADMIN"), PermissionCode.of("ACCOUNT_READ"), "system", ts);
        assertThat(rp.getGrantedAt()).isEqualTo(ts);
    }

    @Test
    void create_should_fail_when_actor_blank_or_invalid() {
        assertThatThrownBy(() -> RolePermission.create(RoleCode.of("ADMIN"), PermissionCode.of("ACCOUNT_READ"), "  ", null))
                .isInstanceOf(InvariantViolation.class);

        String withControl = "sys\n";
        assertThatThrownBy(() -> RolePermission.create(RoleCode.of("ADMIN"), PermissionCode.of("ACCOUNT_READ"), withControl, null))
                .isInstanceOf(InvariantViolation.class);
    }

    @Test
    void create_should_reject_too_long_i18n_keys() {
        String longKey = "k".repeat(129); // KEY_MAX = 128
        assertThatThrownBy(() -> Permission.create(PermissionCode.of("ACCOUNT_READ"), longKey, "desc"))
                .isInstanceOf(xroigmartin.analyzcorp.rbac.domain.errors.InvariantViolation.class);
        assertThatThrownBy(() -> Permission.create(PermissionCode.of("ACCOUNT_READ"), "key", longKey))
                .isInstanceOf(xroigmartin.analyzcorp.rbac.domain.errors.InvariantViolation.class);
    }

}

