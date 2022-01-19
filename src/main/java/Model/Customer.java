package Model;

public class Customer {
    private Integer id;
    private BankBranch bankBranch;
    private String name;
    private String nationalCode;
    private String phone;

    public Customer() {
    }

    public Customer(BankBranch bankBranch, String name,
                    String nationalCode, String phone) {
        this.bankBranch = bankBranch;
        this.name = name;
        this.nationalCode = nationalCode;
        this.phone = phone;
    }

    public Customer(Integer id, BankBranch bankBranch, String name,
                    String nationalCode, String phone) {
        this.id = id;
        this.bankBranch = bankBranch;
        this.name = name;
        this.nationalCode = nationalCode;
        this.phone = phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", bankBranch=" + bankBranch.getBank().getName() +
                ", name='" + name + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
