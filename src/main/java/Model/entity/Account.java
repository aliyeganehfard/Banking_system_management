package Model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Account {
    private Integer id;
    private Customer customer;
    private Long balance;

    public Account(Customer customer, Long balance) {
        this.customer = customer;
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
