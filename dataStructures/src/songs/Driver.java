/**
 * Driver to create a Red-Black Tree from a specific file.
 * keys are Song objects.
 * values are Songs' attributes, String name.
 * Other Song attributes are String year, String album, String time, int seconds
 * Song objects are Comparable by year, album, seconds.
 * Songs from the Red-Black Tree are printed in Song order.
 */
package songs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Damien Pitman
 */
public class Driver {

	public static void main(String[] args) throws IOException {
		RBT<Song, String> rbt = new RBT<Song, String>(); // Initialize Red-Black Tree of Nodes 
		
		BufferedReader reader = null;	// Usual Scanner method did not work. Found a solution at
						// https://www.journaldev.com/2335/read-csv-file-java-scanner#:~:text=
						//  If%20you%20look%20into%20Scanner,CSV%20file%20using%20Scanner%20only.
		String line = null;
		Scanner in = null;
		String name = null;
		String time = null;
		String album = null;
		String year = null;
		// Read and clean songs.csv
		try {
			reader = new BufferedReader(new FileReader("src/songs/songs.csv"));
        	while ((line = reader.readLine()) != null) {
            	in = new Scanner(line.replaceAll("\"\"\"", "\"")); 	
            	in.useDelimiter(","); 				
            	while (in.hasNext()) {
            		name = in.next(); 				
            		time = in.next();
            		album = in.next();
            		year = in.next();
            	}
        		Song song = new Song(name, time, album, year);	
                rbt.put(song, song.getName()); 	
            }
        } catch (FileNotFoundException ex) {
        	System.out.println(ex);
        } finally {
    		if(in != null) in.close();
    	}

		// Print songs from songs.csv in Red-Black Tree Song order
		System.out.printf("%32s%6s%70s%6s%n", "Song Name", "Year", "Album", "Time");
		for (Song s: rbt.keys()) {	
			System.out.printf("%32s%6s%70s%6s%n", s.getName(), s.getYear(), s.getAlbum(),s.getTime()); 
		}	
	}
}
