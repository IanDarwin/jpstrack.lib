package gpstrack.fileio;

import gpstrack.model.Reading;

import java.io.*;

public class GPSFileSaver {
	
	private String startingDir;
	
	private String template;
	
	public GPSFileSaver(String startingDir, String template) {
		this.startingDir = startingDir;
		this.template = template;
	}
	
	public void startFile() {
		
	}
	
	public void write(Reading data) {
		
	}
	
	public void close() {
		
	}
	
	public GPSFileSaver() {
		
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
