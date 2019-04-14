package view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JTextArea;

public class LogPanel extends ImagePanel{
	private Log log;
	private JTextArea logTextArea;
	public LogPanel(String img) {
		super(img);
		logTextArea = new JTextArea();
		logTextArea.setSize(500, 216);
		logTextArea.setPreferredSize(this.getSize());
		logTextArea.setEditable(false);
		logTextArea.setPreferredSize(this.getSize());
		logTextArea.setMaximumSize(this.getPreferredSize());
		
		logTextArea.setPreferredSize(this.getSize());
		log = new Log(logTextArea);
		
		setLayout(new GridLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 100;
		constraints.gridy = 100;
		setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//Contents
		add(log,constraints);
	}
	public Log getLog() {
		return log;
	}
	public JTextArea getLogTextArea() {
		return logTextArea;
	}
}
