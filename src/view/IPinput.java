package view;

import javax.swing.JOptionPane;

public class IPinput{
	private String ip;
	

	public String getIp() {
		return ip;
	}

	

	public IPinput() {
		// TODO Auto-generated constructor stub
		String friendIP=JOptionPane.showInputDialog("Enter IP");
		this.ip=friendIP;

		JOptionPane.showMessageDialog(null,"IP : "+ip+"\n" );
		//System.exit(0);
		
	}
	
	
}
