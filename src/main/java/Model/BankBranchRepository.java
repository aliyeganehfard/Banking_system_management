package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class BankBranchRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public BankBranchRepository() {
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
            System.out.println(e);
        }
        return id;
    }

    public BankBranch findById(Integer id) {
        BankBranch bankBranch = null;
        try {
            query = "select * from BSM_bank_branch " +
                    "inner join BSM_bank Bb on Bb.id = BSM_bank_branch.bank_id " +
                    "where id = ?";
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
            System.out.println(e);
        }
        return bankBranch;
    }

    //    show all branch of bank
    public List<BankBranch> findAll(BankBranch bankBranch) {
        List<BankBranch> bankBranches = null;
        try {
            query = "select * from BSM_bank_branch " +
                    "where bank_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bankBranch.getBank().getId());
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
            System.out.println(e);
        }
        return bankBranches;
    }
//    show all customer of bank
//    show all account of bank
//    show all transaction

}
