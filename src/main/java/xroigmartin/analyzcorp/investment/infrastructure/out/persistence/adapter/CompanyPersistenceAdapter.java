package xroigmartin.analyzcorp.investment.infrastructure.out.persistence.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xroigmartin.analyzcorp.investment.domain.model.Company;
import xroigmartin.analyzcorp.investment.domain.repository.CompanyByIdRepository;
import xroigmartin.analyzcorp.investment.domain.repository.CompanyGetAllRepository;
import xroigmartin.analyzcorp.investment.domain.repository.CompanySearchRepository;
import xroigmartin.analyzcorp.investment.domain.repository.SaveCompanyRepository;
import xroigmartin.analyzcorp.investment.infrastructure.out.persistence.entity.CompanyEntity;
import xroigmartin.analyzcorp.investment.infrastructure.out.persistence.repository.CompanyJpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component("jpa")
@AllArgsConstructor
public class CompanyPersistenceAdapter implements CompanyGetAllRepository, SaveCompanyRepository, CompanyByIdRepository, CompanySearchRepository {

    private final CompanyJpaRepository companyJpaRepository;

    @Override
    public List<Company> findByNameOrTicker(String name, String ticker, int limit, int offset) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyJpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Company findByCik(String cik) {
        return companyJpaRepository.findById(cik).map(this::toDomain).orElse(null);
    }

    @Override
    public void saveAllCompanies(List<Company> companies) {
        List<CompanyEntity> entities = companies.stream()
                .map(c -> CompanyEntity.builder()
                        .cik(c.cik())
                        .name(c.name())
                        .ticker(c.ticker())
                        .createdAt(OffsetDateTime.now())
                        .createdBy("sec-import")
                        .build())
                .collect(Collectors.toList());

        companyJpaRepository.saveAll(entities);
    }

    @Override
    public void saveCompany(Company company) {
        var companyToSave = CompanyEntity.builder()
                .cik(company.cik())
                .name(company.name())
                .ticker(company.ticker())
                .createdAt(OffsetDateTime.now())
                .createdBy("sec-import")
                .build();

        companyJpaRepository.save(companyToSave);
    }

    private Company toDomain(CompanyEntity entity) {
        return new Company(entity.getCik(), entity.getName(), entity.getTicker());
    }
}
