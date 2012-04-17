package regress;

import java.io.File;

import jpstrack.fileio.GPSFileSaver;
import jpstrack.model.Reading;

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
		saver.setStartingDir(new File(System.getProperty("java.tmp.dir", "/tmp")));
		saver.setFileName("testwrite.x");
		saver.startFile();
		Reading r = new Reading();
		r.setLatitude(23.4567f);
		r.setLongitude(45.6789f);
		r.setTime(1000000000L);
		saver.write(r);
		saver.close();
	}

}
