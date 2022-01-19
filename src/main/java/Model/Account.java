package Model;

public class Account {
    private Integer id;
    private Customer customer;
    private Long balance;

    public Account() {
    }

    public Account(Customer customer, Long balance) {
        this.customer = customer;
        this.balance = balance;
    }

    public Account(Integer id, Customer customer, Long balance) {
        this.id = id;
        this.customer = customer;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name =" + customer.getName() +
                ", balance=" + balance +
                '}';
    }
}
