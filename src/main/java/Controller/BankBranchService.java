package Controller;

import Model.entity.Account;
import Model.entity.BankBranch;
import Model.entity.BankEmployee;
import Model.entity.Customer;
import Model.repository.*;

import java.util.List;

public class BankBranchService {
    private BankBranchRepository bankBranchRepository = new BankBranchRepository();
    private BankEmployeeRepository bankEmployeeRepository = new BankEmployeeRepository();
    private CustomerRepository customerRepository = new CustomerRepository();
    private AccountRepository accountRepository = new AccountRepository();
    private TransactionRepository transactionRepository = new TransactionRepository();

    //    insert branch
    public void insert(BankBranch bankBranch) {
        bankBranchRepository.insert(bankBranch);
    }

    //    show all the bank branch
    public List<BankBranch> findAll() {
        return bankBranchRepository.findAll();
    }

    //    show selected bank branch
    public BankBranch findById(Integer id) {
        return bankBranchRepository.findById(id);
    }

    //    show all bank branch employee
    public List<BankEmployee> findAllEmployee(BankBranch bankBranch) {
        return bankEmployeeRepository.findAll(bankBranch);
    }

    //    show all customer of bank branch
    public List<Customer> findAllCustomer(BankBranch bankBranch) {
        return customerRepository.findAll(bankBranch);
    }

    //    show all account of bank branch
    public List<Account> findAllAccount(BankBranch bankBranch) {
        return accountRepository.findAll(bankBranch);
    }

//    show all transaction of bank branch
//    public List<Transaction> findAllTransaction(BankBranch bankBranch){
//        return transactionRepository.findById();
//    }
}
