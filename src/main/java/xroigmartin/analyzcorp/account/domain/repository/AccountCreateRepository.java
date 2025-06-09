package xroigmartin.analyzcorp.account.domain.repository;

import xroigmartin.analyzcorp.account.domain.model.Account;

public interface AccountCreateRepository {

    Account save(Account account);
}
