package datastructures.worklists;

import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int index;
    private int size = 10;
    private E[] arr;


    public ArrayStack() {
        this.index = 0;
        this.arr = (E[])new Object[size];
    }

    @Override
    public void add(E work) {

        // Double capacity once array is filled
        if (size() == arr.length) {
            E[] newArr = (E[]) new Object[arr.length * 2];

            // Copy elements to new array
            for (int i = 0; i < arr.length; i++) {
                newArr[i] = arr[i];
            }
            arr = newArr;
        }

        // Add to "top" of stack
        arr[index++] = work;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }

        // Return element at "top" of stack
        return arr[index-1];
    }

    @Override
    public E next() {

        // Stack is empty so no element to remove
        if (this.index == 0) {
            throw new NoSuchElementException();
        }

        // Save element at top to be removed and decrement top pointer
        E work = arr[--index];

        // Remove value at old "top"
        arr[index] = null;
        return work;
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public void clear() {
        this.arr = (E[])new Object[size];
        this.index = 0;
    }
}
