import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    List<String> supportedExtensions;
    
    public FileHandler(){
        supportedExtensions = new ArrayList<String>();
        supportedExtensions.add("betalservice.txt");
        supportedExtensions.add("inbetalningstjanst.txt");
    }
    
    
    public void handleFile(String path){
        String extensions = getExtension(path);
        
        if(supportedExtensions.contains(extensions)){
            List<String> fileData = readFile(path);
            switch (extensions){
                case "betalservice.txt": 
                    new Betalservice.initPayement(fileData);
                case "inbetalningstjanst.txt":
                    new inbetalningsTjanst().initPayment(fileData);
            }
        }//else throw exceptions
    }

    private List<String> readFile(String path) {
        List<String> fileData = new ArrayList<>();

    }

    private String getExtension(String path) {
    }
}
