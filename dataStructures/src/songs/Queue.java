/**
 * Queue objects have First In First Out (FIFO) structure.
 * Queue objects are iterable.
 */
package songs;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Damien Pitman
 *
 * @param <Item>
 */
public class Queue<Item> implements Iterable<Item>{
	
	private Node front; 		// Node to be first out
	private Node back; 			// Node to be last out
	private int n; 				// size of the Queue (default 0)
	
	public Queue() {
	}
	
	private class Node {
		private Item item; 		// Inherits Item type from Queue<Item>
		private Node next; 		// which Node is next 
		
		/**
		 * Constructs a Node with input
		 * @param item
		 * @param next
		 */
		public Node(Item item, Node next) {
			this.item = item;
			this.next = next;
		}
		
		public Item get() {
			return this.item;
		}

		public Node getNext() {
			return this.next;
		}

		public void set(Item item) {
			this.item = item;
		}
		
		public void setNext(Node next) {
			this.next = next;
		}
		
	}
	
	public Node getBack() {
		return back;
	}

	public void enqueue(Item item) { 	// place new node at back of the queue
		Node oldback = back; 			// initialize holder for old back of Queue
		back = new Node(item, null); 	// initialize new back of Queue
		if (isEmpty()) front = back;	// only one node, so front is back
		else oldback.next = back;	 	// update the pointer from the old back to be the new back
		n++; 							// increment size
	}
	
	public Item dequeue() {				// remove node from front of queue and return its Item
		Item item = front.item;	 		// get front Item
		front = front.next;		 		// assign second Node to front position
		if (isEmpty()) { 				// no one left at front of Queue
			back = null;				// so the Queue is null
		}
		n--; 							// decrement size
		return item;
	}
	
	public boolean isEmpty() {
		return front == null;
	}

	/**
     *
     * @return an iterator. The FIFO order is determined by enqueue() and next()
     */
	public Iterator<Item> iterator()  {
        return new ListIterator(front);  
    }

	private class ListIterator implements Iterator<Item> {
	    Node current;

	    public ListIterator(Node front) {
	        current = front;
	    }

		public boolean hasNext() { 
	    	return current != null;                 
	    }
	    
	    // an iterator, doesn't implement remove() since it's optional
		public void remove() { 
	    	throw new UnsupportedOperationException();  
	    }

		public Item next() {
	        if (!hasNext()) throw new NoSuchElementException();
	        Item item = current.item;
	        current = current.next;
	        return item;
	    }

	}

}

