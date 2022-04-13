package com.bham.fsd.assignments.jabberserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JabberServer {

	private static ServerSocket serverSocket = null;
	private static final int PORT_NUMBER = 44444;
		
	
	public JabberServer() throws InterruptedException {
		
		//step 1. listening on a port
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			//serverSocket.setSoTimeout(300);
			serverSocket.setReuseAddress(true);
			
			System.out.println("Server is running. Connecting to database..");
			
		}
		catch(IOException e) {
			System.out.println("Server couldn't start.");
			e.printStackTrace();
		}
				
		//Step 2. Start to process clients
		System.out.println("Processing clients");
		processClientRequests();
	}
	
	//step 3. listening and processing to client requests
	public static void processClientRequests() throws InterruptedException {
		
		while(true) {
			Socket clientSocket = null;
			
			try {
				clientSocket = serverSocket.accept();
				
			}
			catch(IOException e) {
				e.printStackTrace();
			}
					
			//create a new thread for each request
			ClientConnection client = new ClientConnection(clientSocket, new JabberDatabase());
			client.start();
			
			//System.out.println("Client " + client.getId() + " starting...");
			//client.sleep(3000);
		}
				
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		//1. start server
		JabberServer server = new JabberServer();
		
		
		
	}

}
