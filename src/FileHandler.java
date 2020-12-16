package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    List<String> supportedExtensions; // A dictionary of supported files
    
    public FileHandler(){
        supportedExtensions = new ArrayList<String>();
        supportedExtensions.add("_betalningsservice.txt");
        supportedExtensions.add("_inbetalningstjansten.txt"); // wrong name for now
    }
    
    // main file handler, this is where the api decide on what to do with the file type
    public void handleFile(String path) throws Exception {
        String extensions = getExtension(path);

        // Check if extension is supported in the file handler
        if(supportedExtensions.contains(extensions)){
            //Read the data content of the file
            List<String> fileData = readFile(path);
            switch (extensions){
                //Payments
                case "_betalningsservice.txt":
                   Payments bet = new Betalningsservice();
                   bet.initPayment(fileData);
                   break;
                case "_inbetalningstjansten.txt":
                    Payments bet1 = new Inbetalningstjansten();
                    bet1.initPayment(fileData);
                    break;
                //Here we could extend for other filetypes such as Change of person data.
            }
        }else throw new FileNotFoundException("Can not handle this type of files!");
    }

    /* Reads a file, this would be needed for all file types*/
    private List<String> readFile(String path) {
        List<String> fileData = new ArrayList<>();
        try {
            Path translatePath = Paths.get(path);
            fileData = Files.readAllLines(translatePath, StandardCharsets.ISO_8859_1);
            return fileData;
        } catch (Exception e) {
            System.out.println("An error occurred when reading file, please try again.");
            e.printStackTrace();
        }
        return null;
    }

    /* Gets the extension of the file */
    private String getExtension(String path) {
        // In case of several '_' in the filepath
        int lastUnderscore = path.lastIndexOf("_");
        return path.substring(lastUnderscore);
    }
}
