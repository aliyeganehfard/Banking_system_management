package Model;

public class BlockAccount {
    private Integer id;
    private BankBranch bankBranch;
    private Account account;

    public BlockAccount() {
    }

    public BlockAccount(BankBranch bankBranch, Account account) {
        this.bankBranch = bankBranch;
        this.account = account;
    }

    public BlockAccount(Integer id, BankBranch bankBranch, Account account) {
        this.id = id;
        this.bankBranch = bankBranch;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BankBranch getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(BankBranch bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "BlockAccount{" +
                "id=" + id +
                ", bankBranch=" + bankBranch +
                ", account=" + account +
                '}';
    }
}
