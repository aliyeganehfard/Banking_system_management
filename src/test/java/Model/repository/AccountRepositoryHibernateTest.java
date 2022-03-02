package Model.repository;

import Model.entity.AccountHibernate;
import Model.util.SingleTonConnection;
import org.hibernate.SessionFactory;
import org.hibernate.bytecode.enhance.spi.interceptor.SessionAssociableInterceptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryHibernateTest {
    private AccountRepositoryHibernate accountRepositoryHibernate;

    @BeforeAll
    public static void setup(){
        SessionFactory sessionFactory = SingleTonConnection.getInstance();
    }

    @BeforeEach
    public void beforeEach(){
        accountRepositoryHibernate = new AccountRepositoryHibernate();
    }
    @Test
    void save() {
//        arrange
        var accountHibernate = new AccountHibernate(null,5,5000L);
//        act
        accountRepositoryHibernate.save(accountHibernate);
        var load = accountRepositoryHibernate.findById(accountHibernate.getId());
//        assent
        assertEquals(accountHibernate.getId(),load.getId());
    }

    @Test
    void update() {
//        arrange
        var accountHibernate  =  new AccountHibernate(null,5,6000L);
//        act
        accountRepositoryHibernate.save(accountHibernate);
        accountHibernate.setBalance(8000L);
        accountRepositoryHibernate.update(accountHibernate);
//        assert
        assertEquals(
                accountHibernate.getCustomerId(),
                accountRepositoryHibernate.findById(accountHibernate.getId()).getCustomerId()
        );
    }

    @Test
    void delete() {
//        arrange
        var accountHibernate  =  new AccountHibernate(null,5,6000L);
//        act
        accountRepositoryHibernate.save(accountHibernate);
        int size = accountRepositoryHibernate.findAll().size();
        accountRepositoryHibernate.delete(accountHibernate.getId());
//        assert
        assertEquals(
                size,
                accountRepositoryHibernate.findAll().size()
                );
    }

    @Test
    void findById() {
//        arrange
        var accountHibernate  =  new AccountHibernate(null,5,6000L);
//        act
        accountRepositoryHibernate.save(accountHibernate);
        var load = accountRepositoryHibernate.findById(accountHibernate.getId());
//        assert
        assertAll(
                ()-> assertEquals(accountHibernate.getId(),load.getId()),
                ()-> assertEquals(accountHibernate.getCustomerId(),load.getCustomerId())
        );
    }

    @Test
    void findAll() {
//        arrange
        var size = accountRepositoryHibernate.findAll().size();
        var account1 = new AccountHibernate(null,5,1000L);
        var account2 = new AccountHibernate(null,5,2000L);
        var account3 = new AccountHibernate(null,5,3000L);
//        act
        size +=3;
        accountRepositoryHibernate.save(account1);
        accountRepositoryHibernate.save(account2);
        accountRepositoryHibernate.save(account3);
//        assert
        assertEquals(
                size,
                accountRepositoryHibernate.findAll().size()
                );
    }
}