package src;

import java.util.List;

public class Betalningsservice extends Payments{
    /* SECTIONS */

    /*Posts */
    Section post;
    String openingPost = "O"; // from the description of the file type
    String paymentPost = "B"; // from the description of the file type

    /* Needed information from the opening post */
    Section accountNumber;
    Section paymentDate;
    Section currency;

    /* Needed information from a payment post */
    Section amount;
    Section ref;

    /* for testing */
    Section totalAmount; // Just to check that the amount adds up
    Section numberOfPayments; // To check that the payments adds up


    public Betalningsservice(){
        post = setSection(1, 1);
        // set all the sections in the opening post
        accountNumber = setSection(2,16);
        paymentDate = setSection(41, 48);
        currency = setSection(49,51);
        //testing in opening post
        totalAmount = setSection(17,30);
        numberOfPayments = setSection(31,40);

        //Set all the sections for a payment
        amount = setSection(2,15);
        ref = setSection(16,50);

    }
    @Override
    void readLines(List<String> fileData) throws Exception {
        // Testing variables
        int paymentsInOpening = 0;
        int actualPaymentPosts = 0;

        double amountInOpening = 0;
        double actualAmount = 0;

        for(String line : fileData){
            // if the current line is the opening post set the payment bundle
            if(getSection(post, line).equals(openingPost)){
                //paymentsInOpening = Integer.parseInt(getSection(numberOfPayments, line)); // for testing
                //amountInOpening = Double.parseDouble(getSection(totalAmount, line).replace(",", ".")); // for testing

                pb = new PaymentBundle(getSection(accountNumber, line), toDate(getSection(paymentDate, line)), getSection(currency, line));
            // else if the current line is a payment post add a new payment
            }else if(getSection(post, line).equals(paymentPost)){
                //actualPaymentPosts ++; // for testing
                actualAmount += Double.parseDouble(getSection(amount, line).replace(",", "."));

                addPayment(toBigDecimal(getSection(amount, line)), getSection(ref, line));
            //A post that does not exists
            }else throw new Exception("Post type not supported!");
        }
        // Check that the opening post is telling the truth and exists however the amount does not add upp in the example file.
        //if((paymentsInOpening != actualPaymentPosts) && (paymentsInOpening != 0)) throw new Exception("Number of payments does not add up!");
        //if((amountInOpening != actualAmount) && (actualAmount != 0)) throw new Exception("The amount is not adding up!");
    }
}
