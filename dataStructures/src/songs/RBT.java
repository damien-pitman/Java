/**
 * Red-Black Tree Class from Key, Value pairs
 */
package songs;

import java.util.Iterator;

/**
 * @author Damien Pitman
 *
 * @param <Key>
 * @param <Value>
 */
public class RBT<Key extends Comparable<Key>, Value> {
	
	private Node root;
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	public RBT() {
	}
	
	private class Node {		
		Key key;
		Value val;
		Node left, right; 	// children - left lesser - right greater
		private int size; 	// number of nodes in tree
		boolean color; 		// of current node / parent link
		public Node(Key key, Value val, boolean color, int size) {
			this.key = key;
			this.val = val;
			this.size = size;
			this.color = color;
		}

	}
		
	private boolean isRed(Node x) {
		if (x == null) return false;
		return x.color == RED;
	}
	
	/**
	 * Returns the value associated with input key.
	 * Calls recursive private method get(Node, Key)
	 * @param key
	 * @return
	 */
	public Value get(Key key) {
		return get(root, key);
	}
	
	/**
	 * Returns value associated with key, passed to get(Key key).
	 * Recursively traverses RBT searching for key.
	 * 
	 * @param x
	 * @param key
	 * @return
	 */
	private Value get(Node x, Key key) {
		if (key == null) throw new IllegalArgumentException("called get() with null key");
		if (x == null) return null; 				// no value associated to key
		int cmp = key.compareTo(x.key);				// determines direction of traversal
		if (cmp < 0) return get(x.left, key); 		// recursive call to get from left subtree
		else if (cmp > 0) return get(x.right, key); // recursive call to get from right subtree
		else return x.val; 							// cmp == 0 => key found => return value
	}
	
	/**
	 * Puts key, val pair into RBT at appropriate place with appropriate color.
	 * Updates entire BST to be an RBT.
	 * Calls recursive private method Node = put(Node, Key, Value).
	 * @param key
	 * @param val
	 */
	public void put(Key key, Value val) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (val == null) throw new IllegalArgumentException("called get() with null value");
		/* TODO
		if (val == null) {
			delete(key); 
			return;
		}
		*/
		root = put(root, key, val);
		root.color = BLACK;		
		// TODO assert check();
	}
	
	/**
	 * Recursively searches RBT for leaf to input key, val pair.
	 * Then recursively updates all nodes to maintain RBT.
	 * @param h
	 * @param key
	 * @param val
	 * @return
	 */
	private Node put(Node h, Key key, Value val) { 
		if (h == null) return new Node(key, val, RED, 1); 	// found appropriate null leaf
		
		int cmp = key.compareTo(h.key); 					// determines direction of traversal
		if (cmp < 0) h.left = put(h.left, key, val);		// recursive call to left subtree
		else if (cmp > 0) h.right = put (h.right, key, val);// recursive call to right subtree
		else h.val = val;									// found key already in tree
		
		// Maintain RBT structure
		if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right)) flipColors(h);
		h.size = size(h.left) + size(h.right) + 1; 		// update size
		return h;
	} 

	
	/**
	 * Returns total number of nodes in RBT.
	 * @return
	 */
	public int size() {
		return root.size;
	}
	
	/**
	 * Returns number of nodes in RB subtree of Node x
	 * @param x
	 * @return
	 */
	public int size(Node x) {
		if (x == null) return 0; // null nodes have zero size (default)
		else return x.size; // otherwise each node has a size attribute
	}

	/**
	 * Updates Nodes to ultimately get RBT structure.
	 * Will only be called when Node h has RED right child.
	 * @param h
	 * @return
	 */
	private Node rotateLeft(Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}
	
	/**
	 * Updates Nodes to ultimately get RBT structure.
	 * Will only be called when Node h has RED left child and grandchild.
	 * @param h
	 * @return
	 */
	private Node rotateRight(Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}

	/**
	 * Updates Nodes to ultimately get RBT structure.
	 * Will only be called when parent is BLACK and both children are RED.
	 * @param h
	 */
	private void flipColors(Node h) {
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}

	/**
	 * Returns a Queue, which is iterable.
	 * Calls private recursive method keys(Node, Queue).
	 * @return
	 */
	public Queue<Key> keys() {
		Queue<Key> queue = new Queue<Key>();
		keys(root, queue);
		return queue;
	}
	
	/**
	 * Recursively enqueues Key of Node.
	 * @param x
	 * @param queue
	 */
	private void keys(Node x, Queue<Key> queue) {
		if (x == null) return;
		keys(x.left, queue);
		queue.enqueue(x.key);
		keys(x.right, queue);
	}
	
	/**
	 * Returns number of nodes in longest path of RBT.
	 * Call private recursive method height(Node).
	 * @return
	 */
	public int height() {
		return height(root);
	}

	/**
	 * Returns number of nodes in longest path from input Node x to null leaf.
	 * Includes Node x.
	 * @param x
	 * @return
	 */
	private int height(Node x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	private void delete(Key key) {
		// TODO Auto-generated method stub
		
	}
	
	// All code below is a work in progress.
	
	/*
	@Deprecated
    public Iterator<Key> iterator() {
        return rbt.keySet().iterator();
    }
    */

	public Queue<Key> levelOrder() {
		Queue<Key> keys = new Queue<Key>();
		Queue<Node> queue = new Queue<Node>();
		queue.enqueue(root);
		while (!queue.isEmpty()) {
			Node x = queue.dequeue();
			if (x == null) continue;
			else {
				keys.enqueue(x.key);
				queue.enqueue(x.left);
				queue.enqueue(x.right);
			}
		}
		return keys;
	}

	public Queue<Key> inOrder() {
		Queue<Key> queue = new Queue<Key>();
		inOrder(root, queue);
		return queue;
	}
	
	private void inOrder(Node x, Queue<Key> queue) {
		if (x == null) return;
		inOrder(x.left, queue);
		queue.enqueue(x.key);
		inOrder(x.right, queue);
	}
	
	public Queue<Key> preOrder() {
		Queue<Key> queue = new Queue<Key>();
		preOrder(root, queue);
		return queue;
	}
	
	private void preOrder(Node x, Queue<Key> queue) {
		if (x == null) return;
		queue.enqueue(x.key);
		preOrder(x.left, queue);
		preOrder(x.right, queue);
	}
	
	public Queue<Key> postOrder() {
		Queue<Key> queue = new Queue<Key>();
		postOrder(root, queue);
		return queue;
	}

	private void postOrder(Node x, Queue<Key> queue) {
		if (x == null) return;
		postOrder(x.left, queue);
		postOrder(x.right, queue);
		queue.enqueue(x.key);
	}
	
	public boolean isBalanced() {
		return isBalanced(root);
	}
	
	public boolean isBalanced(Node x) {
		if (x == null) {
			return true;
		} else if (Math.abs(height(x.left) - height(x.right))>1) {
			return false;
		} else {
		return (isBalanced(x.left) && isBalanced(x.right));
		}
	}
	
}
