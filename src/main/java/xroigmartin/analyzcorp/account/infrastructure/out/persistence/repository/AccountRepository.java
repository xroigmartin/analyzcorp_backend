package xroigmartin.analyzcorp.account.infrastructure.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xroigmartin.analyzcorp.account.infrastructure.out.persistence.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
