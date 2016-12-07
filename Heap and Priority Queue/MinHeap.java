import java.util.NoSuchElementException;

/**
 * Your implementation of a min heap.
 * @author Ziyu Zhang
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables

    /**
     * Creates a Heap.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[15];
        size = 0;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null!");
        }
        int index = size + 1;
        if (backingArray.length - 1 <= size) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        backingArray[index] = item;
        size++;
        if (index / 2 != 0) {
            upHeap(index);
        }
    }

    /**
     * goes up the heap and switch elements
     * until every parent is smaller than its children
     *
     * @param index the index at which the going up will start at
     */
    private void upHeap(int index) {
        if (backingArray[index].compareTo(backingArray[index / 2]) < 0) {
            T content = backingArray[index];
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = content;
            if (index / 4 != 0) {
                upHeap(index / 2);
            }
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty!");
        }
        T data = null;
        if (!isEmpty()) {
            data = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            downHeap(1);
        }
        return data;
    }

    /**
     * goes down the heap and switch elements
     * until every parent is smaller than its children
     *
     * @param index the index at which the going down will start at
     */
    private void downHeap(int index) {
        T temp = backingArray[index];
        if (index * 2 + 1 <= size) { // if there are both children
            if (temp.compareTo(backingArray[index * 2]) > 0
                && temp.compareTo(backingArray[index * 2 + 1])
                    > 0) { // if parent larger than both children
                if (backingArray[index * 2].compareTo(
                        backingArray[index * 2 + 1]) < 0) {
                    // if left child smaller than left child
                    backingArray[index] = backingArray[index * 2];
                    backingArray[index * 2] = temp;
                    downHeap(index * 2);
                } else { // if right child is smaller than left child
                    backingArray[index] = backingArray[index * 2 + 1];
                    backingArray[index * 2 + 1] = temp;
                    downHeap(index * 2 + 1);
                }
            } else if (temp.compareTo(backingArray[index * 2])
                    > 0) { // else if parent larger than left child only
                backingArray[index] = backingArray[index * 2];
                backingArray[index * 2] = temp;
                downHeap(index * 2);
            } else if (temp.compareTo(backingArray[index * 2 + 1]) > 0) {
                // else if parent larger than right child only
                backingArray[index] = backingArray[index * 2 + 1];
                backingArray[index * 2 + 1] = temp;
                downHeap(index * 2 + 1);
            }
        } else if (index * 2 <= size) { // there is only left child
            if (temp.compareTo(backingArray[index * 2]) > 0) {
                // if parent larger than left child
                backingArray[index] = backingArray[index * 2];
                backingArray[index * 2] = temp;
                downHeap(index * 2);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        backingArray = (T[]) new Comparable[15];
        size = 0;
    }

    /**
     * Used for grading purposes only. Do not use or edit.
     * @return the backing array
     */
    public Comparable[] getBackingArray() {
        return backingArray;
    }

}
