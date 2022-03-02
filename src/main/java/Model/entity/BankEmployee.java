package Model.entity;

public class BankEmployee {
    private Integer id;
    private BankBranch bankBranch;
    private String name;
    private String nationalCode;
    private String phone;

    public BankEmployee() {
    }

    public BankEmployee(BankBranch bankBranch, String name,
                        String nationalCode, String phone) {
        this.bankBranch = bankBranch;
        this.name = name;
        this.nationalCode = nationalCode;
        this.phone = phone;
    }

    public BankEmployee(Integer id, BankBranch bankBranch,
                        String name, String nationalCode, String phone) {
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
        return "BankEmployee{" +
                "id=" + id +
                ", bankBranch=" + bankBranch.getManagerName() +
                ", name='" + name + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
