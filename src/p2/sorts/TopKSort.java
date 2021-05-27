package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

public class TopKSort {

    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap(comparator);
        for (E e : array) {
            if (heap.size() < k) {
                heap.add(e);
            } else if (heap.size() == k) {
                if (comparator.compare(heap.peek(), e) <= -1) {
                    heap.add(e);
                    heap.next();
                }
            }

        }

        for (int i = 0; i < k; i++) {
            array[i] = heap.next();
            System.out.println(array[i]);
        }
    }
}
