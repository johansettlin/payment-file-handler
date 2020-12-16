package src;

import java.io.FileNotFoundException;

/*Start up class for the file handler
* - Takes and path and starts the handling of the file*/
public class FileHandlerStartUp {
    public static void main(String[] args) throws Exception {
        FileHandler fh = new FileHandler();
        String path = args[0];
        /* For testing *
        String path1 = "/Users/johansettlin/Documents/java-program/payment-file-handler/Exempelfil_betalningsservice.txt";//args[0];
        String path2 = "/Users/johansettlin/Documents/java-program/payment-file-handler/Exempelfil_inbetalningstjansten.txt";
        String path3 = "/Users/johansettlin/Documents/java-program/payment-file-handler/Exempelfil_felformat.txt";*/
        if(!path.isEmpty()) {
            fh.handleFile(path);
        }else{
            System.out.println("You need to pass a path to a file to be handled");
        }
    }
}
