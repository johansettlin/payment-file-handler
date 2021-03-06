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
    //might want to move out because it would probably be used by different types of files as well
    // A section is specific byte positions in a line of a file.
    class Section {
        int startIndex;
        int endIndex;

        Section(int startIndex, int endIndex){
            this.startIndex = startIndex - 1;
            this.endIndex = endIndex;
        }
    }
    // Used to store the information about all the payments about to be made
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
    // A single payment
    class Payment {
        BigDecimal amount;
        String ref;

        Payment(BigDecimal amount, String ref) {
            this.amount = amount;
            this.ref = ref;
        }
    }
    // all payment files contains a payment bundle and N payments.
    PaymentBundle pb;
    // All the payments in the file
    List<Payment> payments;
    // An handler that uses the payment bundle and the payments to call the payment interface
    PaymentReceiverHandler paymentHandler;

    // Construtor that initializes datastructures
    Payments(){
        payments = new ArrayList<Payment>();
        paymentHandler = new PaymentReceiverHandler();
    }

    // Starts the handling of a Payment file,
    // 1. read the lines to extract important data
    // 2. call the interface functions
    public void initPayment(List<String> fileData) throws Exception {
        readLines(fileData);
        startPayment();
    }

    // adds a payment to the payments list
    public void addPayment(BigDecimal amount, String ref){
        payments.add(new Payment(amount,ref));
    }

    // Setter to set a section in a child class
    public Section setSection(int startIndex, int endIndex){
        return new Section(startIndex, endIndex);
    }

    //Getter to fetch the specific section from a string, also this function removes all empty bytes.
    public String getSection(Section section, String line){
        return line.substring(section.startIndex, section.endIndex).trim();
    }
    // Starts a payment bundle and executes calls to the supplied interface with this payment bundle
    private void startPayment() {
        paymentHandler.startPaymentBundle(pb.accountNumber, pb.paymentDate, pb.currency);

        for (Payment p : payments) {
            paymentHandler.payment(p.amount, p.ref);
        }

        paymentHandler.endPaymentBundle();
    }

    /*Helper functions for formatting the data*/
    // Converts a string to a date
    public Date toDate(String sDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.parse(sDate);
    }

    // converts a string to a Big Decimal data type
    public BigDecimal toBigDecimal(String amount){
        String formattedAmount = amount.replace(",", ".");
        return new BigDecimal(formattedAmount);
    }

    //specific to each filetype/ child class
    abstract void readLines(List<String> fileData) throws Exception;

}
