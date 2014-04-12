package jpstrack.upload;

public class UploadResponse {
	int status;	// should be 200!
	long gpxId;					// ID of newly-uploaded gpx file, if any

	@Override
	public String toString() {
		if (status == 200) {
			return "OK: " + gpxId;
		}
		return status + ": " + gpxId;
	}

	public int getStatus() {
		return status;
	}

	public long getGpxId() {
		return gpxId;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setGpxId(long gpxId) {
		this.gpxId = gpxId;
	}
}
