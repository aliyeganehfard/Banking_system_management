package Model;

import Exceptions.NullException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BankRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public BankRepository() {
        try {
            query="create table if not exists BSM_bank " +
                    "( " +
                    "    id serial primary key, " +
                    "    bank_name varchar(100) " +
                    ")";
            connection.prepareStatement(query).execute();
        }catch (Exception e){
//            System.out.println(e);
        }
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
//            System.out.println(e);
        }
        return id;
    }

    public List<Bank> findAll() {
        List<Bank> banks = new ArrayList<Bank>();
        try {
            query = "select * from BSM_bank " +
                    "where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                banks.add(new Bank(
                                resultSet.getInt("id"),
                                resultSet.getString("bank_name")
                        )
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        return banks;
    }

    public Bank findById(Integer id) {
        Bank bank = null;
        try {
            query = "select * from BSM_bank " +
                    "where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bank = new Bank(
                        resultSet.getInt("id"),
                        resultSet.getString("bank_name")
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        if (bank == null)
            throw new NullException();
        return bank;
    }

    //    show all branch of bank
    public List<BankBranch> findAllBranch(Bank bank) {
        List<BankBranch> bankBranches = new ArrayList<BankBranch>();
        try {
            query = "select * from BSM_bank " +
                    "inner join BSM_bank_branch Bbb on BSM_bank.id = Bbb.bank_id " +
                    "where BSM_bank.id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bank.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bankBranches.add(
                        new BankBranch(
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
//            System.out.println("hi");
        }
        return bankBranches;
    }

}
