package xroigmartin.analyzcorp.finance.account.domain.repository;

import xroigmartin.analyzcorp.finance.account.domain.model.Account;

import java.util.Optional;

public interface AccountGetByIdRepository {

    Optional<Account> findById(Long id);
}
