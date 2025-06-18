package xroigmartin.analyzcorp.investment.infrastructure.in.rest.companies;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xroigmartin.analyzcorp.investment.application.use_case.GetAllCompaniesUseCase;
import xroigmartin.analyzcorp.investment.application.use_case.ImportCompaniesFromSecUseCase;
import xroigmartin.analyzcorp.investment.domain.model.Company;
import xroigmartin.analyzcorp.investment.infrastructure.in.rest.dto.CompanyDTO;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@AllArgsConstructor
public class CompaniesController {

    private final GetAllCompaniesUseCase getAllCompaniesUseCase;
    private final ImportCompaniesFromSecUseCase importCompaniesFromSecUseCase;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyDTO>> getCompanies(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String ticker,
                                                         @RequestParam(required = false, defaultValue = "10") int limit,
                                                         @RequestParam(required = false, defaultValue = "0") int offset) {

        var companies = getAllCompaniesUseCase.execute();
        return ResponseEntity.ok(toDTO(companies));
    }

    @GetMapping("/import")
    public ResponseEntity<Void> importFromSec(){
        importCompaniesFromSecUseCase.execute();
        return ResponseEntity.accepted().build();
    }

    private List<CompanyDTO> toDTO(List<Company> companies) {
        return companies.stream()
                .map(company -> new CompanyDTO(company.cik(), company.name(), company.ticker()))
                .toList();
    }
}
