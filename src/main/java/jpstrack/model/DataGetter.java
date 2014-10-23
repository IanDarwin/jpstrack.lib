package jpstrack.model;


/** The interface for getting data from the GPS.
 * Expect to see implementations for gpsd, gllin, etc.
 */
public interface DataGetter {
	public Reading getData();
}
