package Controller;

import Model.entity.Account;
import Model.repository.AccountRepository;

public class AccountService {
    AccountRepository accountRepository = new AccountRepository();
    CreditCardService creditCardService  = new CreditCardService();

    public void insert(Account account){
        int accountId = accountRepository.insert(account);
        System.out.println("your account id : " + accountId);
        account.setId(accountId);
        if (accountId>0){
            creditCardService.insert(account);
        }
    }
    public void update(Account account){
        accountRepository.update(account);
    }
}
