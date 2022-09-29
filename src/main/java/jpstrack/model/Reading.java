package jpstrack.model;

import java.time.LocalDate;

public record Reading(
	double latitude,
	double longitude,
	double altitude,
	long time) {

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
			LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue), ZoneId.systemDefault()),
			latitude, longitude, altitude);
	}
}
