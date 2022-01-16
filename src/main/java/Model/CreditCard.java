package Model;

import java.sql.Date;

public class CreditCard {
    private Integer id;
    private Account account;
    private String cardNumber;
    private String cvv2;
    private Date expirationDate;

    public CreditCard() {
    }

    public CreditCard(Account account, String cardNumber,
                      String cvv2, Date expirationDate) {
        this.account = account;
        this.cardNumber = cardNumber;
        this.cvv2 = cvv2;
        this.expirationDate = expirationDate;
    }

    public CreditCard(Integer id, Account account, String cardNumber, String cvv2, Date expirationDate) {
        this.id = id;
        this.account = account;
        this.cardNumber = cardNumber;
        this.cvv2 = cvv2;
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", account=" + account +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv2='" + cvv2 + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
