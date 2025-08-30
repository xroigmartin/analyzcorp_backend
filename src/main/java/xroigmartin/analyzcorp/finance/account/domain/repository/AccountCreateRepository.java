package xroigmartin.analyzcorp.finance.account.domain.repository;

import xroigmartin.analyzcorp.finance.account.domain.model.Account;

public interface AccountCreateRepository {

    Account create(Account account);
}
