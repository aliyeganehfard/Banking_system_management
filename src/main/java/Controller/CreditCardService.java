package Controller;

import Model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

public class CreditCardService {
    CreditCardRepository creditCardRepository = new CreditCardRepository();
    Random random = new Random();
    public void insert(Account account){
        creditCardRepository.insert(
                new CreditCard(
                        account,
                        getCardNumber(),
                        getCvv2(),
                        Date.valueOf(LocalDate.now()),
                        "")
        );

    }

    public void editPassword(Integer id , String password){
        CreditCard creditCard = creditCardRepository.findById(id);
        if (creditCard == null){
            System.out.println("not found credit card !");
            return;
        }
        creditCardRepository.update(new CreditCard(id,password));
    }

    private String getCvv2(){
        return String.valueOf(random.nextInt(9999-1000)+1000);
    }

    private String getCardNumber(){
        StringBuilder cardNumber= new StringBuilder();
        for (int i = 0; i < 3 ; i++) {
            cardNumber.append(getCvv2());
        }
        return cardNumber.toString();
    }
}
