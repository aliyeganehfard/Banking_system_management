package Model.repository;

import Model.util.PostgresConnection;
import Model.util.TransactionType;
import Model.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public TransactionRepository() {
        try {
            query="create table if not exists BSM_transaction" +
                    "( " +
                    "    id                 serial primary key, " +
                    "    account_id         Integer, " +
                    "    credit_card_number char(16), " +
                    "    amount             bigint, " +
                    "    operation_type     varchar(100), " +
                    "    operation_date     date, " +
                    "    constraint BSM_T_A_ID foreign key (account_id) references BSM_account (id) " +
                    ")";
            connection.prepareStatement(query).execute();
        }catch (Exception e){
//            System.out.println(e);
        }
    }

    public void insert(Transaction transaction) {
        try {
            query = "insert into BSM_transaction (account_id, credit_card_number, amount, operation_type,operation_date) " +
                    " VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, transaction.getAccount().getId());
            preparedStatement.setString(2, transaction.getCreditCard().getCardNumber());
            preparedStatement.setLong(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getType().name());
            preparedStatement.setDate(5, transaction.getDate());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e +" transaction");
        }

    }

    public void update(Transaction transaction) {
        try {
            query = "update BSM_transaction " +
                    "set amount=? and operation_type=? " +
                    "where credit_card_number=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, transaction.getAmount());
            preparedStatement.setString(2, transaction.getType().name());
            preparedStatement.setString(2, transaction.getCreditCard().getCardNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
    }

    public void delete(Transaction transaction) {
        try {
            query = "delete from BSM_transaction " +
                    "where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, transaction.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
    }

    //show transaction of account
    public List<Transaction> findById(Integer cardNumber, Date date) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            query = "select * from BSM_transaction Bt " +
                    "inner join BSM_credit_card Bcc on Bcc.card_number = Bt.credit_card_number " +
                    "inner join BSM_account Ba on Ba.id = Bcc.account_id " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where Bt.account_id=? and Bt.operation_date > ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cardNumber);
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = new Account(
                        resultSet.getInt("id"),
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
                        ),
                        resultSet.getLong("balance")
                );

                transactions.add(
                        new Transaction(
                                resultSet.getInt("id"),
                                account,
                                new CreditCard(
                                        resultSet.getInt("id"),
                                        account,
                                        resultSet.getString("card_number"),
                                        resultSet.getString("cvv2"),
                                        resultSet.getDate("expiration_date"),
                                        resultSet.getString("password")
                                ),
                                resultSet.getLong("amount"),
                                TransactionType.valueOf(resultSet.getString("operation_type")),
                                resultSet.getDate("operation_date")
                        )
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        return transactions;
    }
}
