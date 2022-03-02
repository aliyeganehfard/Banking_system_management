package Model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer CustomerId;

    @Column
    private Long balance;


    public AccountHibernate(Integer customerId, Long balance) {
        CustomerId = customerId;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountHibernate{" +
                "id=" + id +
                ", CustomerId=" + CustomerId +
                ", balance=" + balance +
                '}';
    }
}
