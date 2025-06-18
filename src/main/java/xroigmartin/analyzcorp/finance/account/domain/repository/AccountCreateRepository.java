package xroigmartin.analyzcorp.finance.account.domain.repository;

import xroigmartin.analyzcorp.finance.account.domain.model.Account;

public interface AccountCreateRepository {

    Account save(Account account);
}
