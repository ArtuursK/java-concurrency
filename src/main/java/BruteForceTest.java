import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BruteForceTest {

    private static final String KEY = "Passwor";
    private static final int MAX_ITERATIONS = 10000;
    private static boolean KEY_FOUND = false;

    private static long KEY_COUNT = 0;

    /*
    * ASCII_MODE
    * Possible values are:
    * 1 - all,
    * 2 - only uppercase,
    * 3 - only uppercase and lowercase
    * 4 - upper, lowercase and ?, !
    * 5 - upper, lowercase, ?, ! and numbers 0-9
    */
    private static final int ASCII_MODE = 3;



    public static void guessKey(){

        Set<String> asciiSet = getAsciiSet(ASCII_MODE);
        int characterCount = 1;

        long timeBeforeProcessing = new Date().getTime();
        while(characterCount <= KEY.length()){
            printAllKLength(asciiSet, "", asciiSet.size(), characterCount);
            characterCount++;
        }
        long timeAfterProcessing = new Date().getTime();

        if(KEY_FOUND){
            System.out.println("KEY was found! - Total iterations " + KEY_COUNT);
        }

        System.out.println("Process execution time: " + (timeAfterProcessing - timeBeforeProcessing) + " ms");

    }

    // Recursive method to print all possible strings of length k
    private static void printAllKLength(Set<String> set, String prefix, int n, int k) {

        if(KEY_FOUND == false){
            if (k == 0) {
                //System.out.println(prefix);
                if(prefix.equals(KEY)){
                    KEY_FOUND = true;
                    System.out.println("KEY: " + prefix);
                }
                KEY_COUNT++;
                if(KEY_COUNT % 100000000L == 0){
                    System.out.println("KEYs checked: " + KEY_COUNT);
                }
                return;
            }

            // One by one add all characters
            // from set and recursively
            // call for k equals to k-1
            for(String str : set){
                // Next character of input added
                String newPrefix = prefix + str;

                // k is decreased, because we have added a new character
                printAllKLength(set, newPrefix, n, k - 1);
            }
        }

    }

    private static Set<String> getAsciiSet(int asciiMode){
        int characterCodeFrom;
        int characterCodeTo;

        switch (asciiMode){
            case 1:
                characterCodeFrom = 33;
                characterCodeTo = 126;
                break;
            case 2 :
                characterCodeFrom = 65;
                characterCodeTo = 90;
                break;
            case 3:
                // 65 - 90 & 97 - 122
                characterCodeFrom = 65;
                characterCodeTo = 90;
                break;
            case 4:
                characterCodeFrom = 65;
                characterCodeTo = 90;
                break;
            case 5:
                characterCodeFrom = 65;
                characterCodeTo = 90;
                break;
            default:
                characterCodeFrom = 0;
                characterCodeTo = 127;
                break;
        }

        Set<String> asciiSet = new HashSet<String>();
        int characterCode = characterCodeFrom;

        while(characterCode <= characterCodeTo){
            asciiSet.add(String.valueOf((char)characterCode));
            characterCode++;
        }


        if(asciiMode == 3 || asciiMode == 4 || asciiMode == 5){
            characterCode = 97;
            while(characterCode <= 122){
                asciiSet.add(String.valueOf((char)characterCode));
                characterCode++;
            }
        }

        if (asciiMode == 4 || asciiMode == 5){
            asciiSet.add(String.valueOf((char)33));
            asciiSet.add(String.valueOf((char)63));
        }

        if (asciiMode == 5){
            //number 0 - 9 (ascii code 48 - 57)
            characterCode = 48;
            while(characterCode <= 57){
                asciiSet.add(String.valueOf((char)characterCode));
                characterCode++;
            }
        }



        for(String s : asciiSet){
            //System.out.println(s);
        }

        return asciiSet;
    }












    //get all possible unique permutations from a string
    public static Set<String> permute(String chars) {

        // Use sets to eliminate semantic duplicates (aab is still aab even if you switch the two 'a's)
        // Switch to HashSet for better performance
        Set<String> set = new HashSet<String>();
        //Set<String> set = new TreeSet<String>();

        // Termination condition: only 1 permutation for a string of length 1
        if (chars.length() == 1) {
            set.add(chars);
        } else {
            // Give each character a chance to be the first in the permuted string
            for (int i=0; i<chars.length(); i++) {
                // Remove the character at index i from the string
                String pre = chars.substring(0, i);
                String post = chars.substring(i + 1);
                String remaining = pre + post;

                // Recurse to find all the permutations of the remaining chars
                for (String permutation : permute(remaining)) {
                    // Concatenate the first character with the permutations of the remaining chars
                    set.add(chars.charAt(i) + permutation);
                }
            }
        }
        return set;
    }


}
