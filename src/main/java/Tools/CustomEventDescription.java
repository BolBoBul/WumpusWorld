package Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CustomEventDescription {
    private static ArrayList<String> STEALTHSUCCESS = new ArrayList<>(),
            STEALTHFAIL = new ArrayList<>(),
            AGILITYSUCCESS = new ArrayList<>(),
            AGILITYFAIL = new ArrayList<>();
    public static Random rdm=new Random();

    public static String getStealthSuccess(){
        return STEALTHSUCCESS.get(rdm.nextInt(STEALTHSUCCESS.size()))+'\n'+"PREPARE TO FIGHT!!";
    }
    public static String getStealthFail(){
        return STEALTHFAIL.get(rdm.nextInt(STEALTHFAIL.size()))+'\n'+"PREPARE TO FIGHT!!";
    }
    public static String getAgilitySuccess(){
        return AGILITYSUCCESS.get(rdm.nextInt(AGILITYSUCCESS.size()));
    }
    public static String getAgilityFail(){
        return AGILITYFAIL.get(rdm.nextInt(AGILITYFAIL.size()));
    }

    public static void loadTexts(){
        try (BufferedReader br = new BufferedReader(new FileReader(ResourcesBrowser.RESOURCESPATH+ "eventDescription.txt"))) {
            String line;
            String res="";
            while((line = br.readLine()) != null) {
                char c=line.charAt(0);
                switch (c){
                    case '1':
                        res=line.substring(3);
                        STEALTHSUCCESS.add(res+'\n');
                        break;
                    case '2':
                        res=line.substring(3);
                        STEALTHFAIL.add(res+'\n');
                        break;
                    case '3':
                        res=line.substring(3);
                        AGILITYSUCCESS.add(res+'\n');
                        break;
                    case '4' :
                        res=line.substring(3);
                        AGILITYFAIL.add(res+'\n');
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
