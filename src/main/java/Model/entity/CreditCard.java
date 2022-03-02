package Model.entity;

import java.sql.Date;

public class CreditCard {
    private Integer id;
    private Account account;
    private String cardNumber;
    private String cvv2;
    private Date expirationDate;
    private String password;

    public CreditCard() {
    }

    public CreditCard(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public CreditCard(Account account, String cardNumber, String cvv2,
                      Date expirationDate, String password) {
        this.account = account;
        this.cardNumber = cardNumber;
        this.cvv2 = cvv2;
        this.expirationDate = expirationDate;
        this.password = password;
    }

    public CreditCard(Integer id, Account account, String cardNumber,
                      String cvv2, Date expirationDate, String password) {
        this.id = id;
        this.account = account;
        this.cardNumber = cardNumber;
        this.cvv2 = cvv2;
        this.expirationDate = expirationDate;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", account id=" + account.getId() +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv2='" + cvv2 + '\'' +
                ", expirationDate=" + expirationDate +
                ", password='" + password + '\'' +
                '}';
    }
}
