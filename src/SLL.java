import java.util.EmptyStackException;

public class SLL<T extends Comparable<T>> implements Comparable<Node<T>> {
  private Node<T> head;
  private Node<T> tail;
  private int size = 0;

  public int size() {
	  return this.size;
  }

  public Node<T> get(int i) {
      if(size > 0) {
          Node<T> currentNode = head;
          for(int j = 0; j < i && j < size; j++) {
              currentNode = currentNode.getNext();
          }
          return currentNode;
      } else {
          return null;
      }
  }

  public void addHead(T t) {
    Node<T> n = new Node<>(t);
    // The list is empty, so both the head and tail are made to be the first
    // node in the list.
    if(head == null) { head = n; tail = n; return; }

    // Update the head, but do not update the tail. The tail is already properly
    // set if there is at least one node in the list.
    Node<T> o = head;
    head = n;
    n.setNext(o);
    size++;
  }

  public void addTail(T t) {
      Node<T> n = new Node<>(t);
    // The list is empty, so both the head and tail are made to be the first
    // node in the list.
    if(head == null) {
        head = n;
        tail = n;
    } else {
        // Update the tail of the list.
        tail.setNext(n);
        tail = n;
    }
    size++;
  }

  /**
   * To add nodes in order, they must be naturally sorted upon insertion.
   */
  public void addInOrder(T t) {
    Node<T> n = new Node<>(t);
    // Case 1: empty/headless lists.
    if(head == null) {
      head = n;
      tail = n;
      size++;
      return;
    }

    // Case 2: a new head.
    if(n.getData().compareTo(head.getData()) <= -1) { addHead(t); size++; return; }

    // Case 3: a new tail.
    if(n.getData().compareTo(tail.getData()) >= 1) { addTail(t); size++; return; }

    // Case 4: a new interior element, to be added in order.
    Node<T> priorNode = head, currentNode = head;
    T nodeData = n.getData(); // Only retrieve the new node's data once.

    // Iterate through the nodes until the test returns -1, indicating the next
    // element is greater than this one.
    while(nodeData.compareTo(currentNode.getData()) >= 0) {
      priorNode = currentNode;
      currentNode = currentNode.getNext();

      System.out.println("Current " + currentNode.toString());
    }

    // The insertion point has been located, so insert the new node before the
    // current one.
    priorNode.setNext(n);
    n.setNext(currentNode);
    size++;
  }

  public void printList() {
    System.out.println();
    Node<T> currentNode = head;
    while (currentNode != null) {
      // Visit the node. In this case, print it out.
      System.out.println(currentNode.toString());
      currentNode = currentNode.getNext();
    }
  }

  public void emptyList() {
    head = null; tail = null; size = 0;
  }

  /**
   * Find the first node in the list with the matching T key.
   */
  public Node<T> find(T key) throws IllegalStateException {
    if(head == null)
        throw new
            IllegalStateException("There's naught to be found in empty lists.");

    // Iterate through the nodes until either null is returned or the key is
    // found.
    Node<T> currentNode = head;

    while(currentNode.getNext() != null) {
      if(currentNode.getData().equals(key)) return currentNode;
      currentNode = currentNode.getNext();
    }

    // This code is only reached if there was no node found with KEY.
    return null;
  }

  public Node<T> delete(T key) {
    if(head == null) return null;

    // Case that the list is a single element, and that happens to be the key.
    if(head == tail && head.getData().equals(key)) {
      Node<T> returnMe = head;
      emptyList();
      return returnMe;
    }

    // All other cases. Iterate through the nodes until either null is returned
    // or the key is found.
    Node<T> priorNode = head, currentNode = head;
    while(currentNode.getNext() != null) {
      if(currentNode.getData().equals(key)) {
        priorNode.setNext(currentNode.getNext());
        // In the edge case that key was in the tail.
        if(priorNode.getNext() == null) tail = priorNode;
	size--;
        return currentNode;
      }

      priorNode = currentNode;
      currentNode = currentNode.getNext();
    }

    // This code is only reached if there was no node found with KEY.
    return null;
  }

    /*
     * Nodes are not very special, so a standard comparison of the data in the Node is
     * sufficient. Call the Node data's compareTo method.
     */
    @Override
    public int compareTo(Node n) {
        return n.getData().compareTo(n.getNext().getData());
    }
}
