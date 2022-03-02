import Controller.*;
import Exceptions.*;
import Model.entity.*;
import Model.util.TransactionType;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Bank bank = null;
        BankBranch bankBranch = null;
        Customer customer = null;

        var bankService = new BankService();
        var bankBranchService = new BankBranchService();
        var customerService = new CustomerService();
        var bankEmployeeService = new BankEmployeeService();
        var accountService = new AccountServiceHibernate();
//        var accountService = new AccountService();
        var blockAccountService = new BlockAccountService();
        var creditCardService = new CreditCardService();
        var transactionService = new TransactionService();

        var scn = new Scanner(System.in);
        Integer bankId = null;
        Integer customerId = null;
        Integer bankBranchId = null;
        int customerState = 1;
        int type = 0;
        boolean flag = true;
        boolean state = false;
        String[] commend;
        String commendLine = "";
        showMenu();
        while (flag) {
            System.out.print("commend : ");
            commendLine = scn.nextLine();
            commend = commendLine.trim().split(" ");
            switch (commend[0]) {
                case "addBank":
                    try {
                        ExceptionHandling.isWord(commend[1]);
                        bankService.insert(new Bank(commend[1]));
                    } catch (WordException wordException) {
                        System.out.println("incorrect name!");
                    } catch (Exception e){
                        System.out.println("wrong input!");
                    }

                    break;
                case "showBankBranch":
                    List<BankBranch> bankBranches = bankBranchService.findAll();
                    for (BankBranch branch : bankBranches) {
                        System.out.println(branch);
                    }

                    break;
                case "customerCreatAccount":
                    try {
                        ExceptionHandling.isWord(commend[2]);
                        ExceptionHandling.isNationalCode(commend[3]);
                        ExceptionHandling.isPhoneNumber(commend[4]);
                        customerService.insert(
                                new Customer(
                                        bankBranchService.findById(Integer.valueOf(commend[1])),
                                        commend[2],
                                        commend[3],
                                        commend[4]
                                )
                        );
                    } catch (NullException nullException) {
                        System.out.println("bank branch not found!");
                    } catch (WordException wordException) {
                        System.out.println("incorrect input for name!");
                    } catch (NationalCodeException nationalCodeException) {
                        System.out.println("incorrect National code!");
                    } catch (PhoneNumberException phoneNumberException) {
                        System.out.println("incorrect phone number!");
                    } catch (Exception e){
                        System.out.println("wrong input!");
                    }
                    break;
                case "bankLogin":
                    try {
                        ExceptionHandling.isDigit(commend[1]);
                        bank = bankService.findById(Integer.valueOf(commend[1]));
                        if (bank != null) {
                            state = true;
                            showBankMenu();
                            bankId = bank.getId();
                            type = 1;
                        }
                    } catch (DigitException digitException) {
                        System.out.println("please enter the number!");
                    } catch (NullException nullException) {
                        System.out.println("bank not found!");
                    }  catch (Exception e){
                        System.out.println("wrong input!");
                    }
                    break;
                case "bankBranchLogin":
                    try {
                        ExceptionHandling.isDigit(commend[1]);
                        bankBranch = bankBranchService.findById(Integer.valueOf(commend[1]));
                        if (bankBranch != null) {
                            state = true;
                            showBranchMenu();
                            bankBranchId = bankBranch.getId();
                            type = 2;
                        }
                    } catch (DigitException digitException) {
                        System.out.println("please enter the number!");
                    } catch (NullException nullException) {
                        System.out.println("bank branch not found!");
                    }  catch (Exception e){
                        System.out.println("wrong input!");
                    }
                    break;
                case "customerLogin":
                    try {
                        ExceptionHandling.isNationalCode(commend[1]);
                        customer = customerService.findByID(commend[1], Integer.valueOf(commend[2]));
                        if (customer != null) {
                            state = true;
                            showCustomerMenu();
                            type = 3;
                        }
                    } catch (NationalCodeException nationalCodeException) {
                        System.out.println("incorrect national code!");
                    } catch (NullException nullException) {
                        System.out.println("customer not found!");
                    }  catch (Exception e){
                        System.out.println("wrong input!");
                    }
                    break;
                case "menu":
                    showMenu();
                    break;
                default:
                    System.out.println("wrong input !");
                    break;
            }
            while (state) {
                switch (type) {
                    case 1:
                        System.out.print("commend : ");
                        commendLine = scn.nextLine();
                        commend = commendLine.trim().split(" ");
                        switch (commend[0]) {
                            case "showProfile":
                                System.out.println(bankService.findById(bankId));
                                break;
                            case "showBranch":
                                List<BankBranch> bankBranches = bankService.findAllBranch(bank);
                                for (BankBranch branch : bankBranches) {
                                    System.out.println(branch);
                                }
                                break;
                            case "addBranch":
                                try {
                                    ExceptionHandling.isWord(commend[1]);
                                    ExceptionHandling.isNationalCode(commend[2]);
                                    bankBranchService.insert(new BankBranch(
                                                    bankService.findById(bankId),
                                                    commend[1],
                                                    commend[2],
                                                    commend[3]
                                            )
                                    );
                                } catch (WordException wordException) {
                                    System.out.println("incorrect name!");
                                } catch (NationalCodeException nationalCodeException) {
                                    System.out.println("incorrect national code");
                                } catch (Exception e){
                                    System.out.println("wrong input!");
                                }
                                break;
                            case "help":
                                showBankMenu();
                                break;
                            case "logout":
                                state = false;
                                showMenu();
                                break;
                            default:
                                System.out.println("wrong input ! ");
                                break;
                        }
                        break;
                    case 2:
                        System.out.print("commend : ");
                        commendLine = scn.nextLine();
                        commend = commendLine.trim().split(" ");
                        switch (commend[0]) {
                            case "showProfile":
                                System.out.println(bankBranchService.findById(bankBranchId));
                                break;
                            case "showEmployee":
                                List<BankEmployee> bankEmployees = bankBranchService.findAllEmployee(bankBranch);
                                for (BankEmployee employee : bankEmployees) {
                                    System.out.println(employee);
                                }
                                break;
                            case "showCustomer":
                                List<Customer> customers = bankBranchService.findAllCustomer(bankBranch);
                                for (Customer ctmr : customers) {
                                    System.out.println(ctmr);
                                }
                                break;
                            case "showAccount":
                                List<Account> accounts = bankBranchService.findAllAccount(bankBranch);
                                for (Account acnt : accounts) {
                                    System.out.println(acnt);
                                }
                                break;
                            case "showBlockAccount":
                                List<BlockAccount> blockAccountList = blockAccountService.findAll(bankBranch);
                                for (BlockAccount block : blockAccountList) {
                                    System.out.println(block);
                                }
                                break;
                            case "activatedAccount":
                                try {
                                    ExceptionHandling.isDigit(commend[1]);
                                    blockAccountService.delete(Integer.valueOf(commend[1]));
                                } catch (DigitException digitException) {
                                    System.out.println("incorrect input!");
                                }  catch (Exception e){
                                    System.out.println("wrong input!");
                                }
                                break;
                            case "addEmployee":
                                try {
                                    ExceptionHandling.isWord(commend[1]);
                                    ExceptionHandling.isNationalCode(commend[2]);
                                    ExceptionHandling.isPhoneNumber(commend[3]);
                                    bankEmployeeService.insert(new BankEmployee(
                                                    bankBranchService.findById(bankBranchId),
                                                    commend[1],
                                                    commend[2],
                                                    commend[3]
                                            )
                                    );
                                } catch (WordException wordException) {
                                    System.out.println("incorrect name!");
                                } catch (NationalCodeException nationalCodeException) {
                                    System.out.println("incorrect national code!");
                                } catch (PhoneNumberException phoneNumberException) {
                                    System.out.println("incorrect phone number!");
                                }  catch (Exception e){
                                    System.out.println("wrong input!");
                                }

                                break;
                            case "help":
                                showBranchMenu();
                                break;
                            case "logout":
                                state = false;
                                showMenu();
                                break;
                            default:
                                System.out.println("wrong input !");
                                break;
                        }
                        break;
                    case 3:
                        System.out.print("commend : ");
                        commendLine = scn.nextLine();
                        commend = commendLine.trim().split(" ");
                        switch (commend[0]) {
                            case "showProfile":
                                System.out.println(customerService.findByID(customer));
                                break;
                            case "showBankBranch":
                                List<BankBranch> bankBranches = bankBranchService.findAll();
                                for (BankBranch bbrnch : bankBranches) {
                                    System.out.println(bbrnch);
                                }
                                break;
                            case "showAccount":
                                List<Account> accountList = customerService.findAllAccount(customer);
                                for (Account account : accountList) {
                                    System.out.println(account);
                                }
                                break;
                            case "showCreditCard":
                                List<CreditCard> creditCardList = customerService.findAllCreditCard(customer);
                                for (CreditCard creditCard : creditCardList) {
                                    System.out.println(creditCard);
                                }
                                break;
                            case "showTransaction":
                                try {
                                    ExceptionHandling.isDate(commend[2]);
                                    ExceptionHandling.isDigit(commend[1]);
                                    transactionService.printTransaction(
                                            Integer.valueOf(commend[1]),
                                            Date.valueOf(commend[2])
                                    );
                                } catch (DateException dateException) {
                                    System.out.println("incorrect date!");
                                } catch (DigitException digitException) {
                                    System.out.println("incorrect accountId!");
                                }  catch (Exception e){
                                    System.out.println("wrong input!");
                                }

                                break;
                            case "createBankAccount":
                                try {
                                    ExceptionHandling.isDigit(commend[2]);
                                    ExceptionHandling.isDigit(commend[1]);
                                    accountService.save(new AccountHibernate(
                                                    customer.getId(),
                                                    Long.valueOf(commend[2])
                                            )
                                    );
                                } catch (DigitException digitException) {
                                    System.out.println("enter the number!");
                                }  catch (Exception e){
                                    System.out.println("wrong input!");
                                }
                                break;
                            case "setPassword":
                                try {
                                    ExceptionHandling.isDigit(commend[1]);
                                    creditCardService.editPassword(
                                            Integer.valueOf(commend[1])
                                            , commend[2]
                                    );
                                } catch (NullException nullException) {
                                    System.out.println("credit card not found!");
                                } catch (DigitException digitException) {
                                    System.out.println("enter the number!");
                                }  catch (Exception e){
                                    System.out.println("wrong input!");
                                }
                                break;
                            case "deposit":
                                try {
                                    ExceptionHandling.isDigit(commend[1]);
                                    ExceptionHandling.isDigit(commend[2]);
                                    transactionService.deposit(
                                            Integer.valueOf(commend[1]),
                                            Long.valueOf(commend[2]),
                                            TransactionType.DEPOSIT
                                    );
                                } catch (NullException nullException) {
                                    System.out.println("account not found!");
                                } catch (DigitException digitException) {
                                    System.out.println("incorrect account id!");
                                } catch (MoneyException moneyException) {
                                    System.out.println("enter the correct money!");
                                }  catch (Exception e){
                                    System.out.println("wrong input!");
                                }
                                break;
                            case "withdrew":
                                try {
                                    ExceptionHandling.isDigit(commend[1]);
                                    ExceptionHandling.isDigit(commend[2]);
                                    transactionService.withdraw(
                                            Integer.valueOf(commend[1]),
                                            Long.valueOf(commend[2]),
                                            TransactionType.WITHDRAW
                                    );
                                } catch (NullException nullException) {
                                    System.out.println("account not found!");
                                } catch (DigitException digitException) {
                                    System.out.println("incorrect account id!");
                                } catch (MoneyException moneyException) {
                                    System.out.println("enter the correct money!");
                                }  catch (Exception e){
                                    System.out.println("wrong input!");
                                }
                                break;
                            case "cardToCard":
                                try{
                                    ExceptionHandling.isCardNumber(commend[1]);
                                    ExceptionHandling.isCardNumber(commend[2]);
                                    ExceptionHandling.isCvv2(commend[3]);
                                    ExceptionHandling.isMoney(commend[4]);
                                    transactionService.cardToCard(
                                            commend[1],
                                            commend[2],
                                            commend[3],
                                            Long.valueOf(commend[4])
                                    );
                                }catch (CardNumberException cardNumberException){
                                    System.out.println("incorrect card number!");
                                }catch (Cvv2Exception cvv2Exception){
                                    System.out.println("incorrect cvv2");
                                }catch (MoneyException moneyException){
                                    System.out.println("incorrect money!");
                                }  catch (Exception e){
                                    System.out.println("wrong input!");
                                }
                                break;
                            case "help":
                                showCustomerMenu();
                                break;
                            case "logout":
                                state = false;
                                showMenu();
                                break;
                            default:
                                System.out.println("wrong Number");
                                break;
                        }
                        break;
                }
            }
        }
    }


    public static void showMenu() {
        System.out.println("addBank bankName");
        System.out.println("showBankBranch");
        System.out.println("customerCreatAccount bankBranchID name nationalCode phoneNumber");
        System.out.println("bankLogin bankID");
        System.out.println("bankBranchLogin bankBranchID");
        System.out.println("customerLogin nationalCode branchId");

    }

    public static void showBankMenu() {
        System.out.println("showProfile");
        System.out.println("showBranch");
        System.out.println("addBranch managerName ManagerNationalCode bankBranchAddress");
        System.out.println("help");
        System.out.println("logout");
    }

    public static void showBranchMenu() {
        System.out.println("showProfile");
        System.out.println("showEmployee");
        System.out.println("showCustomer");
        System.out.println("showAccount");
        System.out.println("showBlockAccount");
        System.out.println("activatedAccount blockId");
        System.out.println("addEmployee name nationalCode phone");
        System.out.println("help");
        System.out.println("logout");
    }

    public static void showCustomerMenu() {
        System.out.println("showProfile");
        System.out.println("showBankBranch");
        System.out.println("showAccount");
        System.out.println("showCreditCard");
        System.out.println("showTransaction accountId date");
        System.out.println("createBankAccount customerId balance");
        System.out.println("setPassword accountId newPassword");
        System.out.println("deposit accountId amount");
        System.out.println("withdrew accountId amount");
        System.out.println("cardToCard originCardNumber destinationCardNumber cvv2 amount");
        System.out.println("help");
        System.out.println("logout");
    }


}
