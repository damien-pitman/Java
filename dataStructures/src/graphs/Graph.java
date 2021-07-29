/**
 * Graph objects implemented with Strings identifying vertices
 */
package graphs;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Damien Pitman
 * 
 * Code modified from Algorithms, 4th ed., Hedgewick and Wayne
 * https://algs4.cs.princeton.edu/41graph/Graph.java.html
 */
public class Graph {	
	private final int V; 				// number of vertices
	private int E;						// number of edges
	private ArrayList<String> states;	// names of vertices
	private IntBag[] adj;				// holds indices of sates adjacent to state w/ array index
	private int[] degrees;
	private IntBag[] degreebags;
	
	/**
	 * Constructs an empty graph with
	 * @param V number of desired vertices
	 */
	public Graph(int V) {				
		if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		states = (ArrayList<String>) new ArrayList<String>(V);
		adj = (IntBag[]) new IntBag[V];
		degreebags = (IntBag[]) new IntBag[V];
		degrees = new int[V];
		for (int v = 0; v < V; v++) {
	    	adj[v] = new IntBag();
	    	degreebags[v] = new IntBag();
	    }
	}	
	
	/**
	 * Constructs a Graph object from a file that is made into a Scanner
	 * @param in - comes from a file with a specific structure
	 * 
	 * V
	 * E
	 * name1 neighbor1
	 * name2 neighbor2
	 * .
	 * .
	 * .
	 * name 2E neighbor 2E
	 * 
	 * First line is number of vertices.
	 * Second line is number of edges.
	 * Next 2E lines are edges listed in each direction.
	 */
	public Graph(Scanner in) {
		if (in == null) throw new IllegalArgumentException("in is null");
		try {
        	V = in.nextInt();
    		states = (ArrayList<String>) new ArrayList<String>(V);
    		adj = (IntBag[]) new IntBag[V];
    		degreebags = (IntBag[]) new IntBag[V];
    		degrees = new int[V];
    		for (int v = 0; v < V; v++) {
    	    	adj[v] = new IntBag();
    	    	degreebags[v] = new IntBag();
    	    }
    		int edgelines = in.nextInt();
    		for (int e = 0; e < edgelines; e++) {
    			String state1 = in.next();		// left name (from vertex)
    			if (!states.contains(state1)) { // not already added to ArrayList states
        			states.add(state1);			// add it
    			}
    			String state2 = in.next();		// right name (to vertex)
    			if (!states.contains(state2)) {
        			states.add(state2);
    			}
    			// an edge is added for each line of input 
    			// may seem like a directed edge and that we are double adding edges
    			// see addEdge comments
            	addEdge(states.indexOf(state1), states.indexOf(state2)); 
        	}															
    		for (int v = 0; v < V; v++) {
    			degrees[v] = adj[v].size();
    			degreebags[degrees[v]].insert(v);
    		}
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Input file must conform to graph input structure.", e);
		}
	}
	
	/**
	 * Creates a copy of an already formed
	 * @param G - Graph object
	 */
	public Graph(Graph G) {
		V = G.V();
		E = G.E();
		states = (ArrayList<String>) new ArrayList<String>(V);
		adj = (IntBag[]) new IntBag[V];
		degrees = new int[V];
		for (int v = 0; v < V; v++) {
	    	adj[v] = new IntBag();
	    }
		for (int v = 0; v < V; v++) {
			for (int w : G.adj[v]) {
				adj[v].insert(w);
			}
		}
		for (int v = 0; v < V; v++) {
			degrees[v] = adj[v].size();
			degreebags[degrees[v]].insert(v);
		}															
	}
	
	/**
	 * Adds an edge from u to v (not from v to u).
	 * @param u is the index in the array of Bags
	 * @param v is the index of the vertex to be added to u's Bag
	 * Note: Each edge in this undirected Graph structure is listed twice.
	 * 	name1 name2 adds v = indexOf(name2) to Bag of u = indexOf(name1)
	 * 	name2 name1 adds v = indexOf(name1) to Bag of u = indexOf(name2)
	 *  together, these lines create a double (undirected) edge
	 */
	public void addEdge(int u, int v) {
		adj[u].insert(v); // inserts v into u's adjacency list
		// adj[v].insert(u); // this would be a mistake since graph input lists edges in both directions
		if (!adj[v].contains(u)) E++;
	}
	
	/**
	 * @return the number of vertices in Graph
	 */
	public int V() {
		return V;  
	}
	
	/**
	 * @return the number of edges in Graph
	 */
	public int E() {
		return E;
	}
	
	/**
	 * @param v
	 * @return the degree of vertex v
	 */
	public int degree(int v) {
		return degrees[v];
	}
	
	public IntBag vertsDegree(int d) {
		return degreebags[d];
	}
		
	/**
	 * @param v the vertex (index) whose adjacency list we desire
	 * @return Iterable object of adjacent vertices...
	 * 	the indices of the strings in states[i]
	 */
	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

	/**
	 * @param state - the name of the vertex
	 * @return the index of the vertex in ArrayList states
	 */
	public int index(String state) {
		return states.indexOf(state);
	}
	
	/**
	 * @param v - vertex (index) in Graph
	 * @return
	 */
	public String state(int v) {
		return states.get(v);
	}
	
	/**
	 * Prints the essential Graph structure. 
	 */
	public void print() {
		System.out.printf("%20s: %s%n", "Number of vertices", V);
		System.out.printf("%20s: %s%n", "Number of edges", E);
		for (String state: states) {
			System.out.printf("%20s: ", state);
			for (int i: adj[states.indexOf(state)]) {
				System.out.printf("%15s",states.get(i));
			}
			System.out.println();
		}
	}
	
}
