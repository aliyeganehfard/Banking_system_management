package Model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

}
