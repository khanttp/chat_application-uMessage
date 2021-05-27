package experiments;

import cse332.types.AlphabeticString;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;


public class HashCodeTests {
    private final static int NUM_TESTS = 100;
    private final static int NUM_WARMUPS = 5;


    @Test
    public void main() {

        ChainingHashTable<AlphabeticString, Integer> chtString = new ChainingHashTable<>(MoveToFrontList::new);


        // Inputs - alice.txt, me.txt. egg.txt
        File dictText = new File("dictionary.txt");


        HashSet<AlphabeticString> dictWords = setWords(dictText);

        // Test bad hashcode first

        // Test insert and find on hashtriemap
        double insertTotalTime = 0;
        double findTotalTime = 0;


        for (int i  = 0; i < NUM_TESTS; i++) {
            long insertStartTime = System.currentTimeMillis();
            for (AlphabeticString w : dictWords) {

                chtString.insert(w, 0);
            }
            long insertEndTime = System.currentTimeMillis();


            long findStartTime = System.currentTimeMillis();

            for (AlphabeticString w: dictWords) {

                chtString.find(w);
            }

            long findEndTime = System.currentTimeMillis();



            if (NUM_WARMUPS <= i) {
                insertTotalTime += (insertEndTime-insertStartTime);
                findTotalTime += (findEndTime-findStartTime);
            }
        }
        double insertAvgTime = insertTotalTime / (NUM_TESTS - NUM_WARMUPS);
        double findAvgTime = findTotalTime / (NUM_TESTS-NUM_WARMUPS);

        System.out.println("Bad HashCode");
        System.out.println("----------------------------------");
        System.out.println("Average insert time: " + insertAvgTime);
        System.out.println("Average find time: " + findAvgTime);

    }


    // Get set of words
    public HashSet<AlphabeticString> setWords(File text) {

        HashSet<AlphabeticString> words = new HashSet<>();

        try {
            Scanner scan = new Scanner(text);

            while (scan.hasNext()) {
                String word = scan.next();
                AlphabeticString str = new AlphabeticString(word);
                words.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return words;
    }


}
