package xroigmartin.analyzcorp.application.use_case;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.domain.model.Company;
import xroigmartin.analyzcorp.domain.repository.CompanyRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCompaniesUseCase {

    private final CompanyRepository companyRepository;

    public List<Company> execute() {
        return companyRepository.getAllCompanies();
    }
}
