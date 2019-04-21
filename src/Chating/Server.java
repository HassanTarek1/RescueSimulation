package Chating;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private int port;
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Server(int port) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket=new ServerSocket(port);
		Socket connectionSocket=welcomeSocket.accept();
		BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		String clientSentence=inFromClient.readLine();
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		String returnSentence;
		boolean available=false;
		BufferedReader inServer=new BufferedReader(new InputStreamReader(System.in));
		if(clientSentence.equals("CONNECT")) {
			available=true;
			outToClient.writeBytes("connected"+"\n");
		}
		while(true) {
			if(available) {
				if(inFromClient.ready()) {
					clientSentence=inFromClient.readLine();
					if(clientSentence!=null) {
						System.out.println("From Client : "+clientSentence);
					}
				}
				if(inServer.ready()) {
					returnSentence=inServer.readLine();
					outToClient.writeBytes(returnSentence+"\n");
				}
			}
			else {
				if(inFromClient.ready()) {
					clientSentence=inFromClient.readLine();
					if(clientSentence.equals("CONNECT")) {
						outToClient.writeBytes("connected"+"\n");
						available=true;
					}
				}	
			}
		}
	}

}
