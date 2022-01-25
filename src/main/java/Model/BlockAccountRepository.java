package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BlockAccountRepository {
    private Connection connection = PostgresConnection.connection;
    private String query;

    public BlockAccountRepository() {
        try {
            query="create table if not exists BSM_block_Account" +
                    "( " +
                    "    id             serial primary key, " +
                    "    bank_branch_id Integer, " +
                    "    account_id     Integer, " +
                    "    constraint BSM_BB_B_ID foreign key (bank_branch_id) references BSM_bank_branch (id), " +
                    "    constraint BSM_A_B_ID foreign key (account_id) references BSM_account (id) " +
                    ")";
            connection.prepareStatement(query).execute();
        }catch (Exception e){
//            System.out.println(e);
        }
    }

    public void insert(BlockAccount blockAccount) {
        try {
            query = "insert into BSM_block_Account (bank_branch_id, account_id) VALUES (?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, blockAccount.getBankBranch().getId());
            preparedStatement.setInt(2, blockAccount.getAccount().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
    }

    public void delete(Integer blockId) {
        try {
            query = "delete from BSM_block_Account " +
                    "where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, blockId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
    }

    public BlockAccount findById(Integer bankBranchId, Integer accountId) {
        BlockAccount blockAccount = null;
        try {
            query = "select * from BSM_block_Account BbA " +
                    "inner join BSM_account Ba on Ba.id = BbA.account_id " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where BbA.bank_branch_id = ? and Bba.account_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bankBranchId);
            preparedStatement.setInt(2, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                BankBranch bankBranch = new BankBranch(resultSet.getInt("bank_branch_id"),
                        new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                        resultSet.getString("manager_name"),
                        resultSet.getString("manager_national_code"),
                        resultSet.getString("bank_address"));

                blockAccount = new BlockAccount(
                        resultSet.getInt("id"),
                        bankBranch,
                        new Account(resultSet.getInt("account_id"),
                                new Customer(resultSet.getInt("customer_id"),
                                        bankBranch,
                                        resultSet.getString("customer_name"),
                                        resultSet.getString("customer_national_code"),
                                        resultSet.getString("customer_phone")
                                ),
                                resultSet.getLong("balance"))
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        return blockAccount;
    }

    public List<BlockAccount> findAll(BankBranch bankBranch) {
        List<BlockAccount> blockAccounts = new ArrayList<BlockAccount>();
        try {
            query = "select * from BSM_block_Account BbA " +
                    "inner join BSM_account Ba on Ba.id = BbA.account_id " +
                    "inner join BSM_customer Bc on Bc.id = Ba.customer_id " +
                    "inner join BSM_bank_branch Bbb on Bbb.id = Bc.bank_branch_id " +
                    "inner join BSM_bank Bb on Bb.id = Bbb.bank_id " +
                    "where BbA.bank_branch_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bankBranch.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                BankBranch branch = new BankBranch(resultSet.getInt("bank_branch_id"),
                        new Bank(resultSet.getInt("bank_id"), resultSet.getString("bank_name")),
                        resultSet.getString("manager_name"),
                        resultSet.getString("manager_national_code"),
                        resultSet.getString("bank_address"));

                blockAccounts.add(new BlockAccount(
                                resultSet.getInt("id"),
                                branch,
                                new Account(resultSet.getInt("account_id"),
                                        new Customer(resultSet.getInt("customer_id"),
                                                branch,
                                                resultSet.getString("customer_name"),
                                                resultSet.getString("customer_national_code"),
                                                resultSet.getString("customer_phone")
                                        ),
                                        resultSet.getLong("balance"))
                        )
                );
            }
            preparedStatement.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
        return blockAccounts;
    }


}
