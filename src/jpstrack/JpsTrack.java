package jpstrack.javase;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jpstrack.model.Recorder;
import jpstrack.prefs.Preferences;

/**
 * UI Prototype for GPS Track program for OpenStreetMap data.
 */
public class JpsTrack {


	public static void main(String[] args) {
		new JpsTrack();
	}

	// Share GUI components
	private Preferences prefs = new JavaSEPreferences();
		
	private JDialog prefsDialog;
	private final JLabel fileNameLabel = new JLabel(prefs.getDefaultFilenameFormat());
	private final JButton startButton = new JButton("Start");
	// space at end to make room for "Resume"
	private final JButton pauseButton = new JButton("Pause");
	final JButton stopButton = new JButton("Stop");
	
	// Local State
	private String fileName;
	private boolean paused = false;

	public JpsTrack() {
		final JFrame mainFrame = new JFrame("GPSTrack");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Saving your file.");
				super.windowClosing(e);
			}
		});

		final URL iconFileURL = getClass().getClassLoader().getResource(
				"images/gpstrack.png");
		final Image image =
			Toolkit.getDefaultToolkit().getImage(iconFileURL);
		mainFrame.setIconImage(image);

		JMenuBar bar = new JMenuBar();
		mainFrame.setJMenuBar(bar);

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

		final JMenu editMenu = new JMenu("Edit");
		bar.add(editMenu);
		final Action prefsAction = new AbstractAction("Prefs") {
			private static final long serialVersionUID = -1834592522741890639L;

			public void actionPerformed(ActionEvent e) {
				if (prefsDialog == null) {
					prefsDialog = new PreferencesDialog(mainFrame, (JavaSEPreferences)prefs);
				}
				prefsDialog.setVisible(true);
			}
		};
		mi = new JMenuItem(prefsAction);
		editMenu.add(mi);

		final JMenu helpMenu = new JMenu("Help");
		bar.add(helpMenu);
		mi = new JMenuItem("About");
		helpMenu.add(mi);
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainFrame,
						"GPSTrack 0.0",
						"About GPSTrack",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Row 1
		JPanel row1 = new JPanel();
		row1.add(new JLabel("Latitude"));
		final JLabel latText = new JLabel("44.012150");
		row1.add(latText);

		row1.add(new JLabel("Longitude"));
		final JLabel lonText = new JLabel("-79.932650");
		row1.add(lonText);
		
		// XXX will look better when inner class has icon
		row1.add(new JButton(prefsAction));
		
		mainFrame.add(row1, BorderLayout.NORTH);

		// Row 2
		JPanel row2 = new JPanel();
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startAction();
			}
		});
		row2.add(startButton);
		row2.add(fileNameLabel);
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseAction();
			}
		});
		row2.add(pauseButton);
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopAction(startButton, stopButton);
			}
		});
		row2.add(stopButton);
		mainFrame.add(row2, BorderLayout.CENTER);

		// Row 3
		JPanel row3 = new JPanel();
		final JButton textNoteButton = new JButton("Text Note");
		textNoteButton.setEnabled(false);
		row3.add(textNoteButton);
		final JButton voiceNoteButton = new JButton("Voice Note");
		voiceNoteButton.setEnabled(false);
		row3.add(voiceNoteButton);
		mainFrame.add(row3, BorderLayout.SOUTH);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	private void startAction() {
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
		Runnable recorder = new Recorder();
		new Thread(recorder).start();
	}
	
	private void pauseAction() {
		paused = !paused;
		pauseButton.setText(
			paused ? "Resume" : "Pause ");
		}
	
	private void stopAction(final JButton startButton, final JButton stopButton) {
		stopButton.setEnabled(false);
		startButton.setEnabled(true);
		closeFile();
	}

	protected void openFile() {
		String now = prefs.getNextFilename();
		setFileName(now);
	}

	private void setFileName(String fileName) {
		this.fileName = fileName;
		fileNameLabel.setText(fileName);
		fileNameLabel.setEnabled(true);
	}
	
	public String getFileName() {
		return this.fileName;
	}

	protected void closeFile() {
		this.fileName = null;
//		fileNameLabel.setText(FILENAME_FORMAT);
//		fileNameLabel.setEnabled(false);
	}

}
