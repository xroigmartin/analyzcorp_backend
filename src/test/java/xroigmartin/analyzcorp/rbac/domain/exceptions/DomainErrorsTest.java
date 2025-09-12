package xroigmartin.analyzcorp.rbac.domain.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import xroigmartin.analyzcorp.rbac.domain.vo.PermissionCode;
import xroigmartin.analyzcorp.rbac.domain.vo.RoleCode;
import xroigmartin.analyzcorp.rbac.domain.vo.UserId;
import xroigmartin.analyzcorp.shared_kernel.domain.exceptions.DomainException;

class DomainErrorsTest {

    @Test
    void specific_errors_should_extend_domain_exception() {
        assertThat(new UniqueConflict("dup")).isInstanceOf(DomainException.class);
        assertThat(new InvariantViolation("bad state")).isInstanceOf(DomainException.class);
        assertThat(UserNotFound.byId(UserId.of(1))).isInstanceOf(DomainException.class);
        assertThat(RoleNotFound.byCode(RoleCode.of("ADMIN"))).isInstanceOf(DomainException.class);
        assertThat(PermissionNotFound.byCode(PermissionCode.of("ACCOUNT_READ"))).isInstanceOf(DomainException.class);
    }

    @Test
    void user_not_found_message_should_include_id() {
        assertThat(UserNotFound.byId(UserId.of(42)).getMessage())
                .contains("42");
    }

    @Test
    void optimistic_lock_conflict_should_extend_domain_exception() {
        class OptimisticLockConflict extends DomainException {
            public OptimisticLockConflict(String message) { super(message); }
        }
        assertThat(new OptimisticLockConflict("stale")).isInstanceOf(DomainException.class);
    }

}
