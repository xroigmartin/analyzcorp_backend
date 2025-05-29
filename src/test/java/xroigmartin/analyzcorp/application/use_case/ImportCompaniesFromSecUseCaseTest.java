package xroigmartin.analyzcorp.application.use_case;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xroigmartin.analyzcorp.domain.model.Company;
import xroigmartin.analyzcorp.domain.repository.CompanyGetAllRepository;
import xroigmartin.analyzcorp.domain.repository.SaveCompanyRepository;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImportCompaniesFromSecUseCaseTest {

    @Mock
    private CompanyGetAllRepository companyGetAllRepository;

    @Mock
    private SaveCompanyRepository saveCompanyRepository;

    @InjectMocks
    private ImportCompaniesFromSecUseCase importCompaniesFromSecUseCase;

    @Test
    void shouldImportCompaniesFromSecAndSaveThem() {
        List<Company> companies = List.of(
                new Company("0000000001", "Apple Inc.", "AAPL"),
                new Company("0000000002", "Microsoft Corp.", "MSFT")
        );

        when(companyGetAllRepository.getAllCompanies()).thenReturn(companies);

        importCompaniesFromSecUseCase.execute();

        verify(companyGetAllRepository).getAllCompanies();
        verify(saveCompanyRepository).saveAllCompanies(companies);
    }

    @Test
    void shouldNotSaveWhenSecReturnsEmptyList() {
        when(companyGetAllRepository.getAllCompanies()).thenReturn(List.of());

        importCompaniesFromSecUseCase.execute();

        verify(companyGetAllRepository).getAllCompanies();
        verifyNoInteractions(saveCompanyRepository);
    }
}
