package xroigmartin.analyzcorp.finance.account.domain.repository;

import xroigmartin.analyzcorp.finance.account.domain.model.Account;

import java.util.List;

public interface AccountGetAllRepository {

    List<Account> getAllAccounts();
}
