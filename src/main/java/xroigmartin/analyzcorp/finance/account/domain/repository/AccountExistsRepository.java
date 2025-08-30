package xroigmartin.analyzcorp.finance.account.domain.repository;

public interface AccountExistsRepository {

    boolean existsAccountByName(String name);
    boolean existsAccountById(Long id);
}
