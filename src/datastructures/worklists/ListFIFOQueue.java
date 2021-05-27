package datastructures.worklists;

import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */

public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    int size;
    Node front;
    Node back;

    /**
     * Initializes a new node object with data and reference to next node.
     *
     * @param <E>
     */
    private static class Node<E> {
        E data;
        Node next;

        public Node(E data) {
            this.next = null;
            this.data = data;
        }

    }

    // Queue properties
    public ListFIFOQueue() {
        this.size = 0;
        this.front = this.back = null;

    }

    public void add(E work) {

        // Create a new node to be added
        Node newNode = new Node(work);

        // If list is empty, then make front = newNode = back
        if (this.back == null) {
            this.front = newNode;
            this.back = newNode;
        } else {

            // Add new node to end of list
            this.back.next = newNode;
            this.back = newNode;
        }
        this.size++;
    }

    public E peek() {

        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        // Return data at beginning of list
        return (E) this.front.data;

    }


    public E next() {

        Node newNode = null;
        if (!hasWork()) {
            throw new NoSuchElementException();

        } else {

            // Save element to be removed
            newNode = this.front;

            // Advance front pointer and decrement size
            this.front = this.front.next;
            this.size--;


            // Advancing front pointer causes list to become empty
            // Reset back pointer
            if (this.front == null) {
                this.back = null;
            }

        }
        return (E) newNode.data;
    }

    public int size() {
        return this.size;
    }


    public void clear() {
        this.size = 0;
        this.front = this.back = null;
    }
}
