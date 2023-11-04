/*
 * CS2050 - Computer Science II - Fall 2022
 * Instructor: Thyago Mota
 * Description: prg_02 - Node
 */

public class Node<E> {

    private E  value;
    private Node<E> next;

    public Node(E value) {
        this.value = value;
        next = null;
    }

    public E getValue() {
        return value;
    }

    public void setValue(final E value) {
        this.value = value;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(final Node<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return value + "";
    }
}