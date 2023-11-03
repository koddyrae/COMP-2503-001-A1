import java.util.*;

public class SLL<T extends Comparable<T>> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public int size() {
        return this.size;
    }

    public Node<T> get(int i) {
        if ((i < 0) || (i > (this.size - 1))) {
            throw new IndexOutOfBoundsException("Index i is outside of legal bounds for the singly-linked list.");
        }

        Node<T> currentNode = head;
        while(i > 0) {
            currentNode = currentNode.getNext();
            --i;
        }
        return currentNode;
    }

    public void addHead(T t) {
        size++;

        Node<T> n = new Node<>(t);

        // The list is empty, so both the head and tail are made to be the first
        // node in the list.
        if (head == null) {
            head = n;
            tail = n;
        } else {
            // Update the head, but do not update the tail. The tail is already properly
            // set if there is at least one node in the list.
            n.setNext(head);
            head = n;
        }
    }

    public void addTail(T t) {
        size++;

        // The list is empty, so both the head and tail are made to be the first
        // node in the list.
        if (head == null) {
            head = new Node<T>(t);
        } else {
            // Update the tail of the list.
            tail.setNext(new Node<T>(t));
        }
        tail = tail.getNext();
    }

    public void addInOrder(Node<T> n, Comparator<T> comparator) {
        // Case 1: empty/headless lists.
        // The calculation is size less one because size was just incremented.
        if (size == 0) {
            addHead(n.getData());
            return;
        }

        // Case 2: a new head.
        if (comparator.compare(n.getData(), head.getData()) <= -1) {
            addHead(n.getData());
            return;
        }

        // Case 3: a new tail.
        if (comparator.compare(n.getData(), tail.getData()) >= 1) {
            addTail(n.getData());
            return;
        }

        // Case 4: a new interior element, to be added in order.
        Node<T> priorNode = head, currentNode = head;
        T nodeData = n.getData(); // Only retrieve the new node's data once.

        // Iterate through the nodes until the test returns -1, indicating the next
        // element is greater than this one.
        while (comparator.compare(nodeData, currentNode.getData()) >= 0) {
            priorNode = currentNode;
            currentNode = currentNode.getNext();
        }

        // The insertion point has been located, so insert the new node before the
        // current one.
        priorNode.setNext(new Node<>(n.getData()));
        priorNode.getNext().setNext(currentNode);
        size++;
    }
}
