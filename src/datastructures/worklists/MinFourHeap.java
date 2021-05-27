package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size = 0;
    private int capacity = 10;
    private Comparator<E> comp;
    
    public MinFourHeap(Comparator<E> c) {
        this.comp = c;
        this.data = (E[]) new Object[this.capacity];
    }

    @Override
    public boolean hasWork() {

        return this.size() > 0;
    }

    @Override
    public void add(E work) {

        if (this.size() == this.data.length) {
            E[] newArr = (E[]) new Object[this.data.length * 2];

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
        while ((hole > 0) && (comp.compare(this.data[hole], this.data[parent]) < 0)) {

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

                if (this.comp.compare(this.data[childIndex],minVal) < 0) {
                    minVal = this.data[childIndex];
                    minIndex = childIndex;
                }
            }

            if (this.comp.compare(minVal,value) < 0) {

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

        this.data = (E[]) new Object[this.capacity];
        this.size = 0;
    }
}
