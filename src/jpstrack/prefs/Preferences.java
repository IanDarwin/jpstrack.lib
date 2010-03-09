package jpstrack.prefs;

/**
 * POJO to track preferences values
 */
public class Preferences {
	/** file save directory */
	private String directoryPath;
	/** filename format */
	private String fileNameFormat;
	/** username for uploading */
	private String userName;
	/** password for upload account */
	private String password;

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
