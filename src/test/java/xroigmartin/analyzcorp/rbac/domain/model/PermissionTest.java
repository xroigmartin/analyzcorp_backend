package xroigmartin.analyzcorp.rbac.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;

class PermissionTest {

    @Test
    void create_should_set_version_zero_and_trim_keys() {
        Permission p = Permission.create(PermissionCode.of("ACCOUNT_READ"), "  i18n.perm.read  ", "  i18n.perm.read.desc  ");
        assertThat(p.getVersion().value()).isZero();
        assertThat(p.getDisplayKey()).isEqualTo("i18n.perm.read");
        assertThat(p.getDescriptionKey()).isEqualTo("i18n.perm.read.desc");
    }

    @Test
    void nextVersion_should_increment() {
        Permission p1 = Permission.create(PermissionCode.of("ACCOUNT_EDIT"), "k", "k");
        Permission p2 = p1.nextVersion();
        assertThat(p2.getVersion().value()).isEqualTo(1);
        assertThat(p1.getVersion().value()).isEqualTo(0);
    }
}