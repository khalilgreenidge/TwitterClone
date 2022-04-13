package com.bham.fsd.assignments.jabberserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class User{

	public static final int PORT_NUMBER = 44444;
	public static Socket clientSocket;
	public static String username;
	OutputStream outputStream;
	BufferedReader inputStream = null;
	
	public JabberMessage connect(String username, String clientMsg) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {	
		
		clientSocket = new Socket("127.0.0.1", PORT_NUMBER);		
		
		//notify the client the server is running
		//System.out.println("Client is running.");
			
		//create an output stream to send Jab
		outputStream = clientSocket.getOutputStream();
		
		// create an object output stream from the output stream so we can send an object through it
	    ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			
		///client input stream
		inputStream = new BufferedReader( new InputStreamReader (clientSocket.getInputStream()));
			
		//write request to server
		JabberMessage jm = new JabberMessage(clientMsg);
		JabberMessage user = new JabberMessage(username);
		
		//sending jab to Server via Output Stream
		oos.writeUnshared(user);
		oos.writeUnshared(jm);
		//oos.flush();
		
		
		//receiver response from server
		ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
		JabberMessage request = (JabberMessage) ois.readObject();
		
		return request;
	}
	
	
	public void disconnect(String s) throws IOException {
				
		clientSocket = new Socket("127.0.0.1", PORT_NUMBER);		
		
		//notify the client the server is running
		//System.out.println("Client is running.");
			
		//create an output stream to send Jab
		outputStream = clientSocket.getOutputStream();
		
		// create an object output stream from the output stream so we can send an object through it
	    ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			
		///client input stream
		inputStream = new BufferedReader( new InputStreamReader (clientSocket.getInputStream()));
			
		//write request to server
		JabberMessage jm = new JabberMessage(s);
		
		//sending jab to Server via Output Stream
		oos.writeUnshared(jm);
		//oos.flush();
		
		System.out.println("Closing connection.");
		
		//close connection
		clientSocket.close();
		
	}
	

}
