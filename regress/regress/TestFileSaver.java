package regress;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import jpstrack.fileio.GPSFileSaver;
import jpstrack.model.Reading;

import org.junit.Before;
import org.junit.Test;

import com.darwinsys.io.FileIO;

public class TestFileSaver {
	
	GPSFileSaver saver;

	@Before
	public void setUp() throws Exception {
		saver = new GPSFileSaver();
	}

	@Test
	public final void testWrite() throws IOException {
		final File dirFile = new File(System.getProperty("java.tmp.dir", "/tmp"));
		saver.setStartingDir(dirFile);
		saver.setFileName("testwrite.gpx");
		saver.startFile();
		Reading r = new Reading();
		r.setLatitude(23.4567d);
		r.setLongitude(45.6789d);
		r.setTime(1000000000L);
		saver.write(r);
		saver.close();
		String str = FileIO.readAsString(
				new File(dirFile, saver.getFileName()).getAbsolutePath());
		assertTrue(str.indexOf("23.4567") > 0);
		// ensure file written (and read) to ends
		assertTrue(str.indexOf("</gpx>") > 0);
	}

}
