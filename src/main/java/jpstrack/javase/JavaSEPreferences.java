package jpstrack.javase;

import java.io.File;

import jpstrack.prefs.Preferences;

public class JavaSEPreferences extends Preferences {

		@Override
		public String getDefaultDirectoryPath() {
			String home = System.getProperty("user.home");
			String rest = ".jpstrack";
			return home + File.separator + rest;
		}
		
		public static final String GPSD_HOST = "localhost";
		public static final int GPSD_PORT = 2947;
		
		private String gpsdHost= GPSD_HOST ;
		private int gpsdPort = GPSD_PORT;
		
		public String getGpsdHost() {
			return gpsdHost;
		}
		public void setGpsdHost(String gpsdHost) {
			this.gpsdHost = gpsdHost;
		}
		public int getGpsdPort() {
			return gpsdPort;
		}
		public void setGpsdPort(int gpsdPort) {
			this.gpsdPort = gpsdPort;
		}
		public void setGpsdPort(String gpsdPort) {
			setGpsdPort(Integer.parseInt(gpsdPort));
		}
}
