
package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;
import org.junit.Test;
import java.util.Random;


public class TreeTests {

    // total trials
    private final static int TRIALS = 8;

    // number of tests for each trial
    private final static int NUM_TESTS = 10;
    private final static int WARM_UPS= 3;

    @Test
    public void main(){
        //testTree(new BinarySearchTree<Integer,Integer>(), "BST");
        testTree(new AVLTree<Integer,Integer>(), "AVL");

    }

    public void testTree(Dictionary<Integer,Integer> tree, String name){

        // array of input sizes for trials
        int[] trialSizes = {1000,10000,20000, 30000, 40000, 60000,80000,100000};

        double insertTime = 0;
        double findTime = 0;
        Random rand = new Random();

        int currentTrial = 0;
        while (currentTrial < TRIALS) {

            // perform 10 tests for each trial
            int currentTest = 0;
            while (currentTest < NUM_TESTS) {
                // start insert time
                long insertStartTime = System.currentTimeMillis();

                for(int key = 0; key < trialSizes[currentTrial]; key++){
                    // generate a random number for value
                    int randomNum = rand.nextInt(trialSizes[currentTrial]);
                    // insert into tree
                    tree.insert(key,randomNum);
                }
                long insertEndTime = System.currentTimeMillis();

                // start timer for find
                long findStartTime = System.currentTimeMillis();
                for(int key = 0; key < trialSizes[currentTrial]; key++){
                    tree.find(key);
                }
                // end timer for find
                long findEndTime = System.currentTimeMillis();

                // compute insert and find times for each test
                if(WARM_UPS <= currentTest){
                    insertTime += (insertEndTime-insertStartTime);
                    findTime += (findEndTime-findStartTime);
                }

                currentTest++;
            }

            // compute average insert and find times for the 10 tests
            double insertAverageTime = insertTime/(NUM_TESTS -WARM_UPS);
            double findAverageTime = findTime/(NUM_TESTS -WARM_UPS);
            double insertFindAverage = (insertAverageTime + findAverageTime)/(NUM_TESTS -WARM_UPS);

            //Print out results
            System.out.println("Input Size: " + trialSizes[currentTrial]);
            System.out.println(name + "Average Runtime For Insert: " + insertAverageTime);
            System.out.println(name + "Average Runtime For Find: " + findAverageTime);
            System.out.println(name + "insert and Find Average Runtime: " + insertFindAverage);

            System.out.println("------------------");

            // perform 7 trials
            currentTrial++;
        }
    }
}

