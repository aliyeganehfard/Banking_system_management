package Model;

import java.sql.Date;

public class Transaction {
    private Integer id;
    private CreditCard creditCard;
    private Long amount;
    private TransactionType type;
    private Date date;

    public Transaction() {
    }

    public Transaction(CreditCard creditCard, Long amount, TransactionType type, Date date) {
        this.creditCard = creditCard;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Transaction(Integer id, CreditCard creditCard, Long amount, TransactionType type, Date date) {
        this.id = id;
        this.creditCard = creditCard;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", creditCard=" + creditCard +
                ", amount=" + amount +
                ", type=" + type +
                ", date=" + date +
                '}';
    }
}
