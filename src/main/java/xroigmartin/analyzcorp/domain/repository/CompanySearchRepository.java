package xroigmartin.analyzcorp.domain.repository;

import xroigmartin.analyzcorp.domain.model.Company;

import java.util.List;

public interface CompanySearchRepository {
    List<Company> findByNameOrTicker(String name, String ticker, int limit, int offset);
}
