package com.bham.fsd.assignments.jabberserver;
import com.bham.fsd.assignments.jabberserver.*;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/*
 * JabberClientHandler is to create a thread to handler each client.
 * Remember, this is a multi-threaded server
 */

public class ClientConnection extends Thread{
	
	//every client needs a Socket and Database Connection
	private Socket clientSocket;
	private JabberDatabase dbConnection;  //were leaving the name as JabberServer because of the original file name. But its the db connection
	public String username;
	public int userID;
	
	public ClientConnection(Socket sock, JabberDatabase con) {
		this.clientSocket = sock;
		this.dbConnection = con;
	}
	
	
	@Override
	public void run(){
		
		//grabbing the "Jab" from the client and passing it to the server
		
		//deserialization
		try {
			//Output Stream to create server response message for client
			OutputStream outputStream = clientSocket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			
			
			//create inputStream for Object
			InputStream inputStream = clientSocket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			
			
			//read object from input stream
			JabberMessage user = (JabberMessage)ois.readUnshared();
			JabberMessage theJab = (JabberMessage)ois.readUnshared();
			
			//get username
			username = user.getMessage();
			userID = dbConnection.getUserID(username);
			
			
			//System.out.println("Client message: \"" + theJab.getMessage() + "\"");
			
			
			
			
			//split text from object
			String clientMsg[] = theJab.getMessage().split(" ");
			
			//P.3 - signout
			//while (!"signout".equalsIgnoreCase(clientMsg[0])) {
				
				JabberMessage msg;
				ArrayList<ArrayList<String>> data;
				
				//switch the message typ
				switch(clientMsg[0]) {
				
					//P.1a & b - Signing in 
					case "signin":
						//get if the username exists
												
						//dbConnection.resetDatabase();   // uncomment to reset the database
						
						if(userID == -1)
							//return unknown user
							//send server response to client
							msg = new JabberMessage("unknown-user");
						else {
							//send server response to client
							msg = new JabberMessage("signedin");
							username = clientMsg[1];
						}
						
						oos.writeObject(msg);
						oos.flush();
						break;
					
					//P.2 - Registering
					case "register":
						
						//get if the username exists
						userID = dbConnection.getUserID(clientMsg[1]);
						username = clientMsg[1];
						
						if(userID == -1) {
														
							//add user to table
							String emailadd = clientMsg[1] + "@jabber.com";

							dbConnection.addUser(clientMsg[1], emailadd);

							//send a server response
							msg = new JabberMessage("signedin");
						}	
						else {
							//return unknown user
							
							/*
							 * Note we return "unknown-user" on purpose 
							 * but really and truly, it just means the user exists!
							 */
							
							//send server response to client
							msg = new JabberMessage("unknown-user");
						}
						
						
						oos.writeObject(msg);
						break;
						
					//P.4 - timeline
					case "timeline":
						
						//get data
						data = dbConnection.getTimelineOfUserEx(username);
						
						//create message
						msg = new JabberMessage("timeline", data);
					
						//send server response
						oos.writeObject(msg);
						break;
						
					//P.5 - getting users (who to follow)
					case "users":
						
						data = dbConnection.getUsersNotFollowed(userID);
												
						msg = new JabberMessage("users", data);
						
						//send server response
						oos.writeObject(msg);
						break;
						
						
					//P.6 - Posting a Jab	
					case "post":
						
						//add post to database, where clientMsg[1] is the jabtext
						int i = 1;
						String post = "";
						while(i < clientMsg.length) {
							post += clientMsg[i] + " ";
							i++;
						}
						
						post = post.trim();
						
											
						dbConnection.addJab(username, post);
						
						//create msg
						msg = new JabberMessage("posted");
												
						//send server response
						oos.writeObject(msg);
						break;
						
						
					//P7. Liking a jab
					case "like":
						
						
						dbConnection.addLike(userID, Integer.parseInt(clientMsg[1]));

						//create msg
						msg = new JabberMessage("posted");
						
						//send server response
						oos.writeObject(msg);
						break;
						
						
					case "follow":
						
						//follow user in db, where clientMsg[1] is the username to be followed
						dbConnection.addFollower(userID, clientMsg[1]);
						
						//create msg
						msg = new JabberMessage("posted");
												
						//send server response
						oos.writeObject(msg);
						break;
						
						
				}
					
				
				
			//}
			
			
			//The user signed out
			//System.out.println("Terminating connection");
			
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		} 
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
