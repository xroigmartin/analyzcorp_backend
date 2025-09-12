package xroigmartin.analyzcorp.rbac.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;

class RoleTest {

    @Test
    void create_should_set_version_zero_and_trim_keys() {
        Role r = Role.create(RoleCode.of("ADMIN"), "  i18n.role.admin  ", "  i18n.role.admin.desc  ");
        assertThat(r.getVersion().value()).isZero();
        assertThat(r.getDisplayKey()).isEqualTo("i18n.role.admin");
        assertThat(r.getDescriptionKey()).isEqualTo("i18n.role.admin.desc");
    }

    @Test
    void nextVersion_should_increment() {
        Role r1 = Role.create(RoleCode.of("MANAGER"), "k", "k");
        Role r2 = r1.nextVersion();
        assertThat(r2.getVersion().value()).isEqualTo(1);
        assertThat(r1.getVersion().value()).isEqualTo(0);
    }

    void create_should_reject_too_long_i18n_keys() {
        String longKey = "k".repeat(129); // KEY_MAX = 128
        assertThatThrownBy(() -> Role.create(RoleCode.of("ADMIN"), longKey, "desc"))
                .isInstanceOf(xroigmartin.analyzcorp.rbac.domain.errors.InvariantViolation.class);
        assertThatThrownBy(() -> Role.create(RoleCode.of("ADMIN"), "key", longKey))
                .isInstanceOf(xroigmartin.analyzcorp.rbac.domain.errors.InvariantViolation.class);
    }
}