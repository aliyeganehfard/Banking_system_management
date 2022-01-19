package Controller;

import Model.*;

import java.util.function.LongBinaryOperator;

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
