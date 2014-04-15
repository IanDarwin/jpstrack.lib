package jpstrack.model;

import java.util.Date;

public class Reading {
	long time;
	double latitude;
	double longitude;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return String.format("R[%s, LAT %f, LON %f[",
				new Date(time), latitude, longitude);
	}
}
