package xroigmartin.analyzcorp.domain.repository;

import xroigmartin.analyzcorp.domain.model.Company;

import java.util.List;

public interface SaveCompanyRepository {
    void saveAllCompanies(List<Company> companies);
    void saveCompany(Company company);
}
