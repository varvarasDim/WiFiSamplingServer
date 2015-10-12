package org.varvaras.dimitris;
import java.io.*;
import java.net.*;

public class WiFiServer {
	
	public static final String WIFI_NETWORK = "Dahaka";
	private Chart cs;
	private static int port = 8731, maxConnections = 0;

	public WiFiServer() {
		int i = 0;
		cs = new Chart();

		try {
			ServerSocket listen = new ServerSocket(port);
			listen.setReuseAddress(true); 
			Socket server;

			while ((i++ < maxConnections) || (maxConnections == 0)) {
				System.out.println("New Thread");
				server = listen.accept();
				CommNChart conn_c = new CommNChart(server, cs);
				Thread t = new Thread(conn_c);
				t.start();
			}
		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		WiFiServer wfs = new WiFiServer();

	}

}

class CommNChart implements Runnable {

	private Socket server;
	private Chart cs;
	public CommNChart(Socket server, Chart cs) {
		this.cs = cs;
		this.server = server;

	}
	public void run() {
		String buffer = "";
		CodeWord cw = new CodeWord();
		cw.receiveCodeWord(server);
		String data = "";
		if (cw.getCodeWord().startsWith("SAMPLE"))
		{
			//get sample
			try {
				data = cw.getCodeWord();
				cw.setCodeWord("SAMPLE_OK");
				cw.sendCodeWord(server);
				server.close();
			} catch (Exception e) {	System.out.println("EXCEPTION"); }
			//extract sample data
			String[] initialData = data.split("##");
			String[] networks = initialData[2].split("%%"); //extract network data and throw date data 
			for (int i = 0; i < networks.length; i++) {
				if (networks[i].endsWith(WiFiServer.WIFI_NETWORK)) {
					cs.counter++;
					String[] networkData = networks[i].split(";");							//networkData[2] is dbm of the signal
					cs.datalist.add(new Data(Integer.parseInt(networkData[2]), cs.counter));
					cs.refreshChart();
				}
				System.out.println(networks[i]);
			}
			System.out.println("Sample Received");

		} else {
			System.out.println("Can not recognize received data:" + buffer);

		}
		try {
			if (server.isClosed() == false) {

				server.close(); 
			}
		} catch (IOException e) {
			e.printStackTrace();
			;
		}
	}

}
