package src;

import java.io.FileNotFoundException;

/*Start up class for the file handler
* - Takes and path and starts the handling of the file*/
public class FileHandlerStartUp {
    public static void main(String[] args) throws Exception {
        FileHandler fh = new FileHandler();
        String path = "/Users/johansettlin/Documents/java-program/payment-file-handler/src/Exempelfil_betalningsservice.txt";//args[0];

        if(!path.isEmpty()) {
            fh.handleFile(path);
        }else{
            System.out.println("You need to pass a path to a file to be handled");
        }
    }
}
