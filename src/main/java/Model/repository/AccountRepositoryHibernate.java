package Model.repository;

import Model.entity.AccountHibernate;
import Model.repository.Interface;
import Model.util.SingleTonConnection;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AccountRepositoryHibernate implements Interface<AccountHibernate> {
    private SessionFactory sessionFactory = SingleTonConnection.getInstance();

    @Override
    public void save(AccountHibernate accountHibernate) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.save(accountHibernate);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(AccountHibernate accountHibernate) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.update(accountHibernate);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Integer id) {
        try(var session = sessionFactory.openSession()){
            var transaction = session.beginTransaction();
            try {
                session.delete(id);
                transaction.commit();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public AccountHibernate findById(Integer id) {
        try(var session = sessionFactory.openSession()){
            return session.get(AccountHibernate.class,id);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AccountHibernate> findAll() {
        try(var session = sessionFactory.openSession()){
            var criteria = session.createCriteria(AccountHibernate.class);
            List<AccountHibernate> accounts = criteria.list();
            return accounts;
        }
    }
}
