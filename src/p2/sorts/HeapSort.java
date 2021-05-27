package p2.sorts;

import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {

        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {

        MinFourHeap<E> minHeap = new MinFourHeap<E>(comparator);

        // Insert array elements into heap
        for (int i = 0; i < array.length; i++) {
            minHeap.add(array[i]);
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = minHeap.next();
        }
    }
}

