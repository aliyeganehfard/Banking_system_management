package Controller;

import Model.entity.AccountHibernate;
import Model.repository.AccountRepositoryHibernate;
import Model.repository.Interface;

import java.util.List;

public class AccountServiceHibernate implements Interface<AccountHibernate> {
    private AccountRepositoryHibernate arh ;

    public AccountServiceHibernate() {
        arh = new AccountRepositoryHibernate();
    }

    @Override
    public void save(AccountHibernate accountHibernate) {
        arh.save(accountHibernate);
    }

    @Override
    public void update(AccountHibernate accountHibernate) {
        arh.update(accountHibernate);
    }

    @Override
    public void delete(Integer id) {
        arh.delete(id);
    }

    @Override
    public AccountHibernate findById(Integer id) {
        return arh.findById(id);
    }

    @Override
    public List<AccountHibernate> findAll() {
        return arh.findAll();
    }
}
