package xroigmartin.analyzcorp.rbac.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xroigmartin.analyzcorp.rbac.domain.enums.UserStatus;
import xroigmartin.analyzcorp.rbac.domain.exceptions.InvariantViolation;
import xroigmartin.analyzcorp.rbac.domain.vo.Email;
import xroigmartin.analyzcorp.rbac.domain.vo.UserId;
import xroigmartin.analyzcorp.rbac.domain.vo.UserPublicId;
import xroigmartin.analyzcorp.rbac.domain.vo.Username;

@DisplayName("User aggregate")
class UserTest {

    @Test
    void createNew_should_set_defaults() {
        User u = User.createNew(Username.of("alice"), Email.of("a@b.com"), false, true, true);

        assertThat(u.getId()).isNull();
        assertThat(u.getPublicId()).isNotNull();
        assertThat(u.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(u.getVersion().value()).isZero();
        assertThat(u.isService()).isFalse();
        assertThat(u.isProtected()).isTrue();
        assertThat(u.isCanLogin()).isTrue();
    }

    @Test
    void rehydrate_should_restore_exact_state() {
        User u = User.rehydrate(
                UserId.of(10L),
                UserPublicId.random(),
                Username.of("bob"),
                Email.of("b@c.com"),
                UserStatus.SUSPENDED,
                true, false, false,
                new Version(3)
        );
        assertThat(u.getId().value()).isEqualTo(10L);
        assertThat(u.getStatus()).isEqualTo(UserStatus.SUSPENDED);
        assertThat(u.getVersion().value()).isEqualTo(3);
        assertThat(u.isService()).isTrue();
        assertThat(u.isCanLogin()).isFalse();
        assertThat(u.isProtected()).isFalse();
    }

    @Test
    void requireLoginAllowed_should_pass_only_for_ACTIVE_and_canLogin_true() {
        User ok = User.createNew(Username.of("john"), Email.of("j@e.com"), false, true, false);
        ok.requireLoginAllowed(); // no exception

        User flagFalse = ok.withCanLogin(false);
        assertThatThrownBy(flagFalse::requireLoginAllowed)
                .isInstanceOf(InvariantViolation.class);

        User inactive = ok.withStatus(UserStatus.INACTIVE);
        assertThatThrownBy(inactive::requireLoginAllowed)
                .isInstanceOf(InvariantViolation.class);
    }

    @Test
    void requireDeletable_should_enforce_protection() {
        User protectedUser = User.createNew(Username.of("john"), Email.of("j@e.com"), false, true, true);
        assertThatThrownBy(() -> protectedUser.requireDeletable(false))
                .isInstanceOf(InvariantViolation.class);

        // override=true allows deletion
        protectedUser.requireDeletable(true);
    }

    @Test
    void nextVersion_should_return_copy_with_incremented_version() {
        User u = User.createNew(Username.of("mike"), Email.of("m@e.com"), false, true, false);
        User u2 = u.nextVersion();

        assertThat(u2.getVersion().value()).isEqualTo(1);
        assertThat(u.getVersion().value()).isEqualTo(0);
    }

    @Test
    void toString_should_not_leak_pii_fields() {
        User u = User.createNew(Username.of("alice"), Email.of("a@b.com"), false, true, true);
        String s = u.toString();

        assertThat(s).doesNotContain("a@b.com");  // email excluded
        assertThat(s).doesNotContain("canLogin"); // flag excluded if you used @ToString(exclude=..)
        assertThat(s).doesNotContain("isProtected");
        // sanity: still contains some identifying safe info
        assertThat(s).contains("alice");
    }
}