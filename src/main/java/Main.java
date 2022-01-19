import Controller.*;
import Model.*;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
        var accountService = new AccountService();
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
                    bankService.insert(new Bank(commend[1]));
                    break;
                case "showBankBranch":
                    List<BankBranch> bankBranches = bankBranchService.findAll();
                    for (BankBranch branch : bankBranches) {
                        System.out.println(branch);
                    }

                    break;
                case "customerCreatAccount":
                    customerService.insert(
                            new Customer(
                                    bankBranchService.findById(Integer.valueOf(commend[1])),
                                    commend[2],
                                    commend[3],
                                    commend[4]
                            )
                    );
                    break;
                case "bankLogin":
                    bank = bankService.findById(Integer.valueOf(commend[1]));
                    if (bank != null) {
                        state = true;
                        showBankMenu();
                        bankId = bank.getId();
                        type = 1;
                    }
                    break;
                case "bankBranchLogin":
                    bankBranch = bankBranchService.findById(Integer.valueOf(commend[1]));
                    if (bankBranch != null) {
                        state = true;
                        showBranchMenu();
                        bankBranchId = bankBranch.getId();
                        type = 2;
                    }
                    break;
                case "customerLogin":
                    customer = customerService.findByID(commend[1], Integer.valueOf(commend[2]));
                    if (customer != null) {
                        state = true;
                        showCustomerMenu();
                        type = 3;
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
                                List<BankBranch> bankBranches =bankService.findAllBranch(bank);
                                for (BankBranch branch : bankBranches) {
                                    System.out.println(branch);
                                }
                                break;
                            case "addBranch":
                                bankBranchService.insert(new BankBranch(
                                                bankService.findById(bankId),
                                                commend[1],
                                                commend[2],
                                                commend[3]
                                        )
                                );
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
                                List<BankEmployee> bankEmployees =bankBranchService.findAllEmployee(bankBranch);
                                for (BankEmployee employee : bankEmployees) {
                                    System.out.println(employee);
                                }
                                break;
                            case "showCustomer":
                                List<Customer> customers =bankBranchService.findAllCustomer(bankBranch);
                                for (Customer ctmr: customers) {
                                    System.out.println(ctmr);
                                }
                                break;
                            case "showAccount":
                                List<Account> accounts =bankBranchService.findAllAccount(bankBranch);
                                for (Account acnt :accounts) {
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
                                blockAccountService.delete(Integer.valueOf(commend[1]));
                                break;
                            case "addEmployee":
                                bankEmployeeService.insert(new BankEmployee(
                                                bankBranchService.findById(bankBranchId),
                                                commend[1],
                                                commend[2],
                                                commend[3]
                                        )
                                );
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
                                List<BankBranch> bankBranches =bankBranchService.findAll();
                                for (BankBranch bbrnch : bankBranches) {
                                    System.out.println(bbrnch);
                                }
                                break;
                            case "showAccount":
                                List<Account> accountList =customerService.findAllAccount(customer);
                                for (Account account : accountList) {
                                    System.out.println(account);
                                }
                                break;
                            case "showCreditCard":
                                List<CreditCard> creditCardList =customerService.findAllCreditCard(customer);
                                for (CreditCard creditCard : creditCardList) {
                                    System.out.println(creditCard);
                                }
                                break;
                            case "showTransaction":
                                    transactionService.printTransaction(Integer.valueOf(commend[1]),Date.valueOf(commend[2]));
                                break;
                            case "createBankAccount":
                                accountService.insert(new Account(
                                                customer,
                                                Long.valueOf(commend[2])
                                        )
                                );
                                break;
                            case "setPassword":
                                creditCardService.editPassword(Integer.valueOf(commend[1]),commend[2]);
                                break;
                            case "deposit":
                                transactionService.deposit(Integer.valueOf(commend[1]), Long.valueOf(commend[2]) , TransactionType.DEPOSIT);
                                break;
                            case "withdrew":
                                transactionService.withdraw(Integer.valueOf(commend[1]), Long.valueOf(commend[2]) , TransactionType.WITHDRAW);
                                break;
                            case "cardToCard":
                                transactionService.cardToCard(commend[1],commend[2],commend[3],Long.valueOf(commend[4]));
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
