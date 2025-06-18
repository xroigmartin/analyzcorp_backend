package xroigmartin.analyzcorp.investment.application.use_case;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.investment.domain.model.Company;
import xroigmartin.analyzcorp.investment.domain.repository.CompanyGetAllRepository;

import java.util.List;

@Service
public class GetAllCompaniesUseCase {

    private final CompanyGetAllRepository companyGetAllRepository;

    public GetAllCompaniesUseCase( @Qualifier("jpa") CompanyGetAllRepository companyGetAllRepository) {
        this.companyGetAllRepository = companyGetAllRepository;
    }

    public List<Company> execute() {
        return companyGetAllRepository.getAllCompanies();
    }
}
