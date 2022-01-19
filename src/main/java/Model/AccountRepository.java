package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public AccountRepository() {
        try {
            query="create table if not exists BSM_account " +
                    "( " +
                    "    id          serial primary key, " +
                    "    customer_id Integer, " +
                    "    balance     bigint, " +
                    "    constraint BSM_C_ID foreign key (customer_id) references BSM_customer (id) " +
                    ")";
            connection.prepareStatement(query).execute();
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public Integer insert(Account account) {
        Integer id = null;
        try {
            query = "insert into BSM_account (customer_id, balance) VALUES (?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, account.getCustomer().getId());
            preparedStatement.setLong(2, account.getBalance());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    public void update(Account account) {
        try {
            query = "update BSM_account " +
                    "set balance = ? " +
                    "where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, account.getBalance());
            preparedStatement.setInt(2, account.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("heraljk");
        }
    }

    public void delete(Account account) {
        try {
            query = "delete from BSM_account " +
                    "where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, account.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //show customer accounts
    public Account findById(Integer id) {
        Account account = new Account();
        try {
            query = "select * from BSM_account Ba " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where Ba.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = new Account(
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
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e+"find by id");
        }
        return account;
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
            System.out.println(e);
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
        System.out.println(e);
    }
    return accounts;
}


}
