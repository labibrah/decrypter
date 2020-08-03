
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
public class Tester {

    public void test(){
        int[] key = new int[]{5, 11, 20, 19, 4};
            VigenereCipher vc = new VigenereCipher(key);
            FileResource fr = new FileResource();

            int[] keyAns = vc.tryKeyLength(fr.asString(),5,'e');
            
           for(int i =0;i< keyAns.length;i++){
            System.out.println(keyAns[i]);
        }


    
        
    }
    public String sliceString(String message, int whichSlice,int totalSlices){
    StringBuilder sb = new StringBuilder();
    for(int i =whichSlice; i< message.length();i+=totalSlices){
    sb.append(message.charAt(i));
    }
    return sb.toString();
}
        
        
     }   
        
        /* int[] key = new int[]{5, 11, 20, 19, 4};
            VigenereCipher vc = new VigenereCipher(key);
            FileResource fr = new FileResource();
            int[] keyAns = vc.tryKeyLength(fr.toString(),5,'e');
            
           for(int i =0;i< keyAns.length;i++){
            System.out.println(keyAns[i]);
        }


    }*/
    


