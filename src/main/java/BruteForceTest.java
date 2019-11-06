import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BruteForceTest {

    private static final String KEY = "zAc1";
    private static final int MAX_ITERATIONS = 10000;
    private static boolean KEY_FOUND = false;

    private static long KEY_COUNT = 0;

    //check all combinations from the given ASCII table range(decimal 33-126)
    private static final int ASCII_CODE_FROM = 33;
    private static final int ASCII_CODE_TO = 126;

    private static final int ASCII_CODE_FROM2 = 65;
    private static final int ASCII_CODE_TO2 = 90;



    public static void guessKey(){

        Set<String> asciiSet = getAsciiSet();
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
                System.out.println(prefix);
                if(prefix.equals(KEY)){
                    KEY_FOUND = true;
                }
                KEY_COUNT++;
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

    private static Set<String> getAsciiSet(){
        Set<String> asciiSet = new HashSet<String>();
        int characterCode = ASCII_CODE_FROM;

        while(characterCode <= ASCII_CODE_TO){
            asciiSet.add(String.valueOf((char)characterCode));
            characterCode++;
        }
        /* debug
        for(String s : asciiSet){
            //System.out.println(s);
        }
        */
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
