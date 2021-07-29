/**
 * Song objects hold information about songs.
 * Song objects are Comparable by year, album, time (seconds).
 */
package songs;

/**
 * @author Damien Pitman
 */

public class Song implements Comparable<Song> {
	private String name;
	private String time;
	private int seconds;
	private String album;
	private String year;
	
	/**
	 * Constructs Song object from String inputs
	 * @param name
	 * @param time
	 * @param album
	 * @param year
	 * The int seconds attribute is derived from String time
	 */
	
	public Song(String name, String time, String album, String year) {
		this.name = name;
		this.time = time;
		String[] str = time.split(":");
		int x = Integer.parseInt(str[0]);
		int y = Integer.parseInt(str[1]);
		seconds = 60*x+y;
		this.seconds = seconds;
		this.album = album;
		this.year = year;
	}
	
	public String getName() {
		return name;
	}

	public String getTime() {
		return time;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public String getYear() {
		return year;
	}
	
	@Override
	public int compareTo(Song song) {					// get Comparable objects 
		int first = this.year.compareTo(song.year);		// first compare by year
		int second = this.album.compareTo(song.album);	// second compare by album
		if (first == 0) {								// years are identical
			if (second == 0) {							// and albums are identical
				return this.seconds - song.seconds;		// third compare by seconds (song length)
			} else {
				return second;
			}
		} else {
			return first;
		}
	}
}
