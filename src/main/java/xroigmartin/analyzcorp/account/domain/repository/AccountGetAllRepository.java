package xroigmartin.analyzcorp.account.domain.repository;

import xroigmartin.analyzcorp.account.domain.model.Account;

import java.util.List;

public interface AccountGetAllRepository {

    List<Account> getAllAccounts();
}
