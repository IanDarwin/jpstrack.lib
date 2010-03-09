package jpstrack.model;

import java.util.Date;

public class Reading {
	long time;
	float latitude;
	float longitude;
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
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
