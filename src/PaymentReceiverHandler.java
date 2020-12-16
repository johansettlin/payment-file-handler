package src;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Date;

// class to call the supplied interface for all the payments

public class PaymentReceiverHandler implements PaymentReceiver{
    @Override
    public void startPaymentBundle(String accountNumber, Date paymentDate, String currency) {
        if(paymentDate == null){
            System.out.println("Payment initiated: " + "\n" + "Account number: " + accountNumber +  "\n" + "Currency: " + currency);
        }else System.out.println("Payment initiated: " + "\n" + "Account number: " + accountNumber + "\n" + "Date: " + paymentDate.toString() + "\n" + "Currency: " + currency);
    }

    @Override
    public void payment(BigDecimal amount, String reference) {
        System.out.println("-----PAYMENT------");
        System.out.println("Amount: " + amount + "\t" + "Reference: " + reference);
    }

    @Override
    public void endPaymentBundle() {
        System.out.println("Payment completed");
    }
}
