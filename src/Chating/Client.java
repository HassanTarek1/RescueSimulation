package Chating;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private String address;
	private int port;
	

	public String getAddress() {
		return address;
	}


	public int getPort() {
		return port;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public Client(String address,int port) throws IOException {
		this.address=address;
		this.port=port;
		@SuppressWarnings("resource")
		Socket clientSocket = new Socket(address, port);
		String sentence="";
		String modifiedSentence = null;
		System.out.println("enter a string");
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
		BufferedReader inFromServer =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		while(true){
			if(inFromUser.ready()) {
				inFromUser=new BufferedReader(new InputStreamReader(System.in));
				sentence=inFromUser.readLine();
				DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
				if(sentence!=null)
				outToServer.writeBytes(sentence+"\n");
			}
			
			if(inFromServer.ready()) {
				modifiedSentence=inFromServer.readLine();
				if(modifiedSentence!=null)
					System.out.println("From Server : "+modifiedSentence);
			}
		}
		
	}

}
