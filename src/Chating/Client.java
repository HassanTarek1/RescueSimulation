package Chating;

import java.io.*;
import java.net.Socket;

public class Client {
	
	private String address;
	private InputStream input;
	private OutputStream output;
	public OutputStream getOutput() {
		return output;
	}




	public void setOutput(OutputStream output) {
		this.output = output;
	}




	public InputStream getInput() {
		return input;
	}




	public void setInput(InputStream input) {
		this.input = input;
	}




	public String getAddress() {
		return address;
	}


	

	public void setAddress(String address) {
		this.address = address;
	}




	public Client(String address,InputStream input,OutputStream output) throws IOException {
		this.address=address;
		this.input=input;
		this.output=output;
		PrintStream printStream = new PrintStream(output);
		System.setOut(printStream);
		System.setErr(printStream);
		@SuppressWarnings("resource")
		Socket clientSocket = new Socket(address, 5000);
		String sentence="";
		String modifiedSentence = null;
		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(input));
		BufferedReader inFromServer =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		while(true){
			if(inFromUser.ready()) {
				inFromUser=new BufferedReader(new InputStreamReader(input));
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
