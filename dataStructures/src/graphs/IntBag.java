/**
 * Bag of integers, implemented as a Linked List of Nodes with Integer values
 */
package graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Damien Pitman
 * 
 * Code modified from Algorithms, 4th ed., Hedgewick and Wayne
 */
public class IntBag implements Iterable<Integer> {
	
	private Node head; // current head/first Node in the list
	private int n; // size of the list (default 0)
	
	private class Node {
		int num; // sets the type of data type in Node to be int (default 0)
		Node next; // which Node is next (default null)
	}
	
	public IntBag() {
		head = null;
		n = 0;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	public void insert(int num) {
		// Insert new node with integer input to head of LinkedList
		Node oldhead = head; // point to old head of LinkedList
		head = new Node();
		head.num = num; // assign integer input to new head
		head.next = oldhead; // assign old head to be next for new head
		n++; // increment size
	}
	
	public boolean contains(int num) {
		Node current = head;
		while (current != null) {
			if (current.num == num) return true;
			current = current.next;
		}
		return false;
	}
	
	public Iterator<Integer> iterator() {
		return new IntIterator(head);
	}
	
	private class IntIterator implements Iterator<Integer> {
	    Node current;

	    public IntIterator(Node first) {
	        current = first;
	    }

		public boolean hasNext() { 
	    	return current != null;                 
	    }
	    
	    // an iterator, doesn't implement remove() since it's optional
		public void remove() { 
	    	throw new UnsupportedOperationException();  
	    }

		public Integer next() {
	        if (!hasNext()) throw new NoSuchElementException();
	        int x = current.num;
	        current = current.next;
	        return x;
	    }

	}

	public void deleteDups() {
		Node current = head;
		Node priortocompare = current;
		Node compare = current.next;
		int repeats = 0;
		while (!(compare == null)) {
			do {
				if (current.num == compare.num) {
					priortocompare.next = compare.next;
					repeats++;
				} else {
				priortocompare = compare;
				}
				compare = compare.next;
			}
			while(!(compare == null));
			current = current.next;
			priortocompare = current;
			compare = current.next;
		}
		n = n - repeats;
	}
	
	public void print() {
		// Prints each integer 
		Node tmpcurr = head;
		for (int i = 0; i < n; i++) {
			System.out.print(tmpcurr.num + ", ");
			tmpcurr = tmpcurr.next;
		}
		System.out.println();
	}

}
