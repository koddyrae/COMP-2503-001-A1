// Only permit comparable types.
/**
 * A generic class representing a node in a singly-linked list
 *
 * @param <T> The type of data that this node holds, which must be comparable.
 */
public class Node<T extends Comparable<T>> {
    private T data = null;
    private Node<T> next;

    /**
     * Gets the data stored in this node.
     *
     * @return The data in this node.
     */
    public T getData() {
        return this.data;
    }
    /**
     * Gets the next node in the list.
     *
     * @return The next node in the list, or null if this is the last node.
     */
    public Node<T> getNext() {
        return this.next;
    }
    
    public void setNext(T t) {
        next = new Node<T>(t);
    }
    /**
     * Sets the next node in the list by providing an existing Node object.
     *
     * @param n The next node to set.
     */
    public void setNext(Node<T> n) {
        this.next = n;
    }
     /**
     * Constructs a new node with the specified data.
     *
     * @param t The data to be stored in this node.
     */
    public Node(T t) {
        this.data = t;
    }

    /**
     * Compares this node's data with another element of type 'T'.
     *
     * @param t The element to compare to.
     * @return A negative integer if this data is less than 't', zero if they are equal,
     *         or a positive integer if this data is greater than 't'.
     */
    public int compareTo(T t) {
        return data.compareTo(t);
    }
}
