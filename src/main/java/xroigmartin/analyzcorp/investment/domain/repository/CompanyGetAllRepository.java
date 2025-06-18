package xroigmartin.analyzcorp.investment.domain.repository;

import xroigmartin.analyzcorp.investment.domain.model.Company;

import java.util.List;

public interface CompanyGetAllRepository {
    List<Company> getAllCompanies();
}
