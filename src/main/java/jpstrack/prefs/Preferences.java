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
	
	/** 
	 * @return the platform-dependant default directory path
	 */
	public abstract String getDefaultDirectoryPath();

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
