import com.sun.jdi.InterfaceType;

import java.util.*;

public class SLL<T extends Comparable<T>> {
  private Node<T> head;
  private Node<T> tail;
  private int size = 0;

  public SLL(SLL list) {
      head = list.get(0);
      size = list.size();
      tail = list.get(size - 1);
  }

  public SLL() { }

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

    public void addHead(Node<T> n) {
        // The list is empty, so both the head and tail are made to be the first
        // node in the list.
        if (head == null) {
            head = n;
            tail = n;
            return;
        }

        // Update the head, but do not update the tail. The tail is already properly
        // set if there is at least one node in the list.
        Node<T> o = head;
        head = n;
        n.setNext(o);
        size++;
    }

    public void addTail(Node<T> n) {
        // The list is empty, so both the head and tail are made to be the first
        // node in the list.
        if (head == null) {
            head = n;
            tail = n;
        } else {
            // Update the tail of the list.
            tail.setNext(n);
            tail = n;
        }
        size++;
    }

    public void addInOrder(Node<T> n, Comparator<T> comparator) {
    // Case 1: empty/headless lists.
    if(head == null) {
      head = n;
      tail = n;
      size++;
      return;
    }

    // Case 2: a new head.
    if(comparator.compare(n.getData(), head.getData()) <= -1) { addHead(n); size++; return; }

    // Case 3: a new tail.
    if(comparator.compare(n.getData(), tail.getData()) >= 1) { addTail(n); size++; return; }

    // Case 4: a new interior element, to be added in order.
    Node<T> priorNode = head, currentNode = head;
    T nodeData = n.getData(); // Only retrieve the new node's data once.

    // Iterate through the nodes until the test returns -1, indicating the next
    // element is greater than this one.
    while(comparator.compare(nodeData, currentNode.getData()) >= 0) {
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

    public void addHead(T t) {
        Node<T> n = new Node<>(t);
        addHead(n);
    }

    public void addTail(T t) {
        Node<T> n = new Node<>(t);
        addTail(n);
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
  public Node<T> find(T key) {
    if(head == null)
        return null;

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
}
