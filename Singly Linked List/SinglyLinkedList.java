/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Ziyu Zhang
 * @version 1.0
 */

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data"
                    + " in the node cannot be null!");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index should"
                    + " be non-negative,"
                    + "and is less than the size of the list!");
        } else {
            if (index == 0) {
                addToFront(data);
            } else {
                int counter = 0;
                LinkedListNode<T> nodeBefore = null;
                LinkedListNode<T> indexNode = getHead();
                while (counter < index) {
                    nodeBefore = indexNode;
                    indexNode = indexNode.getNext();
                    counter++;
                }
                nodeBefore.setNext(new LinkedListNode<T>(data, indexNode));
                size++;
            }
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index "
                    + "should be non-negative,"
                    + "and is less than the size of the list!");
        } else {
            int counter = 0;
            LinkedListNode<T> temp = getHead();
            while (counter < index) {
                temp = temp.getNext();
                counter++;
            }
            return temp.getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should"
                    + " be non-negative,"
                    + "and is less than the size of the list!");
        } else {
            if (index == 0) {
                return removeFromFront();
            } else if (index == size - 1) {
                return removeFromBack();
            } else {
                int counter = 0;
                LinkedListNode<T> nodeBefore = null;
                LinkedListNode<T> indexNode = getHead();
                while (counter < index) {
                    nodeBefore = indexNode;
                    indexNode = indexNode.getNext();
                    counter++;
                }
                nodeBefore.setNext(indexNode.getNext());
                size--;
                return indexNode.getData();
            }
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data in"
                    + " the node cannot be null!");
        } else {
            head = new LinkedListNode<T>(data, head);
            size++;
        }
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data in "
                    + "the node cannot be null!");
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<>(data);
            if (isEmpty()) {
                head = newNode;
                size++;
            } else {
                LinkedListNode<T> temp = getHead();
                while (temp.getNext() != null) {
                    temp = temp.getNext();
                }
                temp.setNext(newNode);
                size++;
            }
        }
    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        } else {
            T data = head.getData();
            head = head.getNext();
            size--;
            return data;
        }
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        } else {
            if (size == 1) {
                return removeFromFront();
            } else {
                LinkedListNode<T> nodeBefore = getHead();
                while (nodeBefore.getNext().getNext() != null) {
                    nodeBefore = nodeBefore.getNext();
                }
                T data = nodeBefore.getNext().getData();
                nodeBefore.setNext(null);
                size--;
                return data;
            }
        }
    }

    @Override
    public int removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null!");
        } else {
            LinkedListNode<T> temp = getHead();
            LinkedListNode<T> before = null;
            int index = 0;
            while (temp != null) {
                if (temp.getData() == data) {
                    if (index == 0) {
                        removeFromFront();
                    } else {
                        before.setNext(temp.getNext());
                        size--;
                    }
                    return index;
                } else {
                    before = temp;
                    temp = temp.getNext();
                    index++;
                }
            }
            throw new NoSuchElementException("The data"
                    + "doesn't exist in the linked list.");
        }
    }

    @Override
    public Object[] toArray() {
        if (isEmpty()) {
            return new Object[0];
        } else {
            Object[] array = new Object[size];
            LinkedListNode<T> temp = head;
            int counter = 0;
            while (temp.getNext() != null) {
                array[counter] = temp.getData();
                temp = temp.getNext();
                counter++;
            }
            array[counter] = temp.getData();
            return array;
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

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}
