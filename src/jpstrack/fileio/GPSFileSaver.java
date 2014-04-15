package jpstrack.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jpstrack.model.Reading;

public class GPSFileSaver {
	
	private File startingDir;
	
	private String fileName = "test.gpx";
	
	private PrintWriter out;
	
	public GPSFileSaver(String startingDir, String fileName) {
		this.startingDir = new File(startingDir);
		this.fileName = fileName;
	}
	
	public GPSFileSaver(File startingDir, String fileName) {
		this.startingDir = startingDir;
		this.fileName = fileName;
	}
	
	public GPSFileSaver() {
		// nothing, do setup via setters
	}
	
	public File startFile() {
		if (startingDir == null || fileName == null) {
			throw new IllegalStateException("Must set startDir and fileName before call startFile()");
		}
		File f = new File(startingDir, fileName);
		try {
			out = new PrintWriter(f);
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>");
			out.println("<gpx version=\"1.1\"");
			out.println("    creator=\"GpsTrack java client\"");
			out.println("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
			out.println("    xmlns='http://www.topografix.com/GPX/1/1'");
			out.println("    xsi:schemaLocation='http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd'>");
			out.println("  <metadata>");
			out.println("    <name>jpstrack GPS Receiver track log</name>");
			out.println("    <time>" + df.format(new Date()) + "</time>");
			out.println("  </metadata>");

			out.println("");

			out.println("<!-- track start -->");
			out.println("<trk>");
			out.println("<src>Logged using jpstrack</src>");
			out.println("<link href=\"http://darwinsys.com/jpstrack\"><text>RejmiNet Group Inc.</text></link>");
			out.println("<trkseg>");
			return f;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Can't create file " + f, e);
		}
	}
	DateFormat df = 
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.00Z");
	Date date = new Date();
	
	public void write(long time, double latitude, double longitude) {
		out.printf("<trkpt lat='%f' lon='%f'>%n", latitude, longitude);
		out.printf("    <ele>0</ele>%n");
		date.setTime(time);
		out.printf("    <time>%s</time>%n", df.format(date));
		out.printf("    <fix>3d</fix>%n");
		out.printf("</trkpt>%n");
		out.flush();
	}
	
	public void write(Reading r) {
		write(r.getTime(), r.getLatitude(), r.getLongitude());
	}
	
	/** Close the file, after outputting the trailing end tags */
	public void close() {
		out.println("</trkseg>");
		out.println("</trk>");
		out.println("</gpx>");
		out.flush();
		out.close();
	}
	
	/** Historical name for close functionality */
	public void endFile() {
		close();
	}
	
	public File getStartingDir() {
		return startingDir;
	}
	public void setStartingDir(File startingDir) {
		this.startingDir = startingDir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getFile() {
		return new File(startingDir, fileName);
	}
}
