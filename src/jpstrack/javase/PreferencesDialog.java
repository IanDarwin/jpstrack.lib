package jpstrack.javase;

import jpstrack.prefs.Preferences;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.darwinsys.swingui.LabelText;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = -203160926116576495L;
	private final Preferences preferences;
	
	WindowListener loader = new WindowAdapter() {
		@Override
		public void windowActivated(java.awt.event.WindowEvent e) {
			userNameLabelText.setText(preferences.getUserName());
			passwordLabelText.setText(preferences.getPassword());
			dirPathLabelText.setText(preferences.getDirectoryPath());
		}
	};

	final LabelText userNameLabelText = new LabelText("User");
	final LabelText passwordLabelText = new LabelText("Password");
	final LabelText dirPathLabelText = new  LabelText("DirectoryPath");

	public PreferencesDialog(Frame owner, Preferences prefs) {
		super(owner, true);
		this.preferences = prefs;
		setLayout(new GridLayout(0, 1));
		add(userNameLabelText);
		add(passwordLabelText);
		add(dirPathLabelText);
		
		JButton okButton = new JButton("OK");
		add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preferences.setUserName(userNameLabelText.getText());
				preferences.setPassword(passwordLabelText.getText());
				preferences.setDirectoryPath(dirPathLabelText.getText());		
				System.err.println("XXX Need to save to java.util.Preferences");
				PreferencesDialog.this.dispose();
			}
		});
	}
}
