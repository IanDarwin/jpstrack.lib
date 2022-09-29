package jpstrack.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Reading {

	public Reading(double latitude, double longitude, double altitude, long time) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.time = time;
	}

	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public long getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return String.format("R[%s, LAT %f, LON %f, Alt %f]",
			LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()),
			latitude, longitude, altitude);
	}
}
