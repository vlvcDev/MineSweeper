/*
 * CS2050 - Computer Science II - Fall 2022
 * Instructor: Thyago Mota
 * Description: prg_02 - LinkedList
 */

import java.util.Iterator;

public class LinkedList<E> implements Iterable<E> {

    // NOTE: changed from private to protected
    protected Node<E> head;

    public LinkedList() {
        head = null;
    }

    // TODOd: return true/false depending whether the list is empty or not
    public boolean isEmpty() {
        return head == null;
    }

    // TODOd: add a new element (with the value) in front of the list!
    public void add(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.setNext(head);
        head = newNode;
    }

    // TODOd: add a new element (with the value) at the end of the list!
    public void append(E value) {
        Node<E> newNode = new Node<>(value);
        if (isEmpty())
            head = newNode;
        else {
            Node<E> current = head;
            while (current.getNext() != null)
                current = current.getNext();
            // at this point, what can we tell about current?
            // for sure, current points to the tail node!!!
            current.setNext(newNode);
        }
    }

    // TODOd: returns the # of elements
    public int size() {
        Node<E> current = head;
        int size = 0;
        while (current != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

    // TODOd: return a string representation of the object
    @Override
    public String toString() {
        Node<E> current = head;
        String s = "";
        while (current != null) {
            s += current + " ";
            current = current.getNext();
        }
        return s.trim();
    }

    // TODOd: return the element at index location
    public E get(int index) {
        // check if index is valid 1st
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        Node<E> current = head;
        for (int i = 0; i < index; i++)
            current = current.getNext();
        return current.getValue();
    }

    // TODOd: sets value to location at index
    public void set(int index, E value) {
        // check if index is valid 1st
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        Node<E> current = head;
        for (int i = 0; i < index; i++)
            current = current.getNext();
        current.setValue(value);
    }

    // TODOd: inserts value at the given index location
    // throw an exception if index is invalid
    public void insert(int index, E value) {
        // check if index is valid 1st
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        // special case: index is zero
        if (index == 0)
            add(value);
        else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();
            Node<E> newNode = new Node<>(value);
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
    }

    // TODOd: removes the element at the given index location
    // throw an exception if index is invalid
    public void remove(int index) {
        // check if index is valid 1st
        if (index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        // special case: index is zero
        if (index == 0) {
            Node<E> toBeRemoved = head;
            head = head.getNext();
            toBeRemoved.setNext(null);
        }
        else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();
            Node<E> toBeRemoved = current.getNext();
            current.setNext(toBeRemoved.getNext());
            toBeRemoved.setNext(null);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    E value = current.getValue();
                    current = current.getNext();
                    return value;
                }
                return null;
            }
        };
    }
}