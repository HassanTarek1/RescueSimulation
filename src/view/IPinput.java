package view;

import javax.swing.JOptionPane;

public class IPinput{
	private String ip;
	private int port;
	
	public int getPort() {
		return port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public IPinput() {
		// TODO Auto-generated constructor stub
		String friendIP=JOptionPane.showInputDialog("Enter IP");
		this.ip=friendIP;

		try{
			int port=Integer.parseInt(JOptionPane.showInputDialog("Enter port number"));
			this.port=port;
		}
		catch (Exception e) {
			// TODO: handle exception
			new MiniFrame("Enter a valid port");
		}
		JOptionPane.showMessageDialog(null,"IP : "+ip+"\n"+"port : "+port );
		//System.exit(0);
		
	}
	
	public static void main(String[] args) {
		new IPinput();
	}
}
