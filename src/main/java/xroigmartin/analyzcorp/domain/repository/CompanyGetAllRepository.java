package xroigmartin.analyzcorp.domain.repository;

import xroigmartin.analyzcorp.domain.model.Company;

import java.util.List;

public interface CompanyGetAllRepository {
    List<Company> getAllCompanies();
}
