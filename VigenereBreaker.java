import java.util.*;
import edu.duke.*;
import java.io.File;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE

        StringBuilder sb = new StringBuilder();
        for(int i =whichSlice; i< message.length();i+=totalSlices){
            sb.append(message.charAt(i));
        }
        return sb.toString();

    }
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //WRITE YOUR CODE HERE
        for(int i = 0;i<klength;i++){
            StringBuilder slice = new StringBuilder();

            slice.append(sliceString(encrypted,i,klength));
            CaesarCracker cc = new CaesarCracker(mostCommon);

            int currKey = cc.getKey(slice.toString());
            key[i] = currKey;
        }
        return key;

    }

    public void breakVigenere () {
        //WRITE YOUR CODE HERE
        HashMap<String, HashSet<String>> dictonaries = new HashMap<String, HashSet<String>>();
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
       
        FileResource frD = new FileResource(f);
        HashSet<String> words = readDictonary(frD);
        dictonaries.put(f.getName(),words);
      
    }
      
        FileResource fr = new FileResource();
        String input = fr.asString();
      
       breakForAllLangs(input,dictonaries);

        
        

    }

    public HashSet<String> readDictonary(FileResource fr){
        HashSet<String> words = new HashSet<String>();

        for(String word: fr.lines()){

            String lowerCaseWord = word.toLowerCase();
            words.add(lowerCaseWord);
        }

        return words;
    }

    public int countWords(String message, HashSet<String> dictonary){
        String[] mssg = message.split("\\W+");
        int count = 0;
        for(int i = 0;i< mssg.length;i++){
            String word = mssg[i].toLowerCase();
            if(dictonary.contains(word)){
                count++;
            }
        }
        return count;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictonary){
        int indexOfMax = 0;
        int maxWord =0;

    
        for(int i =1;i<101;i++){

            int[] keyL = tryKeyLength(encrypted,i, mostCommonCharIn(dictonary));
            VigenereCipher vc = new VigenereCipher(keyL);
            String decrypted = vc.decrypt(encrypted);
            int wordCountDic = countWords(decrypted,dictonary);
            if(wordCountDic>maxWord){
                maxWord = wordCountDic;
                indexOfMax = i;

            }

        }
        //System.out.println("Words: " + maxWord);
        //System.out.println("Key Length: " + indexOfMax);

        VigenereCipher vC = new VigenereCipher(tryKeyLength(encrypted,indexOfMax, 'e'));
        return vC.decrypt(encrypted);

    }

    public char mostCommonCharIn(HashSet<String> dictionary){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[] count = new int[26];
        int max=0;
        int index =0;
        for(String s:dictionary){
            for (int i = 0 ; i < s.length() ; i++)
            {

                char ch = s.charAt(i); 
                index = alphabet.indexOf(ch);
                if(index>=0 && index<=26){ 
                    count[index]++;
                }

            }
        }

        for(int i = 0 ; i < count.length;i++){
            if(max>count[i]){
                max = count[i];
                index = i;
            }
        }

        return alphabet.charAt(index);

    }
    public void breakForAllLangs(String encrypted,HashMap<String, HashSet<String>> languages){
        String mostWordLang = new String();
        int maxWord =0;
        for(String lang : languages.keySet()){
        String decrypted = breakForLanguage( encrypted,languages.get(lang));
        int currCount = countWords(decrypted, languages.get(lang));
        if(currCount>maxWord){
        maxWord = currCount;
        }
        }
        for(String lang : languages.keySet()){
        String decrypted = breakForLanguage( encrypted,languages.get(lang));
        int currCount = countWords(decrypted, languages.get(lang));
            if(currCount==maxWord){
            System.out.println(decrypted);
            System.out.println(lang);
            }
        }
        
    }
    
    public void testMostCommon(){
        FileResource frD = new FileResource();
        HashSet<String> words = readDictonary(frD);
        System.out.println(mostCommonCharIn(words));

           
    }
}

