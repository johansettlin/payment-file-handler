package src;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* super class to all payment files*/
abstract class Payments {
    // might want to move out because it would probably be used by different types of files a swell
     class Section {
        int startIndex;
        int endIndex;

        Section(int startIndex, int endIndex){
            this.startIndex = startIndex - 1;
            this.endIndex = endIndex;
        }
    }

    class PaymentBundle {
        String accountNumber;
        Date paymentDate;
        String currency;

        PaymentBundle(String accountNumber, Date paymentDate, String currency){
            this.accountNumber = accountNumber;
            this.paymentDate = paymentDate;
            this.currency = currency;
        }
    }

    class Payment {
        BigDecimal amount;
        String ref;

        Payment(BigDecimal amount, String ref) {
            this.amount = amount;
            this.ref = ref;
        }
    }
    PaymentBundle pb;
    // All the payments in the file
    List<Payment> payments;

    PaymentReceiverHandler paymentHandler;

    public Payments(){
        payments = new ArrayList<Payment>();
    }

    public void initPayment(List<String> fileData) throws Exception {
        readLines(fileData);
        startPayment();
    }

    // adds a payment to the payment bundle
    public void addPayment(BigDecimal amount, String ref){
        payments.add(new Payment(amount,ref));
    }

    // Setter to set a section in a child class
    public Section setSection(int startIndex, int endIndex){
        return new Section(startIndex, endIndex);
    }

    //Getter to fetch the specific section from a string
    public String getSection(Section section, String line){
        return line.substring(section.startIndex, section.endIndex).trim();
    }
    // Starts a payment bundle and executes calls to the supplied interface with this payment bundle
    private void startPayment() {
        paymentHandler = new PaymentReceiverHandler();
        paymentHandler.startPaymentBundle(pb.accountNumber, pb.paymentDate, pb.currency);

        for (Payment p : payments) {
            paymentHandler.payment(p.amount, p.ref);
        }

        paymentHandler.endPaymentBundle();
    }

    public Date toDate(String sDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.parse(sDate);
    }

    public BigDecimal toBigDecimal(String amount){
        String formatedAmount = amount.replace(",", ".");
        return new BigDecimal(formatedAmount);
    }

    //specific to each filetype/ child class
    abstract void readLines(List<String> fileData) throws Exception;

}
