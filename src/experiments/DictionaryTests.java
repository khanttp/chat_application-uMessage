package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class DictionaryTests {
    private final static int NUM_TESTS = 1000;
    private final static int NUM_WARMUPS = 10;

    @Test
    public void main() {

        // Initialize BST, AVL, ChainingHashTable, and HashTrieMap
        BinarySearchTree<String, String> bst = new BinarySearchTree<>();
        AVLTree<String, String> avl = new AVLTree<>();
        ChainingHashTable<String, String> chainHashTable = new ChainingHashTable<>(AVLTree::new);
        HashTrieMap<Character, AlphabeticString, String> hashTrieMap = new HashTrieMap<>(AlphabeticString.class);

        DeletelessDictionary<String, String>[] dictionaries = new DeletelessDictionary[]{bst, avl, chainHashTable};
        String[] dictNames = {"Binary Search Tree", "AVL Tree", "Chaining Hash Table"};


        // Test on alice.txt
        File text = new File("alice.txt");

        HashSet<String> hashSet = new HashSet<>();

        try {
            Scanner scan = new Scanner(text);

            while (scan.hasNext()) {
                String word = scan.next();
                hashSet.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        // Test insert and find on avl, vst, cht
        for (int i = 0; i < dictionaries.length; i++) {
            double avgInsertTime = testInsert(dictionaries[i], hashSet);
            double avgFindTime = testFind(dictionaries[i], hashSet);

            System.out.println("Dictionary: " + dictNames[i]);
            System.out.println("----------------------------------");
            System.out.println("The average insert time is: " + avgInsertTime);
            System.out.println("The average find time is: " + avgFindTime);

            System.out.println("----------------------------------");

        }

        // Test insert and find on hashtriemap
        double insertTotalTime = 0;
        double findTotalTime = 0;

        for (int i  = 0; i < NUM_TESTS; i++) {
            long insertStartTime = System.currentTimeMillis();
            for (String w : hashSet) {
                AlphabeticString str = new AlphabeticString(w);
                hashTrieMap.insert(str, w);
            }
            long insertEndTime = System.currentTimeMillis();


            long findStartTime = System.currentTimeMillis();

            for (String w: hashSet) {
                AlphabeticString str = new AlphabeticString(w);
                hashTrieMap.find(str);
            }

            long findEndTime = System.currentTimeMillis();



            if (NUM_WARMUPS <= i) {
                insertTotalTime += (insertEndTime-insertStartTime);
                findTotalTime += (findEndTime-findStartTime);
            }
        }
        double insertAvgTime = insertTotalTime / (NUM_TESTS - NUM_WARMUPS);
        double findAvgTime = findTotalTime / (NUM_TESTS-NUM_WARMUPS);

        System.out.println("Dictionary: HashTrieMap");
        System.out.println("----------------------------------");
        System.out.println("The average insert time is: " + insertAvgTime);
        System.out.println("The average find time is: " + findAvgTime);

        System.out.println("----------------------------------");
    }

    public double testInsert(DeletelessDictionary<String, String> dict, HashSet<String> input) {
        double totalTime = 0;


        for (int i = 0; i < NUM_TESTS; i++) {

            long startTime = System.currentTimeMillis();

            for (String w : input) {
                dict.insert(w, w);
            }


            long endTime = System.currentTimeMillis();

            if (NUM_WARMUPS <= i) {
                totalTime += (endTime-startTime);
            }
        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUPS);

        return averageRuntime;
    }

    public double testFind(DeletelessDictionary<String, String> dict, HashSet<String> input) {
        double totalTime = 0;


        for (int i = 0; i < NUM_TESTS; i++) {

            long startTime = System.currentTimeMillis();

            for (String w : input) {
                dict.find(w);
            }


            long endTime = System.currentTimeMillis();

            if (NUM_WARMUPS <= i) {
                totalTime += (endTime-startTime);
            }
        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUPS);

        return averageRuntime;
    }
}
