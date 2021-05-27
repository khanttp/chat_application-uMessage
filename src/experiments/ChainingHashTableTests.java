package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import org.junit.Test;

import java.util.Random;

public class ChainingHashTableTests {
    private final static int NUM_TESTS = 10;
    private final static int NUM_WARMUPS = 4;


    @Test
    public void main() {

        // We are testing MTFList, BST, AVL Tree
        ChainingHashTable<Integer, Integer> chain1 = new ChainingHashTable<>(MoveToFrontList::new);
        ChainingHashTable<Integer, Integer> chain2 = new ChainingHashTable<>(AVLTree::new);
        ChainingHashTable<Integer, Integer> chain3 = new ChainingHashTable<>(BinarySearchTree::new);

        ChainingHashTable<Integer, Integer>[] chains = new ChainingHashTable[]{chain1, chain2, chain3};
        String[] chainTypes = {"MoveToFrontList", "AVLTree", "BST Tree"};

        // Generate random inputs
        int[] inputSizes = {1000, 10000, 20000, 30000, 40000, 60000, 80000, 100000};


        for (int i = 0; i < chains.length; i++) {

            System.out.println("Trial: " + chainTypes[i]);
            System.out.println("-------------------------");

            int[] data;
            // Test on various input sizes
            for (int j = 0; j < inputSizes.length; j++) {
                data = generateInput(inputSizes[j]);

                double avgInsertTime = testInsert(chains[i], data);
                double avgFindTime = testFind(chains[i], data);

                System.out.println("Input Size: " + inputSizes[j]);
                System.out.println("Average Insert Time: " + avgInsertTime);
                System.out.println("Average Find Time: " + avgFindTime);

                System.out.println();
                System.out.println("-------------------------");
                System.out.println();
            }
            System.out.println("-------------------------");
        }

    }

    public double testInsert(ChainingHashTable<Integer, Integer> dict, int[] input) {
        double totalTime = 0;


        for (int i = 0; i < NUM_TESTS; i++) {

            long startTime = System.currentTimeMillis();
            Random rand = new Random();

            for (int j = 0; j < input.length; j++) {
                dict.insert(input[j], rand.nextInt());
            }

            long endTime = System.currentTimeMillis();

            if (NUM_WARMUPS <= i) {
                totalTime += (endTime-startTime);
            }
        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUPS);

        return averageRuntime;
    }

    public double testFind(ChainingHashTable<Integer, Integer> dict, int[] input) {
        double totalTime = 0;
        Random rand = new Random();


        for (int i = 0; i < NUM_TESTS; i++) {

            long startTime = System.currentTimeMillis();


            for (int j = 0; j < input.length; j++) {
                dict.find(input[j]);
            }

            long endTime = System.currentTimeMillis();

            if (NUM_WARMUPS <= i) {
                totalTime += (endTime-startTime);
            }
        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUPS);

        return averageRuntime;
    }

    public static int[] generateInput(int length) {
        // Generate random inputs of varying sizes
        Random rand = new Random();

        int[] result = new int[length];

        for (int i = 0; i < length; i++) {
            result[i] = rand.nextInt();
        }

        return result;
    }

}

