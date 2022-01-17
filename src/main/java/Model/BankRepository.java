package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BankRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public BankRepository() {
    }

    public Integer insert(Bank bank) {
        Integer id = null;
        try {
            query = "insert into BSM_bank (bank_name) values (?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, bank.getName());
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

    public Bank findById(Integer id) {
        Bank bank = null;
        try {
            query = "select * from BSM_bank " +
                    "where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                bank = new Bank(
                        resultSet.getInt("id"),
                        resultSet.getString("bank_name")
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return bank;
    }



}
