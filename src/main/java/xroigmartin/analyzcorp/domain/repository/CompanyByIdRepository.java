package xroigmartin.analyzcorp.domain.repository;

import xroigmartin.analyzcorp.domain.model.Company;

public interface CompanyByIdRepository {
    Company findByCik(String cik);
}
