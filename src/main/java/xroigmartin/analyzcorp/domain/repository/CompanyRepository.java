package xroigmartin.analyzcorp.domain.repository;

import xroigmartin.analyzcorp.domain.model.Company;

import java.util.List;

public interface CompanyRepository {
    List<Company> findByNameOrTicker(String name, String ticker, int limit, int offset);
    List<Company> getAllCompanies();
    Company getCompanyByCik(String cik);
}
