package xroigmartin.analyzcorp.infrastructure.out.adapter.sec;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xroigmartin.analyzcorp.domain.model.Company;
import xroigmartin.analyzcorp.domain.repository.CompanyGetAllRepository;

import java.util.List;

@Component("sec_api_client")
@Slf4j
@AllArgsConstructor
public class SecEdgarApiClient implements CompanyGetAllRepository {

    private final SecApiHttpClient secApiHttpClient;
    private final SecCompanyJsonParser secCompanyJsonParser;

    @Override
    public List<Company> getAllCompanies() {
        log.info("Requesting company data from SEC API...");

            var response = secApiHttpClient.fetchCompaniesJson();

            var companies = secCompanyJsonParser.parseCompanyJsonToListOfCompanies(response);

            log.info("Company data successfully retrieved form SEC.");

            return companies;

    }

}
