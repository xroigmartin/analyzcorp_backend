package xroigmartin.analyzcorp.investment.domain.repository;

import xroigmartin.analyzcorp.investment.domain.model.Company;

import java.util.List;

public interface CompanySearchRepository {
    List<Company> findByNameOrTicker(String name, String ticker, int limit, int offset);
}
