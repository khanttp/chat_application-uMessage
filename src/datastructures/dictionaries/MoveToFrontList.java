package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list. When implementing your iterator, you should 
 *    NOT copy every item to another dictionary/list and return that 
 *    dictionary/list's iterator. 
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    Node front;


    public MoveToFrontList() {
        this.front = null;
    }


    /**
     * Initializes a new node object with data and reference to next node.
     */
    public static class Node<K, V>{

        Node next;
        K key;
        V value;

        public Node(K key, V value){
            this.next = null;
            this.key = key;
            this.value = value;
        }

    }

    public Node getFront() {
        return this.front;
    }

    @Override
    public V insert(K key, V value) {

        // We return null if there was no key-value mapping
        // Otherwise we return the original value, before reassigning with this new value

        // If key or value is null, throw exception
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }



        V val = find(key);
        if (this.front == null && val == null ) {
            this.front = new Node(key, value);

            this.size++;

            return value;
        } else if (val == null && !(this.front.key.equals(key)) && this.size() > 0) {
            // Insert new node at front

            Node newNode = new Node(key, value);
            newNode.next = this.front;

            // Point front to new node
            this.front = newNode;


            this.size++;
            return value;
        }

        if (val != null) {
            this.front.value = value;
        }

        return val;

    }

    @Override
    // This method finds the node with the associated key and moves it to the front of the list
    public V find(K key) {

        if (key == null) {
            throw new IllegalArgumentException();
        }

        // If list is empty, then there's no mapping and we just return null
        if (this.front == null && this.size == 0) {
            return null;

        }

        if (this.front != null) {

            if (this.front.key.equals(key)) {
                return (V) this.front.value;
            } else {
                Node curr = this.front;
                Node prev = this.front;

                //Traverse list
                while (curr != null) {
                    if (curr.key.equals(key)) {

                        // Move node to front
                        Node node = curr;
                        Node temp = curr.next;
                        prev.next = temp;

                        node.next = this.front;
                        this.front = node;

                        return (V) curr.value;
                    }
                    prev = curr;
                    curr = curr.next;
                }
            }
        }

        // If no mapping found, we return null
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ListIterator();

    }

    /**
     * MoveToFrontList Iterator
     */
    private class ListIterator extends SimpleIterator<Item<K, V>> {
        private Node current;

        public ListIterator() {

            this.current = MoveToFrontList.this.front;
        }


        public boolean hasNext() {

            return this.current != null;
        }


        // Return next node with key value pair
        public Item<K, V> next() {
            Item<K, V> nextItem;


            if (hasNext()) {
                nextItem = new Item<>((K)this.current.key, (V)this.current.value);
                this.current = current.next;
                return nextItem;
            }
            return null;
        }
    }
}
