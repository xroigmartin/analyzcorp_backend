package xroigmartin.analyzcorp.investment.domain.repository;

import xroigmartin.analyzcorp.investment.domain.model.Company;

import java.util.List;

public interface SaveCompanyRepository {
    void saveAllCompanies(List<Company> companies);
    void saveCompany(Company company);
}
