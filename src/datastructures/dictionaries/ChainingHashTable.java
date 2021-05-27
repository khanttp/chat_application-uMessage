package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import datastructures.worklists.ArrayStack;

import java.util.Iterator;
import java.util.function.Supplier;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 *    than 200,000 elements. After more than 200,000 elements, it should
 *    continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt
 *    NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 *    dictionary/list and return that dictionary/list's iterator.

 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {

    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] table;
    private int primeSizesIndex;

    // hardcoded prime numbers for table sizes
    private static final int[] PRIME_SIZES = {29, 347, 1103, 8629, 23663, 52673, 72671, 122743,
            177893, 202187, 252617};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.primeSizesIndex = 0;

        // initialize dictionary with the first prime size
        this.table = new Dictionary[PRIME_SIZES[primeSizesIndex]];
    }

    @Override
    public V insert(K key, V value) {

        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        //check if load factor is exceeded
        double load_factor = 1.0;
        if ((double) this.size / (double) this.table.length >= load_factor) {
            reHash();
        }

        // get index of key
        int tableIndex = Math.abs(key.hashCode() % table.length);

        V oldValue = null;

        // if no item in the table, create new chain
        if (table[tableIndex] == null) {
            table[tableIndex] = this.newChain.get();
        }

        // look for value associated with key and save it
        V oldVal = find(key);
        oldValue = table[tableIndex].insert(key, value);

        // if key is not associated with value, increment size
        if (oldVal == null) {
            this.size++;
        }

        return oldValue;
    }


    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        // Get bucket index
        int bucketIndex = Math.abs(key.hashCode() % table.length);

        // if value not found, create a new chain
        if (table[bucketIndex] == null) {
            table[bucketIndex] = newChain.get();
            return null;
        }

        // return
        return table[bucketIndex].find(key);
    }


    private void reHash() {

        Dictionary<K, V>[] newTable;

        // set new table to the size of next prime
        if (primeSizesIndex < PRIME_SIZES.length) {
            newTable = new Dictionary[PRIME_SIZES[primeSizesIndex++]];
        } else {
            // if no primes left then create new table with double the size
            newTable = new Dictionary[(this.size * 2)];
        }

        for (Item<K, V> nextItem : this) {

            // get index of nextItem
            int bucketIndex = Math.abs(nextItem.key.hashCode() % newTable.length);

            // if no next item, then create a new chain
            if (newTable[bucketIndex] == null) {
                newTable[bucketIndex] = newChain.get();
            }

            // insert item into new table
            newTable[bucketIndex].insert(nextItem.key, nextItem.value);

            // set old table to new table
            this.table = newTable;
        }

    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new HashIterator();
    }

    private class HashIterator extends SimpleIterator<Item<K, V>> {
        private ArrayStack<Item<K, V>> stack;
        public HashIterator() {
            Dictionary<K, V>[] buckets = ChainingHashTable.this.table;

            this.stack = new ArrayStack<>();

            for (Dictionary<K, V> bucket : buckets) {
                if (bucket != null) {
                    for (Item<K, V> kvItem : bucket) {
                        stack.add(kvItem);
                    }
                }
            }
        }

        public boolean hasNext() {
            return stack.hasWork();
        }

        // Return next node with key value pair
        public Item<K, V> next() {
            return stack.next();
        }

    }

}

