package p2.sorts;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {

        quickSort(array, comparator, 0, array.length - 1);

    }

    public static <E> void quickSort(E[] array, Comparator<E> comparator, int low, int high) {
        if (low < high) {
            int part = inSort(array, comparator, low, high);
            quickSort(array, comparator, low, part - 1);
            quickSort(array, comparator, part + 1, high);
        }

    }

    public static <E> int inSort(E[] array, Comparator<E> comparator, int low, int high) {

        // set pivot to element at end of array
        E pivot = array[high];
        int i = low - 1;
        int j = low;

        while (j < high) {
            if (comparator.compare(pivot, array[j]) >= 0) {
                i++;
                swap(array, i, j);
            }
            j++;
        }
        swap(array, i + 1, high);

        return i+1;
    }


    // method to swap elements
    private static <E> void swap(E[] array, int x, int y) {
        E temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}

