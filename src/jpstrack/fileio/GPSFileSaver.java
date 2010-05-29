package jpstrack.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GPSFileSaver {
	
	private String startingDir = ".";
	
	private String template = "test,gpx";
	
	private PrintWriter out;
	
	public GPSFileSaver(String startingDir, String template) {
		this.startingDir = startingDir;
		this.template = template;
	}
	
	public GPSFileSaver() {
		// nothing, do setup via setters
	}
	
	public void startFile() {
		File f = new File(startingDir, template);
		System.out.println("GPSFileSaver.startFile(): file = " + f.getAbsolutePath());
		try {
			out = new PrintWriter(f);
			out.println("<?xml version='1.0' encoding='utf-8'?>");
			out.println("<gpx version='1.1' creator='GpsTrack java client'");
			out.println("	xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'");
			out.println("	xmlns='http://www.topografix.com/GPX/1.1'");
			out.println("	xsi:schemaLocation='http://www.topografix.com/GPS/1/1");
			out.println("						http://www.topografix.com/GPX/1/1/gpx.xsd'>");
			out.println("<metadata>");
			out.println("  <name>GpsTrack java client</name>");
			out.println("  <author>Ian Darwin (http://www.darwinsys.com/</author>");
			out.println("  <copyright>2-clause BSD License</copyright>");
			out.println("</metadata>");

			out.println("");

			out.println("<!-- track start -->");
			out.println("<trk>");
			out.println("<trkseg>");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Can't create file " + f, e);
		}
	}
	DateFormat df = 
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.00Z");
	Date date = new Date();
	
	public void write(long time, double latitude, double longitude) {
		out.printf("<trkpt lat='%f' lon='%f'>%n", latitude, longitude);
		out.printf("    <fix>3d</fix>%n");
		out.printf("    <ele>0</ele>%n");
		date.setTime(time);
		out.printf("    <time>%s</time>%n", df.format(date));
		out.printf("</trkpt>");
		out.flush();
	}
	
	public void endFile() {
		out.println("</trkseg>");
		out.println("</trk>");
		out.println("</gpx>");
		out.flush();
		out.close();
	}
	

	public String getStartingDir() {
		return startingDir;
	}
	public void setStartingDir(String startingDir) {
		this.startingDir = startingDir;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
