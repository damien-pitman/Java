/**
 * Reads file with specific graph structure of U.S. States. 
 * Creates Graph object based on geographic adjacency of states. 
 * Prints list of states grouped by number of adjacent states in decreasing order.
 */
package graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Damien Pitman
 */
public class Driver {

	public static void main(String[] args) {		
		Scanner in = null;
		try {
			File file = new File("src/graphs/in5-2.txt"); 
        	in = new Scanner(file); 						
        	Graph G = new Graph(in);						
    		
    		for (int d = G.V()-1; d >= 0; d--) {  
    			if(!G.vertsDegree(d).isEmpty()) {
    				System.out.println("States with " + d + " Neighbor(s)");
    				for (int x: G.vertsDegree(d)) {
    					System.out.println("    " + G.state(x));
    				}
    	    		System.out.println();
    			}
    		}
    		System.out.println();
    		
    		
		} catch (FileNotFoundException ex) {
			System.out.println(ex);
		} finally {
			if(in != null) in.close();
		}
	}
}
