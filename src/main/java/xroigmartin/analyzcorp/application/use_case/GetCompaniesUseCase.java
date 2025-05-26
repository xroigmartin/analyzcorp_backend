package xroigmartin.analyzcorp.application.use_case;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.domain.model.Company;
import xroigmartin.analyzcorp.domain.repository.CompanyRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GetCompaniesUseCase {

    private final CompanyRepository companyRepository;

    public List<Company> execute(String companyName, String ticker, int limit, int offset) {
        return companyRepository.findByNameOrTicker(companyName, ticker, limit, offset);
    }
}
