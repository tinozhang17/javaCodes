import java.util.Queue;
import java.util.Stack;
import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Ziyu Zhang
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail shaker sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * When writing your sort, don't recheck already sorted items. The amount of
     * items you are comparing should decrease by 1 for each pass of the array
     * (in either direction). See the PDF for more info.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailShakerSort(T[] arr,
                                              Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("parameter null!");
        }
        int length = arr.length;
        int i = 1;
        while (i <= length - 1) {
            for (int j = i - 1; j <= length - i - 1; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
            for (int k = length - i - 1; k >= i; k--) {
                if (comparator.compare(arr[k], arr[k - 1]) < 0) {
                    T tmp = arr[k];
                    arr[k] = arr[k - 1];
                    arr[k - 1] = tmp;
                }
            }
            i++;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("parameter null!");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            T tmp = arr[i + 1];
            for (int k = i; k >= 0
                    && comparator.compare(arr[k], tmp) > 0; k--) {
                arr[k + 1] = arr[k];
                arr[k] = tmp;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("parameter null!");
        }
        for (int head = 0; head < arr.length - 1; head++) {
            T min = arr[head];
            int minIndex = head;
            for (int i = head + 1; i < arr.length; i++) {
                if (comparator.compare(arr[i], min) < 0) {
                    min = arr[i];
                    minIndex = i;
                }
            }
            T tmp = arr[head];
            arr[head] = min;
            arr[minIndex] = tmp;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("parameter null!");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     *This is the helperMethod for quicksort, it enables recursion
     *
     * @param <T> data type to sort
     * @param arr the array to be quicksorted
     * @param comparator the comparator
     * @param rand the randomizer
     * @param l left bound
     * @param r right bound
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator,
                                            Random rand, int l, int r) {
        // both l and r inclusive
        if (l >= r) {
            return;
        }
        int left = l;
        int right = r;
        int pivot = rand.nextInt(right + 1 - left) + left;
        while (left < right) {
            while (left != pivot
                    && comparator.compare(arr[left], arr[pivot]) < 0) {
                left++;
            }
            while (right != pivot
                    && comparator.compare(arr[right], arr[pivot]) > 0) {
                right--;
            }
            if (left < right) {
                T tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
                if (left == pivot) {
                    pivot = right;
                } else if (right == pivot) {
                    pivot = left;
                }
            }
        }
        quickSortHelper(arr, comparator, rand, l, pivot - 1);
        quickSortHelper(arr, comparator, rand, pivot + 1, r);
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * ********************* IMPORTANT ************************
     * FAILURE TO DO SO MAY CAUSE ClassCastException AND CAUSE
     * YOUR METHOD TO FAIL ALL THE TESTS FOR MERGE SORT
     * ********************************************************
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("parameters null!");
        }
        int length = arr.length;
        if (length < 2) {
            return;
        }
        int mid = length / 2;
        T[] arr1 = (T[]) new Object[mid];
        T[] arr2 = (T[]) new Object[length - mid];
        for (int i = 0; i < mid; i++) {
            arr1[i] = arr[i];
        }
        int k = 0;
        for (int j = mid; j < length; j++) {
            arr2[k++] = arr[j];
        }
        mergeSort(arr1, comparator);
        mergeSort(arr2, comparator);
        merging(arr1, arr2, arr, comparator);
    }

    /**
    * Helper method to merge the two sub-lists;
    * @param <T> the data type to be sorted
    * @param arr1 first array
    * @param arr2 second array
    * @param orig the original array
    * @param comparator the comparator
     */
    private static <T> void merging(T[] arr1, T[] arr2, T[] orig,
                                   Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        while (i + j < orig.length) {
            if (j == arr2.length || (i < arr1.length
                    && comparator.compare(arr1[i], arr2[j]) < 0)) {
                orig[i + j] = arr1[i++];
            } else {
                orig[i + j] = arr2[j++];
            }
        }
    }

    /**
     * Implement radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * DO NOT USE {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use an ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] radixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("parameter null!");
        }
        int max = Math.abs(arr[0]);
        int exp = 1;
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            if (arr[i] == -2147483648) {
                max = 2147483647;
            } else if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }
        while (Math.abs(max / exp) > 0) {
            Queue<Integer>[] bucket = new LinkedList[10];
            for (int j = 0; j < length; j++) {
                if (arr[j] / exp != 0) {
                    int index = Math.abs((arr[j] / exp) % 10);
                    if (bucket[index] == null) {
                        bucket[index] = new LinkedList<>();
                    }
                    bucket[index].add(arr[j]);
                } else {
                    if (bucket[0] == null) {
                        bucket[0] = new LinkedList<>();
                    }
                    bucket[0].add(arr[j]);
                }
            }
            int e = 0;
            for (int k = 0; k < 10; k++) {
                if (bucket[k] != null) {
                    while (bucket[k].peek() != null) {
                        arr[e++] = bucket[k].poll();
                    }
                }
            }
            exp *= 10;
        }
        Queue<Integer> positive = new LinkedList<>();
        Stack<Integer> negative = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                negative.push(arr[i]);
            } else {
                positive.add(arr[i]);
            }
        }
        int m = 0;
        while (!negative.empty()) {
            arr[m] = negative.pop();
            m++;
        }
        while (positive.peek() != null) {
            arr[m] = positive.poll();
            m++;
        }
        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sort instead of {@code Math.pow()}. DO NOT MODIFY THIS METHOD.
     *
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power.
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Invalid exponent.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
