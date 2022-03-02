package Model.entity;

public class BankBranch {
    private Integer id;
    private Bank bank;
    private String managerName;
    private String managerNationalCode;
    private String address;

    public BankBranch() {
    }

    public BankBranch(Bank bank, String managerName,
                      String managerNationalCode, String address) {
        this.bank = bank;
        this.managerName = managerName;
        this.managerNationalCode = managerNationalCode;
        this.address = address;
    }

    public BankBranch(Integer id, Bank bank, String managerName,
                      String managerNationalCode, String address) {
        this.id = id;
        this.bank = bank;
        this.managerName = managerName;
        this.managerNationalCode = managerNationalCode;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerNationalCode() {
        return managerNationalCode;
    }

    public void setManagerNationalCode(String managerNationalCode) {
        this.managerNationalCode = managerNationalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BankBranch{" +
                "id=" + id +
                ", bank=" + bank.getName() +
                ", managerName='" + managerName + '\'' +
                ", managerNationalCode='" + managerNationalCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
