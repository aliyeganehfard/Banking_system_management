package Controller;

import Model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TransactionService {
    private AccountRepository accountRepository = new AccountRepository();
    private CreditCardRepository creditCardRepository = new CreditCardRepository();
    private AccountService accountService = new AccountService();
    private TransactionRepository transactionRepository = new TransactionRepository();
    private BlockAccountRepository blockAccountRepository = new BlockAccountRepository();
    private BankBranchRepository bankBranchRepository = new BankBranchRepository();

    //withdraw
    public void withdraw(Integer id, Long amount, TransactionType type) {
        Account account = accountRepository.findById(id);

        if (account == null) {
            System.out.println("account not found !");
            return;
        }
        if (account.getBalance() < amount) {
            System.out.println("not enough money!");
            return;
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.update(account);
        CreditCard creditCard = creditCardRepository.findById(account.getId());
        Transaction transaction = new Transaction(
                account,
                creditCard,
                amount,
                type,
                Date.valueOf(LocalDate.now())
        );
        transactionRepository.insert(transaction);
    }

    //    deposit
    public void deposit(Integer id, Long amount, TransactionType type) {
        Account account = accountRepository.findById(id);
        if (account == null) {
            System.out.println("not found account!");
            return;
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.update(account);
        CreditCard creditCard = creditCardRepository.findById(account.getId());

        Transaction transaction = new Transaction(
                account,
                creditCard,
                amount,
                type,
                Date.valueOf(LocalDate.now())
        );
        transactionRepository.insert(transaction);
    }

    //    card to card
//    public void cardToCard(Integer accountId, String destinationCardNumber, Long amount) {
//        Account account = accountRepository.findById(accountId);
//        CreditCard creditCard = creditCardRepository.findById(account.getId());
//
//        CreditCard destinationCreditCard = creditCardRepository.findByCardNumber(destinationCardNumber);
//        if (creditCard == null) {
//            System.out.println("destination card not found !");
//            return;
//        }
//        Account destinationAccount = accountRepository.findById(destinationCreditCard.getAccount().getId());
//        if (destinationCreditCard == null) {
//            System.out.println("credit card not found !");
//            return;
//        }
//
//        if (account.getBalance() < amount) {
//            System.out.println("not enough money!");
//            return;
//        }
//
//
//        account.setBalance(account.getBalance() - amount);
//        accountService.update(account);
//        Transaction transaction =
//                new Transaction(
//                        account,
//                        creditCard,
//                        amount,
//                        TransactionType.CART_TO_CARD_SENDER,
//                        Date.valueOf(LocalDate.now())
//                );
//        transactionRepository.insert(transaction);
//
//        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
//        accountService.update(destinationAccount);
//        Transaction transaction1 =
//                new Transaction(
//                        destinationAccount,
//                        destinationCreditCard,
//                        amount,
//                        TransactionType.CART_TO_CARD_RECEIVER,
//                        Date.valueOf(LocalDate.now())
//                );
//        transactionRepository.insert(transaction1);
//    }

    public void cardToCard(String cardNumber1, String cardNumber2, String cvv2, Long amount) {
        try {
            if (!isExist(cardNumber1, cardNumber2, cvv2, amount)) {
                return;
            }
        }catch (Exception e){
            System.out.println("wrong input");
        }


        CreditCard creditCard1 = creditCardRepository.findByCardNumber(cardNumber1);
        CreditCard creditCard2 = creditCardRepository.findByCardNumber(cardNumber2);
        BlockAccount blockAccount = blockAccountRepository.findById(
                creditCard1.getAccount().getCustomer().getBankBranch().getId(),
                creditCard1.getAccount().getId()
        );
        if (blockAccount != null){
            System.out.println("your account blocked !");
            return;
        }

        withdraw(creditCard1.getAccount().getId(), amount-600, TransactionType.CART_TO_CARD_SENDER);
        deposit(creditCard2.getAccount().getId(), amount, TransactionType.CART_TO_CARD_RECEIVER);
    }

    public boolean isExist(String cardNumber1, String cardNumber2, String cvv2, Long amount) {

        CreditCard creditCard = creditCardRepository.findByCardNumber(cardNumber1);
        CreditCard creditCard1 = creditCardRepository.findByCardNumber(cardNumber2);
        if (creditCard == null || creditCard1 == null) {
            System.out.println("wrong card number");
            return false;
        }
        if (!creditCard.getCvv2().equals(cvv2)) {
            System.out.println("wrong cvv2");
            return false;
        }
        Scanner scanner = new Scanner(System.in);
        if (creditCard.getPassword().length() >= 1) {
            for (int i = 0; i < 3; i++) {
                System.out.print("enter your password : ");
                String pass = scanner.nextLine();
                if (creditCard.getPassword().equals(pass)) {
                    return true;
                }
            }
            Account account = accountRepository.findById(creditCard.getAccount().getId());
            BankBranch bankBranch = bankBranchRepository.findById(account.getCustomer().getBankBranch().getId());
            blockAccountRepository.insert(new BlockAccount(bankBranch, account));
        } else {
            return true;
        }

        return false;
    }


    // show transaction of card
    public void printTransaction(Integer accountID, Date date) {
        List<Transaction> transactionList = transactionRepository.findById(accountID, date);
        for (int i = 0; i < transactionList.size(); i++) {
            System.out.println(transactionList.get(i));
        }
    }
}
