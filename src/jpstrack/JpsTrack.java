package gpstrack;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GpsTrack {

	private static final String FILENAME_FORMAT = "YYYYMMDDHHMM.gpx";

	public static void main(String[] args) {
		new GpsTrack();
	}

	final static String filenameFormat = "%1$tY%1$tm%1$td%1$tH%1$tM.gpx";
	final JLabel fileNameLabel = new JLabel(FILENAME_FORMAT);
	private String fileName;
	private boolean paused = false;

	GpsTrack() {
		JFrame f = new JFrame("GPSTrack");

		final URL iconFileURL = getClass().getClassLoader().getResource(
				"images/gpstrack.png");
		System.out.println(iconFileURL);
		final Image image =
			Toolkit.getDefaultToolkit().getImage(iconFileURL);
		f.setIconImage(image);

		JMenuBar bar = new JMenuBar();
		f.setJMenuBar(bar);

		final JMenu fileMenu = new JMenu("File");
		bar.add(fileMenu);
		JMenuItem mi;
		mi = new JMenuItem("Quit");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFile();
				System.exit(0);
			}
		});
		fileMenu.add(mi);
		bar.add(new JMenu("Edit"));
		bar.add(new JMenu("Settings"));
		bar.add(new JMenu("Help"));

		// Row 1
		JPanel row1 = new JPanel();
		row1.add(new JLabel("Latitude"));
		final JLabel latText = new JLabel("44.012150");
		row1.add(latText);

		row1.add(new JLabel("Longitude"));
		final JLabel lonText = new JLabel("-79.932650");
		row1.add(lonText);

		f.add(row1, BorderLayout.NORTH);

		// Row 2
		JPanel row2 = new JPanel();
		final JButton startButton = new JButton("Start");
		// space at end to make room for "Resume"
		final JButton pauseButton = new JButton("Pause ");
		final JButton stopButton = new JButton("Stop");
		row2.add(startButton);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				openFile();
			}
		});
		row2.add(fileNameLabel);
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paused = !paused;
				pauseButton.setText(
					paused ? "Resume" : "Pause ");
			}
		});
		row2.add(pauseButton);
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
				closeFile();
			}
		});
		row2.add(stopButton);
		f.add(row2, BorderLayout.CENTER);

		// Row 3
		JPanel row3 = new JPanel();
		row3.add(new JButton("Txt Anno"));
		row3.add(new JButton("Voice Anno"));
		f.add(row3, BorderLayout.SOUTH);

		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

	protected void openFile() {
		String now = String.format(filenameFormat, System.currentTimeMillis());
		setFileName(now);
	}

	private void setFileName(String fileName) {
		this.fileName = fileName;
		fileNameLabel.setText(fileName);
		fileNameLabel.setEnabled(true);
	}

	protected void closeFile() {
		this.fileName = null;
		fileNameLabel.setText(FILENAME_FORMAT);
		fileNameLabel.setEnabled(false);
	}
}
