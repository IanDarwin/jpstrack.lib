package jpstrack.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class GpsdDataGetter implements DataGetter {

	public static final int GPSD_PORT = 2947;
	
	private final Socket sock; 
	private final BufferedReader in;
	private final PrintWriter out;
	
	GpsdDataGetter() {
		try {
			sock = new Socket("localhost", GPSD_PORT);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);
		} catch (IOException e) {
			throw new RuntimeException("Can't connect to gpsd", e);
		}
	}
	
	/* Parse a line with format like:
	 * GPSD,O=GSA 1202864472.000 0.005 46.232232 -63.123533 0.30 43.20 42.40 ? ? 0.000 ? 86.40 ? 3
	 */
	Reading parseLine(String line) {
		StringTokenizer tok = new StringTokenizer(line);
		if (tok.countTokens() == 1) {
			System.err.println("NO FIX YET");
			return null;
		}
		if (tok.countTokens() != 15) {
			System.err.println("INVALID LINE: " + line);
			return null;
		}
		String type = tok.nextToken();
		if (!"GPSD".equals(type)) {
			throw new IllegalStateException(
			"Dunno what you connected me to, but it's not GPSD:" + line);
		}
		String time_t = tok.nextToken(".");
		long time = Long.parseLong(time_t.trim());
		tok.nextToken(" ");
		tok.nextToken();
		float lat = Float.parseFloat(tok.nextToken().trim());
		float lon = Float.parseFloat(tok.nextToken().trim());
		Reading r = new Reading(time, lat, lon, 0);
		return r;
	}

	/**
	 * Don't use "watcher" mode, but get a fix when
	 * we need it.
	 */
	public Reading getData() {
		try {
			out.println("o");
			String resp = in.readLine();
			Reading reading = parseLine(resp);
			System.out.println(reading);
		} catch (IOException e) {
			throw new RuntimeException("Error talking to gpsd", e);
		}
		return null;
	}

	public void close() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (sock != null)
				sock.close();
		} catch (IOException e) {
			throw new RuntimeException("Close Error!", e);
		}
	}
}
