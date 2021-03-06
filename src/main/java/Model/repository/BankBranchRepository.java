package Model.repository;

import Exceptions.NullException;
import Model.entity.Account;
import Model.entity.Customer;
import Model.util.PostgresConnection;
import Model.entity.Bank;
import Model.entity.BankBranch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BankBranchRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public BankBranchRepository() {
        try {
            query="create table if not exists BSM_bank_branch " +
                    "( " +
                    "    id                    serial primary key, " +
                    "    bank_id               Integer, " +
                    "    manager_name          varchar(100), " +
                    "    manager_national_code char(10),  " +
                    "    bank_address          varchar(200), " +
                    "    constraint BSM_B_ID foreign key (bank_id) references BSM_bank (id) " +
                    ")";
            connection.prepareStatement(query).execute();
        }catch (Exception e){
//            System.out.println(e);
        }
    }

    public Integer insert(BankBranch bankBranch) {
        Integer id = null;
        try {
            query = "insert into BSM_bank_branch " +
                    "(bank_id, manager_name, manager_national_code, bank_address) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bankBranch.getBank().getId());
            preparedStatement.setString(2, bankBranch.getManagerName());
            preparedStatement.setString(3, bankBranch.getManagerNationalCode());
            preparedStatement.setString(4, bankBranch.getAddress());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        return id;
    }

    public BankBranch findById(Integer id) {
        BankBranch bankBranch = null;
        try {
            query = "select * from BSM_bank_branch " +
                    "inner join BSM_bank Bb on Bb.id = BSM_bank_branch.bank_id " +
                    "where BSM_bank_branch.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bankBranch = new BankBranch(
                        resultSet.getInt("id"),
                        new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                        resultSet.getString("manager_name"),
                        resultSet.getString("manager_national_code"),
                        resultSet.getString("bank_address")
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }

        if (bankBranch == null){
            throw new NullException();
        }

        return bankBranch;
    }



    //    show all bank branch
    public List<BankBranch> findAll() {
        List<BankBranch> bankBranches = new ArrayList<BankBranch>();
        try {
            query = "select * from BSM_bank_branch " +
                    "inner join BSM_bank Bb on Bb.id = BSM_bank_branch.bank_id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bankBranches.add(new BankBranch(
                                resultSet.getInt("id"),
                                new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                                resultSet.getString("manager_name"),
                                resultSet.getString("manager_national_code"),
                                resultSet.getString("bank_address")
                        )
                );
            }
        } catch (Exception e) {
//            System.out.println(e);
        }
        return bankBranches;
    }

    public List<Account> findAll(BankBranch bankBranch) {
        List<Account> accounts = new ArrayList<Account>();
        try {
            query = "select * from BSM_account Ba " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id where Bbb.id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,bankBranch.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(new Account(
                                resultSet.getInt("id"),
                                new Customer(
                                        resultSet.getInt("customer_id"),
                                        new BankBranch(resultSet.getInt("bank_branch_id"),
                                                new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                                                resultSet.getString("manager_name"),
                                                resultSet.getString("manager_national_code"),
                                                resultSet.getString("bank_address")
                                        ),
                                        resultSet.getString("customer_name"),
                                        resultSet.getString("customer_national_code"),
                                        resultSet.getString("customer_phone")
                                ),
                                resultSet.getLong("balance")
                        )
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println("account find all problem");
        }
        return accounts;
    }

    //    show all customer Account
    public List<Account> findAll(Customer customer) {
        List<Account> accounts = new ArrayList<Account>();
        try {
            query = "select * from BSM_account Ba " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id where Bc.id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(new Account(
                                resultSet.getInt("id"),
                                new Customer(
                                        resultSet.getInt("customer_id"),
                                        new BankBranch(resultSet.getInt("bank_branch_id"),
                                                new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                                                resultSet.getString("manager_name"),
                                                resultSet.getString("manager_national_code"),
                                                resultSet.getString("bank_address")
                                        ),
                                        resultSet.getString("customer_name"),
                                        resultSet.getString("customer_national_code"),
                                        resultSet.getString("customer_phone")
                                ),
                                resultSet.getLong("balance")
                        )
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//        System.out.println("account find all problem");
        }
        return accounts;
    }

//    show all customer of bank
//    show all account of bank
//    show all transaction

}
