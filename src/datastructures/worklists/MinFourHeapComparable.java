package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;

    private int capacity = 10;
    private int size;

    public MinFourHeapComparable() {

        this.data = (E[]) new Comparable[this.capacity];
        this.size = 0;
    }

    @Override
    public boolean hasWork() {

        return this.size() > 0;
    }

    @Override
    public void add(E work) {
        // Resize array if full
        if (this.size() == this.data.length) {

            E[] newArr = (E[]) new Comparable[this.data.length * 2];

            // Copy elements into new array
            int i;
            for (i = 0; i < this.data.length; i++) {
                newArr[i] = this.data[i];
            }

            this.data = newArr;
        }


        // Increment size for new node insert
        this.size++;

        // Insert element at end of array
        this.data[this.size-1] = work;

        // Find insertion point by percolating up
        percolateUp(this.size-1);
    }

    private void percolateUp(int hole) {

        int parent = (hole-1)/4;
        // Continue percolating up while inserted value is less than parent
        while (hole > 0 && this.data[hole].compareTo(this.data[parent]) < 0) {

            E tempValue = this.data[hole];

            // Swap parent with current insertion value
            this.data[hole] = this.data[parent];
            this.data[parent] = tempValue;

            hole = parent;
            parent = (hole-1)/4;
        }
    }



    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        return this.data[0];
    }


    private int percolateDown(int parent, E value) {


        // Continue to percolate down while parent can be lower
        int minIndex = 0;
        while ((4*parent) <= (this.size()-1)) {

            int childIndex;
            E minVal = value;


            // Get minimum amidst children
            for (int child = 1; child <= 4; child++) {
                childIndex = (4*parent)+child;

                if (childIndex > this.size()-1) {
                    break;
                }

                if (this.data[childIndex].compareTo(minVal) < 0) {
                    minVal = this.data[childIndex];
                    minIndex = childIndex;
                }
            }

            if (minVal.compareTo(value) < 0) {

                // Swap parent with new min index
                this.data[parent] = this.data[minIndex];
                parent = minIndex;
            } else {
                break;
            }
        }
        return parent;
    }


    @Override
    // Delete min
    public E next() {
        if (!hasWork() ) {
            throw new NoSuchElementException();
        }

        // min element will be at root
        E minElement = this.data[0];


        // Percolate down and decrement size
        int hole = percolateDown(0, this.data[this.size-1]);
        this.data[hole] = this.data[this.size-1];
        this.size--;


        return minElement;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.data = (E[]) new Comparable[this.capacity];

        this.size = 0;
    }
}
