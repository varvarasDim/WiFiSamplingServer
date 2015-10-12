package org.varvaras.dimitris;
import java.io.*;
import java.net.*;

public class CodeWord {
	private String code;

	public CodeWord() {
		this.code = "";

	}

	public String getCodeWord() {
		return this.code;
	}

	public void setCodeWord(String cw) {
		this.code = cw;
	}

	public void receiveCodeWord(Socket server) {
		String data="";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					server.getInputStream()));

			data = in.readLine();
			System.out.println("The Received Data is: " + data);

		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
		code = data;
	}

	public void sendCodeWord(Socket server) {
												
		try {

			PrintStream out = new PrintStream(server.getOutputStream());
			out.println(code);
			System.out.println("Send CodeWord: " + code);

		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}
}
