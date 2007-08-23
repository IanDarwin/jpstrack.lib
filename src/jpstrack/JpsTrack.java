package gpstrack;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.darwinsys.swingui.LabelText;

public class GpsTrack {

	public static void main(String[] args) {
		new GpsTrack();
	}

	final static String filenameFormat = "%1$tY%1$tm%1$td%1$tH%1$tM.gpx";

	GpsTrack() {
		JFrame f = new JFrame();

		JMenuBar bar = new JMenuBar();
		f.setJMenuBar(bar);

		bar.add(new JMenu("File"));
		bar.add(new JMenu("Edit"));
		bar.add(new JMenu("Settings"));
		bar.add(new JMenu("Help"));

		// Row 1
		JPanel row1 = new JPanel();
		row1.add(new JLabel("Lat"));
		final JLabel latText = new JLabel("49.1520");
		row1.add(latText);

		row1.add(new JLabel("Lon"));
		final JLabel lonText = new JLabel("-79.2918");
		row1.add(lonText);

		f.add(row1, BorderLayout.NORTH);

		// Row 2
		JPanel row2 = new JPanel();
		row2.add(new JButton("Start"));
		String now = String.format(filenameFormat, System.currentTimeMillis());
		row2.add(new JLabel(now));
		row2.add(new JButton("Pause"));
		row2.add(new JButton("Stop"));
		f.add(row2, BorderLayout.CENTER);

		// Row 3
		JPanel row3 = new JPanel();
		row3.add(new JButton("Txt Anno"));
		row3.add(new JButton("Voice Anno"));
		f.add(row3, BorderLayout.SOUTH);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
