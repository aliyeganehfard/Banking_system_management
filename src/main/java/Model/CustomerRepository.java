package Model;

import Exceptions.NullException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public CustomerRepository() {
        try {
            query="create table if not exists BSM_customer" +
                    "(" +
                    "    id                     serial primary key, " +
                    "    bank_branch_id         Integer, " +
                    "    customer_name          varchar(100), " +
                    "    customer_national_code char(10), " +
                    "    customer_phone         char(11), " +
                    "    constraint BSM_BB_C_ID foreign key (bank_branch_id) references BSM_bank_branch (id) " +
                    ")";
            connection.prepareStatement(query).execute();
        }catch (Exception e){
//            System.out.println(e);
        }
    }

    public Integer insert(Customer customer) {
        Integer id = null;
        try {
            query = "insert into BSM_customer (bank_branch_id, customer_name, customer_national_code, customer_phone) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, customer.getBankBranch().getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getNationalCode());
            preparedStatement.setString(4, customer.getPhone());
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

    public void delete(Customer customer) {
        try {
            query = "delete from BSM_customer " +
                    "where id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
    }

    public Customer findById(String id) {
        Customer customer = null;
        try {
            query = "select * from BSM_customer Bc " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where Bc.customer_national_code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer = new Customer(
                                resultSet.getInt("id"),
                                new BankBranch(resultSet.getInt("bank_branch_id"),
                                        new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                                        resultSet.getString("manager_name"),
                                        resultSet.getString("manager_national_code"),
                                        resultSet.getString("bank_address")
                                ),
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_national_code"),
                                resultSet.getString("customer_phone")

                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        return customer;
    }

    public Customer findById(String nationalCode , Integer branchId) {
        Customer customer = null;
        try {
            query = "select * from BSM_customer Bc " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where Bc.customer_national_code = ? and Bc.bank_branch_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nationalCode);
            preparedStatement.setInt(2, branchId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer = new Customer(
                        resultSet.getInt("id"),
                        new BankBranch(resultSet.getInt("bank_branch_id"),
                                new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                                resultSet.getString("manager_name"),
                                resultSet.getString("manager_national_code"),
                                resultSet.getString("bank_address")
                        ),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_national_code"),
                        resultSet.getString("customer_phone")

                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        if (customer == null)
            throw new NullException();

        return customer;
    }

    //    show all customer of bank
    public List<Customer> findAll(BankBranch bankBranch) {
        List<Customer> customers = new ArrayList<Customer>();
        try {
            query = "select * from BSM_customer " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = BSM_customer.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where bank_branch_id=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bankBranch.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getInt("id"),
                                new BankBranch(resultSet.getInt("bank_branch_id"),
                                        new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                                        resultSet.getString("manager_name"),
                                        resultSet.getString("manager_national_code"),
                                        resultSet.getString("bank_address")
                                ),
                                resultSet.getString("customer_name"),
                                resultSet.getString("customer_national_code"),
                                resultSet.getString("customer_phone")
                        )
                );
            }
        } catch (Exception e) {
//            System.out.println(e);
        }
        return customers;
    }

    //    show all account
    public List<Account> findAccount(Integer id) {
        List<Account> accounts = new ArrayList<Account>();
        try {
            query = "select * from BSM_customer Bc " +
                    "    inner join BSM_account Ba on Bc.id = Ba.customer_id " +
                    "where Bc.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(
                        new Account(
                                resultSet.getInt("account_id"),
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
//            System.out.println(e);
        }
        return accounts;
    }
//    show all log

}
