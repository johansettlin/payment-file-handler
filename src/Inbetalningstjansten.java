package src;

import java.util.Date;
import java.util.List;

public class Inbetalningstjansten extends Payments {
    /*Posts */
    Section post;
    private String openingPost = "00"; // from the description of the file type
    private String paymentPost = "30"; // from the description of the file type
    private String endPost = "99"; // from the description of the file type

    /*Opening post*/
    private Date paymentDate = null; // Not specified in the specification
    private String currency = "SEK"; // from the specification
    private Section accountNumber;

    /* Payment posts */
    private Section amount;
    private Section ref;

    /* For testing */
    private Section totalAmount; // Just to check that the amount adds up
    private Section numberOfPayments; // To check that the payments adds up


    public Inbetalningstjansten(){
        //Initialize all the sections
        post = setSection(1,2);
        accountNumber = setSection(15,24);

        amount = setSection(3,22);
        ref = setSection(41,65);

        totalAmount = setSection(3,22);
        numberOfPayments = setSection(31,38);
    }

    @Override
    void readLines(List<String> fileData) throws Exception {
        for(String line : fileData){
            String postType = getSection(post, line);

            if(postType.equals(openingPost)){ // the line is a opening post
                pb = new PaymentBundle(getSection(accountNumber, line), paymentDate, "SEK");
            }else if(postType.equals(paymentPost)){ // the line is a payment post
                addPayment(toBigDecimal(getSection(amount, line)), getSection(ref, line));
            }else if(postType.equals(endPost)){ // the line is a end post
                //Nothing needed for the interface but variables can be used for testing
            }else throw new Exception("Post type not supported!"); // the post type is not supported
        }

    }
}
