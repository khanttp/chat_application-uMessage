package datastructures.worklists;

import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable> extends FixedSizeFIFOWorkList<E> {
    private E[] arr;
    private int front;
    private int back;
    private int len;

    public CircularArrayFIFOQueue(int capacity) {

        // Constructs circular array with this capacity

        // Indicate that parent constructor should take this parameter capacity
        super(capacity);
        this.len = 0;
        this.arr =  (E[])new Comparable[capacity];
        this.front = 0;
        this.back = 0;
    }

    @Override
    public void add(E work) {

        if (isFull()) {
            throw new IllegalStateException();
        } else {

            // Add to back of array
            this.arr[back] = work;

            // Wrap around if back pointer goes beyond capacity
            back = (back+1) % this.arr.length;
            this.len++;
        }

    }

    @Override
    public E peek() {

        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            return this.arr[front];
        }
    }

    @Override
    public E peek(int i) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return this.arr[(front+i)%this.arr.length];
        }
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {

            // Save element to bge removed
            E element = this.arr[front];

            // Remove value at front and advance front pointer
            this.arr[front] = null;
            front = (front+1) % this.arr.length;
            this.len--;
            return element;
        }

    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        if (i < 0 || i >= this.size()) {
            throw new IndexOutOfBoundsException();
        } else {

            // Update ith element and wrap around if needed
            this.arr[(front+i)%this.arr.length] = value;
        }

    }



    @Override
    public int size() {

        return this.len;
    }



    @Override
    public void clear() {
        this.len = 0;
        this.front = 0;
        this.back = 0;
    }




    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // We loop through the minimum length of the two
        int end = Math.min(other.size(), this.size());

        // Check for inequality in prefixes
        if (!(other.equals(this))){
            for (int i = 0; i < end; i++) {
                if (this.peek(i) != other.peek(i)) {
                    return this.peek(i).compareTo(other.peek(i));
                }
            }
        }

        // If they had the same prefix but one has a greater size, the string with greater size wins
        if (this.size() > other.size()) {
            return 1;
        } else if (other.size() > this.size()) {
            return -1;
        }

        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // If sizes are not equal they cannot be equal
            if (other.size() != this.size()) {
                return false;
            } else {

                for (int i = 0; i < this.size(); i++) {
                    if (other.peek(i) != this.peek(i)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    @Override
    public int hashCode() {

        // Horner's rule to compute ith character multiplied by 37^i
        int total = 0;
        for (int i = 0; i < this.size(); i++) {
            total = (this.peek(i).hashCode()  + (37*total));
        }

        return total;

    }
}
