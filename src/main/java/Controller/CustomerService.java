package Controller;

import Model.entity.Account;
import Model.entity.AccountHibernate;
import Model.entity.CreditCard;
import Model.entity.Customer;
import Model.repository.AccountRepository;
import Model.repository.CreditCardRepository;
import Model.repository.CustomerRepository;
import Model.repository.TransactionRepository;

import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepository();
    private AccountRepository accountRepository = new AccountRepository();
    private CreditCardRepository creditCardRepository = new CreditCardRepository();
    private TransactionRepository transactionRepository = new TransactionRepository();

//    insert customer
    public void insert(Customer customer){
        customerRepository.insert(customer);
    }

//    show all customer account
    public List<AccountHibernate> findAllAccount(Customer customer){
        return accountRepository.findAll(customer);
    }
//    show credit card
    public List<CreditCard> findAllCreditCard(Customer customer){
        return creditCardRepository.findById(customer);
    }
//    show customer
    public Customer findByID(String nationalCode){
        return customerRepository.findById(nationalCode);
    }

    public Customer findByID(Customer customer){
        return customerRepository.findById(customer.getNationalCode() , customer.getBankBranch().getId());
    }
    public Customer findByID(String nationalCode , Integer branchId){
        return customerRepository.findById(nationalCode,branchId);
    }

//    show customer account
    public List<Account> getAccount(Integer id){
        return customerRepository.findAccount(id);
    }
}
