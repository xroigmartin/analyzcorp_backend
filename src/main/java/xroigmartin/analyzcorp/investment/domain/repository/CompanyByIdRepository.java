package xroigmartin.analyzcorp.investment.domain.repository;

import xroigmartin.analyzcorp.investment.domain.model.Company;

public interface CompanyByIdRepository {
    Company findByCik(String cik);
}
