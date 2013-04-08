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

/**
 * Support for uploading GPX Trace files to OSM.
 * Refer to http://www.w3.org/TR/html401/interact/forms.html#h-17.13.4.2
 * @author Ian Darwin
 */
public class Upload {

	private static final String API_CREATE_URL = "/api/0.6/gpx/create";
	private static final String FILENAME = "testdata.gpx";
	private final static String BOUNDARY = "ANOTHER_GREAT_OSM_TRACE_FILE";
	private static boolean debug = true;

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
		final String encodedPostBody = encodePostBody(description, visibility, gpxFile);
		if (debug) {
			System.out.println("--- About to send this POST body: ---");
			System.out.println(encodedPostBody);
		}
		UploadResponse response = 
			converse(p.getProperty("hostname"), Integer.parseInt(p.getProperty("port", "80")), 
				API_CREATE_URL,
				p.getProperty("userName"), p.getProperty("password"),
				encodedPostBody);

		System.out.println("Server responded thus: " + response);
	}

	/** Handle the HTTP POST conversation */
	public static UploadResponse converse(String host, int port, String path, String userName, String password, String postBody) throws IOException {
		UploadResponse ret = new UploadResponse();
		URL url = new URL("http", host, port, path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		final String auth = "Basic " + new String(Base64.encodeBytes((userName + ":" + password).getBytes()));
		conn.setRequestProperty("Authorization", auth);
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		if (debug) {
			System.out.println("--- About to send POST with these Request Headers: ---");
			for (String s : conn.getRequestProperties().keySet()) {
				System.out.println(s + ": " + conn.getRequestProperty(s));
			}
		}
		conn.setDoInput(true);	// we want POST!
		conn.setDoOutput(true);
		conn.setAllowUserInteraction(true);

		conn.connect();
		
		final OutputStream outputStream = conn.getOutputStream();
		outputStream.write(postBody.getBytes());
		outputStream.close();

		ret.status = conn.getResponseCode();
		
		// The response from this REST is a single line with the GPX ID
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						conn.getInputStream()));
		String line = null;
		if ((line = in.readLine()) != null) {
			ret.gpxId = Long.parseLong(line);
		}
		if (in != null) {
			in.close();
		}
		return ret;
	}

	/** Get the GPX file and other parameters into shape for POSTing */
	public static String encodePostBody(String description, TraceVisibility visibility, File gpxFile) throws IOException {
		StringBuffer body = new StringBuffer();
		body.append(encodePlainTextPart("description", description, true));
		body.append(encodePlainTextPart("visibility", visibility.toString().toLowerCase(), false));
		body.append(encodePlainTextPart("tags", "test", false));

		body.append("\r\n--" + BOUNDARY + "\r\n");
		body.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + gpxFile.getName() + "\"\r\n");
		body.append("Content-Type: application/gpx+xml\r\n");
		body.append("\r\n");

		body.append(FileIO.readerToString(new FileReader(gpxFile)));

		body.append("\r\n--" + BOUNDARY + "--\r\n");
		return body.toString();
	}

	private static String encodePlainTextPart(String partName, String partBody, boolean first) {
		return (first? "" : "\r\n") + "--" + BOUNDARY + "\r\n"
				+ "Content-Disposition: form-data; name=\"" + partName + "\"\r\n\r\n" 
				+ partBody;
	}

	public static void setDebug(boolean debug) {
		Upload.debug = debug;
	}
}
