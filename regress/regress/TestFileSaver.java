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
	
	double[][] data = {
		{45.678900, -80.000000, 1397526902100L },
		{44.068195, -79.506640, 1397526903200L },
		{44.068249, -79.506356, 1397526904300L },
		{44.068249, -79.506190, 1397526905400L },
	};

	@Test
	public final void testWrite() throws IOException {
		final File dirFile = new File(System.getProperty("java.tmp.dir", "/tmp"));
		saver.setStartingDir(dirFile);
		saver.setFileName("testdata.gpx");
		saver.startFile();
		for (double[] row : data) {
			Reading r = new Reading();
			r.setLatitude(row[0]);
			r.setLongitude(row[1]);
			r.setTime((long)row[2]);
			saver.write(r);
		}
		saver.close();
		String str = FileIO.readAsString(
				new File(dirFile, saver.getFileName()).getAbsolutePath());
		assertTrue(str.indexOf("45.67890") > 0);
		// ensure file written (and read) to ends
		assertTrue(str.indexOf("</gpx>") > 0);
	}

}
