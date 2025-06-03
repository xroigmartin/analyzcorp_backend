package xroigmartin.analyzcorp.application.use_case;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xroigmartin.analyzcorp.domain.model.Company;
import xroigmartin.analyzcorp.domain.repository.CompanyGetAllRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllCompaniesUseCaseTest {

    @Mock
    private CompanyGetAllRepository companyGetAllRepository;

    @InjectMocks
    private GetAllCompaniesUseCase getAllCompaniesUseCase;

    @Test
    void shouldReturnAllCompaniesFromRepository() {
        // given
        List<Company> expected = List.of(
                new Company("0000000001", "Apple Inc.", "AAPL"),
                new Company("0000000002", "Microsoft Corp.", "MSFT")
        );
        when(companyGetAllRepository.getAllCompanies()).thenReturn(expected);

        // when
        List<Company> result = getAllCompaniesUseCase.execute();

        // then
        assertEquals(expected, result);
        verify(companyGetAllRepository).getAllCompanies();
    }
}
