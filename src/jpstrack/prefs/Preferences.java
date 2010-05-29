package jpstrack.prefs;

/**
 * POJO to track preferences values
 */
public abstract class Preferences {
	/** file save directory */
	private String directoryPath;
	/** filename format */
	private String fileNameFormat;
	/** OSM username for uploading */
	private String userName;
	/** password for OSM upload account */
	private String password;
	
	private static final String FILENAME_FORMAT = "YYYYMMDDHHMM.gpx";
	private final static String filenameFormat = "%1$tY%1$tm%1$td%1$tH%1$tM.gpx";

	
	public abstract String getDefaultDirectoryPath();
	
	public String getDefaultFilenameFormat() {
		return FILENAME_FORMAT;
	}
	
	public String getNextFilename() {
		return String.format(filenameFormat, System.currentTimeMillis());		
	}

	public String getDirectoryPath() {
		return directoryPath;
	}
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}
	public String getFileNameFormat() {
		return fileNameFormat;
	}
	public void setFileNameFormat(String fileNameFormat) {
		this.fileNameFormat = fileNameFormat;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
