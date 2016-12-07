/**
 * Your implementation of a CircularDoublyLinkedList
 *
 * @author Ziyu Zhang
 * @version 1.0
 */

import java.util.NoSuchElementException;

public class CircularDoublyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Creates an empty circular doubly-linked list.
     */
    public CircularDoublyLinkedList() {
        head = null;
    }

    /**
     * Creates a circular doubly-linked list with
     * {@code data} added to the list in order.
     * @param data The data to be added to the LinkedList.
     * @throws IllegalArgumentException if {@code data} is null or any
     * item in {@code data} is null.
     */
    public CircularDoublyLinkedList(T[] data) {
        super();
        if (data == null) {
            throw new IllegalArgumentException("Data cannot"
                    + "be null!");
        } else if (data.length != 0) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    addToBack(data[i]);
                } else {
                    throw new IllegalArgumentException("Data cannot"
                            + "be null!");
                }
            }
        }

    }

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is in"
                    + "the wrong range!");
        } else if (data == null) {
            throw new IllegalArgumentException("Data cannot"
                    + "be null!");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> current = head;
            LinkedListNode<T> prev = null;
            int counter = 0;
            while (counter < index) {
                prev = current;
                current = current.getNext();
                counter++;
            }
            LinkedListNode<T> newNode = new LinkedListNode<T>(
                    data, prev, current);
            current.setPrevious(newNode);
            prev.setNext(newNode);
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not"
                    + "in the correct range!");
        } else if (index == size - 1) {
            return head.getPrevious().getData();
        } else {
            int counter = 0;
            LinkedListNode<T> current = head;
            while (counter < index) {
                current = current.getNext();
                counter++;
            }
            return current.getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not"
                    + "in the correct range!");
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            int counter = 0;
            LinkedListNode<T> current = head;
            while (counter < index) {
                current = current.getNext();
                counter++;
            }
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
            size--;
            return current.getData();
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data"
                    + "cannot be null!");
        } else if (size == 0) {
            head = new LinkedListNode<T>(data, null, null);
            head.setNext(head);
            head.setPrevious(head);
            size++;
        } else {
            LinkedListNode<T> temp = head;
            head = new LinkedListNode<T>(data, temp.getPrevious(), temp);
            temp.setPrevious(head);
            head.getPrevious().setNext(head);
            size++;
        }
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data"
                    + "cannot be null!");
        } else if (isEmpty()) {
            addToFront(data);
        } else {
            LinkedListNode<T> prev = head.getPrevious();
            LinkedListNode<T> newNode = new LinkedListNode<T>(
                    data, prev, head);
            prev.setNext(newNode);
            head.setPrevious(newNode);
            size++;
        }
    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        } else if (size == 1) {
            T data = head.getData();
            head = null;
            size--;
            return data;
        } else {
            T data = head.getData();
            head.getPrevious().setNext(head.getNext());
            head.getNext().setPrevious(head.getPrevious());
            head = head.getNext();
            size--;
            return data;
        }
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        } else if (size == 1) {
            T data = head.getData();
            head = null;
            size--;
            return data;

        } else {
            T data = head.getPrevious().getData();
            head.getPrevious().getPrevious().setNext(head);
            head.setPrevious(head.getPrevious().getPrevious());
            size--;
            return data;
        }
    }

    @Override
    public int removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        int counter = 0;
        if (head.getData() == data) {
            removeFromFront();
            return 0;
        }
        LinkedListNode<T> current = head;
        while (counter < size) {
            if (current.getData() == data) {
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                size--;
                return counter;
            } else {
                current = current.getNext();
                counter++;
            }
        }
        throw new NoSuchElementException("There is no such element!");
    }

    @Override
    public boolean removeAllOccurrences(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        boolean answer = false;
        int counter = 0;
        int tempSize = size;
        while (counter < tempSize) {
            try {
                removeFirstOccurrence(data);
                answer = true;
                counter++;
            } catch (NoSuchElementException e) {
                break;
            }
        }
        return answer;
    }

    @Override
    public Object[] toArray() {
        if (isEmpty()) {
            return new Object[0];
        } else {
            Object[] answer = new Object[size];
            LinkedListNode<T> current = head;
            for (int i = 0; i < size; i++) {
                answer[i] = current.getData();
                current = current.getNext();
            }
            return answer;
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
        head = null;
        size = 0;
    }

    /* DO NOT MODIFY THIS METHOD */
    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }
}
