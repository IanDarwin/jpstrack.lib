package jpstrack.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Standalone demo of the Uploader
 * @author Ian Darwin
 */
public class UploadDemo {

	private static final String UPLOAD_PROPERTIES_FILE = "upload.properties";

	private static final String DEMO_FILE_NAME = "testdata.gpx";
	
	private static final boolean debug = Upload.debug;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String description = "This is a test track";
		TraceVisibility visibility = TraceVisibility.IDENTIFIABLE;
		File gpxFile = new File(DEMO_FILE_NAME);
		if (!gpxFile.canRead()) {
			throw new IOException("Can't read file " + DEMO_FILE_NAME);
		}
		Properties p = new Properties();
		p.load(new FileInputStream(UPLOAD_PROPERTIES_FILE));
		final String encodedPostBody = Upload.encodePostBody(description, visibility, gpxFile);
		if (debug) {
			System.out.println("--- About to send this POST body: ---");
			System.out.println(encodedPostBody);
		}
		UploadResponse response = 
			Upload.converse(p.getProperty("hostname"), Integer.parseInt(p.getProperty("port", "80")), 
				p.getProperty("userName"), p.getProperty("password"),
				encodedPostBody);

		System.out.println("Server responded thus: " + response);
	}


}
