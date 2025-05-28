package xroigmartin.analyzcorp.application.use_case;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xroigmartin.analyzcorp.domain.repository.CompanyGetAllRepository;
import xroigmartin.analyzcorp.domain.repository.SaveCompanyRepository;

@Service
public class ImportCompaniesFromSecUseCase {

    private final CompanyGetAllRepository secApiClient;
    private final SaveCompanyRepository jpaRepositoryAdapter;

    public ImportCompaniesFromSecUseCase(@Qualifier("sec_api_client") CompanyGetAllRepository secApiClient,
                                         @Qualifier("jpa") SaveCompanyRepository jpaRepositoryAdapter) {
        this.secApiClient = secApiClient;
        this.jpaRepositoryAdapter = jpaRepositoryAdapter;
    }

    public void execute() {
        var companies = secApiClient.getAllCompanies();

        if (companies.isEmpty()) {
            return;
        }

        jpaRepositoryAdapter.saveAllCompanies(companies);

    }
}
