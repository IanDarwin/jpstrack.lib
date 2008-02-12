package regress;

import static org.junit.Assert.*;

import java.io.File;

import gpstrack.fileio.GPSFileSaver;
import gpstrack.model.Reading;

import org.junit.Before;
import org.junit.Test;

public class TestFileSaver {
	
	GPSFileSaver saver;

	@Before
	public void setUp() throws Exception {
		saver = new GPSFileSaver();
	}

	@Test
	public final void testWrite() {
		saver.setStartingDir(System.getProperty("java.tmp.dir"));
		saver.setTemplate("testwrite.x");
		saver.startFile();
		Reading r = new Reading();
		r.setLatitude(23.4567f);
		r.setLongitude(45.6789f);
		r.setTime(1000000000L);
		saver.write(r);
		saver.close();
	}

}
