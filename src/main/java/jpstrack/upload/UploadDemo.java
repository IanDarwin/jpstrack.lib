package jpstrack.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import jpstrack.net.NetResult;

/**
 * Standalone demo of the Uploader
 * @author Ian Darwin
 */
public class UploadDemo {

	private static final String UPLOAD_PROPERTIES_FILE = "upload.properties";

	private static final String DEMO_FILE_NAME = "testdata.gpx";
	
	private static final boolean debug = Upload.debug;

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
		final String hostName = p.getProperty("hostname");
		if (hostName == null) {
			throw new IllegalArgumentException("hostname in properties must not be null");
		}
		final String userName = p.getProperty("userName");
		final String password = p.getProperty("password");
		if (userName == null || password == null) {
			throw new IllegalArgumentException("username/password in properties must not be null");
		}
		NetResult<String> response = 
			Upload.converse(hostName, Integer.parseInt(p.getProperty("port", "80")), 
				userName, password,
				encodedPostBody);

		System.out.println("Server responded thus: " + response);
	}

}
