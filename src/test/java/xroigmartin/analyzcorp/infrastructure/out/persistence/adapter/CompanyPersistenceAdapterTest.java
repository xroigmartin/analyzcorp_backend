package xroigmartin.analyzcorp.infrastructure.out.persistence.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xroigmartin.analyzcorp.domain.model.Company;
import xroigmartin.analyzcorp.infrastructure.out.persistence.entity.CompanyEntity;
import xroigmartin.analyzcorp.infrastructure.out.persistence.repository.CompanyJpaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyPersistenceAdapterTest {

    @Mock
    private CompanyJpaRepository companyJpaRepository;

    @InjectMocks
    private CompanyPersistenceAdapter adapter;

    @Test
    void shouldGetAllCompaniesFromJpaRepository() {
        // given
        List<CompanyEntity> entities = List.of(
                CompanyEntity.builder().cik("0000000001").name("Apple").ticker("AAPL").build(),
                CompanyEntity.builder().cik("0000000002").name("Microsoft").ticker("MSFT").build()
        );
        when(companyJpaRepository.findAll()).thenReturn(entities);

        // when
        List<Company> companies = adapter.getAllCompanies();

        // then
        assertEquals(2, companies.size());
        assertEquals("0000000001", companies.get(0).cik());
        assertEquals("Apple", companies.get(0).name());
        verify(companyJpaRepository).findAll();
    }

    @Test
    void shouldFindCompanyByCik() {
        // given
        CompanyEntity entity = CompanyEntity.builder()
                .cik("0000000001")
                .name("Apple")
                .ticker("AAPL")
                .build();
        when(companyJpaRepository.findById("0000000001")).thenReturn(Optional.of(entity));

        // when
        Company result = adapter.findByCik("0000000001");

        // then
        assertNotNull(result);
        assertEquals("Apple", result.name());
        verify(companyJpaRepository).findById("0000000001");
    }

    @Test
    void shouldReturnNullWhenCompanyNotFoundByCik() {
        when(companyJpaRepository.findById("notfound")).thenReturn(Optional.empty());

        Company result = adapter.findByCik("notfound");

        assertNull(result);
        verify(companyJpaRepository).findById("notfound");
    }

    @Test
    void shouldSaveAllCompanies() {
        List<Company> companies = List.of(
                new Company("0000000001", "Apple", "AAPL"),
                new Company("0000000002", "Microsoft", "MSFT")
        );

        adapter.saveAllCompanies(companies);

        ArgumentCaptor<List<CompanyEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(companyJpaRepository).saveAll(captor.capture());

        List<CompanyEntity> savedEntities = captor.getValue();
        assertEquals(2, savedEntities.size());
        assertEquals("Apple", savedEntities.get(0).getName());
    }

    @Test
    void shouldSaveSingleCompany() {
        Company company = new Company("0000000001", "Apple", "AAPL");

        adapter.saveCompany(company);

        ArgumentCaptor<CompanyEntity> captor = ArgumentCaptor.forClass(CompanyEntity.class);
        verify(companyJpaRepository).save(captor.capture());

        CompanyEntity savedEntity = captor.getValue();
        assertEquals("0000000001", savedEntity.getCik());
        assertEquals("Apple", savedEntity.getName());
        assertEquals("AAPL", savedEntity.getTicker());
    }

    @Test
    void shouldThrowExceptionForUnimplementedMethod() {
        assertThrows(UnsupportedOperationException.class, () -> adapter.findByNameOrTicker("Apple", "AAPL", 10, 0));
    }
}