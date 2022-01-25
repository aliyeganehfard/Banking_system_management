package Controller;

import Exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExceptionHandling {

    public static void isWord(String word) {
        for (char ch : word.toLowerCase().toCharArray())
            if (!(ch >= 97 && ch <= 122))
                throw new WordException();
    }

    public static void isDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(date);
            sdf.setLenient(false);
        } catch (ParseException e) {
            throw new DateException();
        }
    }

    public static void isDigit(String digit) {
        for (char chr : digit.toCharArray())
            if (!Character.isDigit(chr))
                throw new DigitException();
    }

    public static void isDigit(Integer digit) {
        isDigit(String.valueOf(digit));
    }

    public static void isNationalCode(String nationalCode) {
        if (nationalCode.length() != 10)
            throw new NationalCodeException();

        for (char chr : nationalCode.toCharArray())
            if (!Character.isDigit(chr))
                throw new NationalCodeException();
    }

    public static void isPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11)
            throw new PhoneNumberException();

        if (phoneNumber.charAt(0) != '0' || phoneNumber.charAt(1) != '9')
            throw new PhoneNumberException();
    }

    public static void isMoney(String money) {
        for (char chr : money.toCharArray()) {
            if (!Character.isDigit(chr))
                throw new MoneyException();
        }
    }

    public static void isCardNumber(String cardNumber) {
        if (cardNumber.length() != 12)
            throw new CardNumberException();

        for (char chr : cardNumber.toCharArray()) {
            if (!Character.isDigit(chr))
                throw new CardNumberException();
        }
    }

    public static void isCvv2(String cvv2){
        if (!(cvv2.length()>=3 && cvv2.length()<=4))
            throw new Cvv2Exception();
    }
}
