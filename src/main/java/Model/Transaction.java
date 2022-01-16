package Model;

public class Transaction {
    private Integer id;
    private CreditCard originCreditCard;
    private CreditCard destinationCreditCard;
    private Long amount;
    private TransactionType type;

    public Transaction() {
    }

    public Transaction(CreditCard originCreditCard, CreditCard destinationCreditCard,
                       Long amount, TransactionType type) {
        this.originCreditCard = originCreditCard;
        this.destinationCreditCard = destinationCreditCard;
        this.amount = amount;
        this.type = type;
    }

    public Transaction(Integer id, CreditCard originCreditCard, CreditCard destinationCreditCard,
                       Long amount, TransactionType type) {
        this.id = id;
        this.originCreditCard = originCreditCard;
        this.destinationCreditCard = destinationCreditCard;
        this.amount = amount;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CreditCard getOriginCreditCard() {
        return originCreditCard;
    }

    public void setOriginCreditCard(CreditCard originCreditCard) {
        this.originCreditCard = originCreditCard;
    }

    public CreditCard getDestinationCreditCard() {
        return destinationCreditCard;
    }

    public void setDestinationCreditCard(CreditCard destinationCreditCard) {
        this.destinationCreditCard = destinationCreditCard;
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
                ", originCreditCard=" + originCreditCard +
                ", destinationCreditCard=" + destinationCreditCard +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
