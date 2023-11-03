// Only permit comparable types.
public class Node<T extends Comparable<T>> {
    private T data = null;
    private Node<T> next;

    public T getData() {
        return this.data;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public void setNext(T t) {
        next = new Node<T>(t);
    }

    public void setNext(Node<T> n) {
        this.next = n;
    }

    public Node(T t) {
        this.data = t;
    }

    public int compareTo(T t) {
        return data.compareTo(t);
    }
}
