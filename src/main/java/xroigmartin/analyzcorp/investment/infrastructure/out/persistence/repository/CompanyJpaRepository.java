package xroigmartin.analyzcorp.investment.infrastructure.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xroigmartin.analyzcorp.investment.infrastructure.out.persistence.entity.CompanyEntity;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, String> {
}
