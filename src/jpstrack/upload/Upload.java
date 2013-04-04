package jpstrack.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import com.darwinsys.io.FileIO;
import com.sun.xml.internal.messaging.saaj.util.Base64;


public class Upload {

	private static final String API_CREATE_URL = "/api/0.6/gpx/create";
	private static final String FILENAME = "testdata.gpx";
	private final static String BOUNDARY = "OSM_TRACK_FILE_42_GNORNMPLATZ";
	private final static boolean debug = false;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String description = "This is a test track";
		TraceVisibility visibility = TraceVisibility.IDENTIFIABLE;
		File gpxFile = new File(FILENAME);
		if (!gpxFile.canRead()) {
			throw new IOException("Can't read file " + FILENAME);
		}
		Properties p = new Properties();
		p.load(new FileInputStream("user.properties"));
		int port = Integer.parseInt(p.getProperty("port", "80"));
		String response = converse(p.getProperty("hostname"), port, 
				API_CREATE_URL,
				p.getProperty("userName"), p.getProperty("password"),
				encodePostBody(description, visibility, gpxFile));

		System.out.println("Server responded thus: " + response);
	}

	/** Handle the HTTP POST conversation */
	public static String converse(String host, int port, String path, String userName, String password, String postBody) throws IOException {
		URL url = new URL("http", host, port, path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept", "application/xml");
		final String auth = "Basic " + new String(Base64.encode((userName + ":" + password).getBytes()));
		conn.setRequestProperty("Authorization", auth);
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		if (debug) {
			System.out.println(conn.getRequestProperties());
		}
		conn.setDoInput(true);	// we want POST!
		conn.setDoOutput(true);
		conn.setAllowUserInteraction(true);

		conn.connect();

		final OutputStream outputStream = conn.getOutputStream();
		outputStream.write(postBody.getBytes());
		outputStream.close();

		StringBuilder sb = new StringBuilder();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						conn.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}

	/** Get the GPX file and other parameters into shape for POSTing */
	public static String encodePostBody(String description, TraceVisibility visibility, File gpxFile) throws IOException {
		StringBuffer body = new StringBuffer();
		body.append(encodePlainTextPart("description", description, true));
		body.append(encodePlainTextPart("visibility", visibility.toString().toLowerCase(), false));

		body.append("\r\n--" + BOUNDARY + "\r\n");
		body.append("Content-Disposition: form-data; name=\"" + gpxFile.getName() + "\"\r\n");
		body.append("Content-Type: application/xml\r\n");
		body.append("Content-Transfer-Encoding: text\r\n\r\n");

		body.append(FileIO.readerToString(new FileReader(gpxFile)));

		body.append("\r\n--" + BOUNDARY + "--\r\n");
		return body.toString();
	}

	private static String encodePlainTextPart(String partName, String partBody, boolean first) {
		return (first? "" : "\r\n") + "--" + BOUNDARY + "\r\n"
				+ "Content-Disposition: form-data; name=\"" + partName + "\"\r\n\r\n" 
				+ partBody;
	}
}
