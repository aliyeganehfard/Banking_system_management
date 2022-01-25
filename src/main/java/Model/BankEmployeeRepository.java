package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BankEmployeeRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public BankEmployeeRepository() {
        try {
            query="create table if not exists BSM_bank_employee " +
                    "( " +
                    "    id                     serial primary key, " +
                    "    bank_branch_id         Integer, " +
                    "    employee_name          varchar(100), " +
                    "    employee_national_code char(10), " +
                    "    employee_phone         char(11), " +
                    "    constraint BSM_BB_ID foreign key (bank_branch_id) references BSM_bank_branch (id) " +
                    ")";
            connection.prepareStatement(query).execute();
        }catch (Exception e){
//            System.out.println(e);
        }
    }

    public Integer insert(BankEmployee bankEmployee) {
        Integer id = null;
        try {
            query = "insert into BSM_bank_employee (bank_branch_id, employee_name, employee_national_code,employee_phone) " +
                    "VALUES (?,?,?,?);";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bankEmployee.getBankBranch().getId());
            preparedStatement.setString(2, bankEmployee.getName());
            preparedStatement.setString(3, bankEmployee.getNationalCode());
            preparedStatement.setString(4, bankEmployee.getPhone());
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

    public void delete(BankEmployee bankEmployee) {
        try {
            query = "delete from BSM_bank_employee " +
                    "where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bankEmployee.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
    }

    public BankEmployee findById(Integer id) {
        BankEmployee bankEmployee = null;
        try {
            query = "select * from BSM_bank_employee BE " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = BE.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bankEmployee = new BankEmployee(
                        resultSet.getInt("id"),
                        new BankBranch(resultSet.getInt("bank_branch_id"),
                                new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                                resultSet.getString("manager_name"),
                                resultSet.getString("manager_national_code"),
                                resultSet.getString("bank_address")
                        ),
                        resultSet.getString("employee_name"),
                        resultSet.getString("employee_national_code"),
                        resultSet.getString("employee_phone")
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        return bankEmployee;
    }

    //    show all bank branch employee
    public List<BankEmployee> findAll(BankBranch bankBranch) {
        List<BankEmployee> bankEmployees = new ArrayList<BankEmployee>();
        try {
            query = "select * from BSM_bank_employee " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = BSM_bank_employee.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where bank_branch_id=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bankBranch.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bankEmployees.add(new BankEmployee(
                                resultSet.getInt("id"),
                                new BankBranch(resultSet.getInt("bank_branch_id"),
                                        new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                                        resultSet.getString("manager_name"),
                                        resultSet.getString("manager_national_code"),
                                        resultSet.getString("bank_address")
                                ),
                                resultSet.getString("employee_name"),
                                resultSet.getString("employee_national_code"),
                                resultSet.getString("employee_phone")
                        )
                );
            }
        } catch (Exception e) {
//            System.out.println(e);
        }
        return bankEmployees;
    }
}
