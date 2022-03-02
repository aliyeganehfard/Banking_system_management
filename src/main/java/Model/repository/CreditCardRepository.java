package Model.repository;

import Exceptions.NullException;
import Model.util.PostgresConnection;
import Model.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CreditCardRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public CreditCardRepository() {
        try {
            query="create table if not exists BSM_credit_card" +
                    "( " +
                    "    id              serial primary key, " +
                    "    account_id      Integer, " +
                    "    card_number     char(16), " +
                    "    cvv2            char(4), " +
                    "    expiration_date date, " +
                    "    password        varchar(50),  " +
                    "    constraint BSM_A_ID foreign key (account_id) references BSM_account (id) " +
                    ")";
            connection.prepareStatement(query).execute();
        }catch (Exception e){
//            System.out.println(e);
        }
    }

    public void insert(CreditCard creditCard) {

        try {
            query = "insert into BSM_credit_card " +
                    "(account_id, card_number, cvv2, expiration_date, password) " +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, creditCard.getAccount().getId());
            preparedStatement.setString(2, creditCard.getCardNumber());
            preparedStatement.setString(3, creditCard.getCvv2());
            preparedStatement.setDate(4, creditCard.getExpirationDate());
            preparedStatement.setString(5, creditCard.getPassword());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }

    }

    public void update(CreditCard creditCard) {
        try {
            query = "update BSM_credit_card " +
                    "set password = ? " +
                    "where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, creditCard.getPassword());
            preparedStatement.setInt(2, creditCard.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
    }


    public void delete(CreditCard creditCard) {
        try {
            query = "delete from BSM_credit_card " +
                    "where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, creditCard.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //show customer credit card
    public CreditCard findById(Integer accountId) {
        CreditCard creditCard = null;
        try {
            query = "select * from BSM_credit_card Bcc " +
                    "inner join BSM_account Ba on Ba.id = Bcc.account_id " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where Ba.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                creditCard = new CreditCard(
                        resultSet.getInt("id"),
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
                        ),
                        resultSet.getString("card_number"),
                        resultSet.getString("cvv2"),
                        resultSet.getDate("expiration_date"),
                        resultSet.getString("password")
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e + "customer credit card");
        }
        if (creditCard == null)
            throw new NullException();
        return creditCard;
    }

    //show all customer credit card
    public List<CreditCard> findById(Customer customer) {
        List<CreditCard> creditCards = new ArrayList<CreditCard>();
        try {
            query = "select * from BSM_credit_card Bcc " +
                    "inner join BSM_account Ba on Ba.id = Bcc.account_id " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where Bc.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                creditCards.add(new CreditCard(
                                resultSet.getInt("id"),
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
                                ),
                                resultSet.getString("card_number"),
                                resultSet.getString("cvv2"),
                                resultSet.getDate("expiration_date"),
                                resultSet.getString("password")
                        )
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        return creditCards;
    }


    //    show account of credit card
    public CreditCard findByCardNumber(String cardNumber) {
        CreditCard creditCard = null;
        try {
            query = "select * from BSM_credit_card Bcc " +
                    "inner join BSM_account Ba on Ba.id = Bcc.account_id " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where Bcc.card_number=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                creditCard = new CreditCard(
                        resultSet.getInt("id"),
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
                        ),
                        resultSet.getString("card_number"),
                        resultSet.getString("cvv2"),
                        resultSet.getDate("expiration_date"),
                        resultSet.getString("password")
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e+"find card");
        }
        return creditCard;
    }

}
