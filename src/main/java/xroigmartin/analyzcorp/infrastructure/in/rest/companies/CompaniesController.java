package xroigmartin.analyzcorp.infrastructure.in.rest.companies;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xroigmartin.analyzcorp.application.use_case.GetAllCompaniesUseCase;
import xroigmartin.analyzcorp.domain.model.Company;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@AllArgsConstructor
public class CompaniesController {

    private final GetAllCompaniesUseCase getAllCompaniesUseCase;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Company>> getCompanies(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String ticker,
                                                      @RequestParam(required = false, defaultValue = "10") int limit,
                                                      @RequestParam(required = false, defaultValue = "0") int offset) {

        var companies = getAllCompaniesUseCase.execute();
        return ResponseEntity.ok(companies);
    }
}
