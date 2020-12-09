import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/* super class to all payment files*/
abstract class Payments {

    private class Section {
        int startIndex;
        int endIndex;

        Section(int startIndex, int endIndex){
            this.startIndex = startIndex - 1;
            this.endIndex = endIndex;
        }
    }

    private class PaymentBundle {
        String accountNumber;
        Date paymentDate;
        String currency;
    }

    private class Payment {
        BigDecimal amount;
        String ref;

        Payment(BigDecimal amount, String ref) {
            this.amount = amount;
            this.ref = ref;
        }
    }

    List<Payment> payments;

    public void initPayment(List<String> fileData){
        readLines(fileData);
        startPaymentBundle();
    }

    private void startPaymentBundle() {
        //gå igenom varje payment och kalla på interface
    }

    public String getSection(Section section, String line){
        return line.substring(section.startIndex, section.endIndex);
    }
    //specific to each filetype
    abstract void readLines(List<String> fileData);

}
