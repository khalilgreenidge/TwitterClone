package com.bham.fsd.assignments.jabberserver;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainController {
	public String username = "";
	@FXML public TextField T1;
	@FXML public Button B1;
	private Socket clientSocket = null;
	private static final int PORT_NUMBER = 44444;
	OutputStream outputStream;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	BufferedReader inputStream = null;
	
	private static ArrayList<String> requests = new ArrayList<>();
	private static ArrayList<JabberMessage> responses = new ArrayList<>();
	
	private static boolean powerButton = false; 
	
	//private static User user = new User();
	private UsersNotFollowingController uc;
	//private TimelineController timelineController;
		
	/**
	 * ClientRQ1 and ClientRQ2
	 *
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void login(ActionEvent event) throws HeadlessException, Exception {
		
		//write request to server
		JabberMessage jm = new User().connect(T1.getText(), "signin "+ T1.getText());
		
						
		if(jm.getMessage().equals("signedin")){
			JOptionPane.showMessageDialog(null, "Logged in");	
			username = T1.getText();
		
			((Node) event.getSource()).getScene().getWindow();//.hide();
			
			
			B1.setText("Sign Out");
			showTimelineWindow(username);
			showWhoToFollow(username);
			
			
			if (B1.getText().equals("Sign Out")) {
				B1.setOnAction(event1 -> {
					try {
						signOut(event);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
				
			} 
			
		}
		else 
			JOptionPane.showMessageDialog(null, "Invalid Username. Please retype, or register by entering a "
					+ "username then clicking register below.");
		
	}
	
	/**
	 * ClientRQ3
	 *
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void register(ActionEvent event) throws Exception {
		username = T1.getText();
		if(username.equals("")){
			System.out.println("Empty textfield");
		}
		else {
			JabberMessage response =
					new User().connect(T1.getText(), "register " + T1.getText());
			if (response.getMessage().equals("signedin")) {
				JOptionPane.showMessageDialog(null, "Registered successfully!");
				login(event);
			} else {
				JOptionPane.showMessageDialog(null, "Username already exists");
			}
		}
	}
	/**
	 * ClientRQ4
	 *
	 * @param event
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	@FXML
	public void signOut(ActionEvent event) throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException {
		//wait for the server response
		new User().disconnect("signout");
		System.exit(0);
	}
	/**
	 * ClientRQ5
	 *
	 * @throws IOException
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	@FXML public void showTimelineWindow(String user) throws IOException, ClassNotFoundException, InterruptedException {
		Stage stage  = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("Timeline.fxml").openStream());
		TimelineController tc = loader.getController();
		tc.username = user;
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image("images/jab.png"));
		stage.setTitle(user + "'s Timeline");
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * ClientRQ7
	 * @throws IOException
	 * @throws Exception
	 */
	@FXML public void showWhoToFollow(String user) throws IOException {
		Stage stage1  = new Stage();
		FXMLLoader loader1 = new FXMLLoader();
		Parent root1 = loader1.load(getClass().getResource(	"usersNotFollowing.fxml").openStream());
		uc = loader1.getController();
		uc.username = user;
		stage1.setTitle("Users you aren't following");
		stage1.getIcons().add(new Image("images/jab.png"));
		Scene scene1 = new Scene(root1);
		stage1.setScene(scene1);
		stage1.show();
	}
	public void newFollow(String user, String personToFollow) throws IOException, ClassNotFoundException, InterruptedException {

		//System.out.println("newFollower method----   username: " + user);
		
		JabberMessage jm = new User().connect(user, "follow " + personToFollow);
	
		if(jm.getMessage().equals("posted")) {
			JOptionPane.showMessageDialog(null,"Following " + personToFollow);

			
			new UsersNotFollowingController().showUnfollowers();
			//uc.showUnfollowers();
		}
		else
			JOptionPane.showMessageDialog(null,"Error following " + personToFollow);
		
	}
	/*
	@FXML public void newFollow(String personToFollow) throws IOException, ClassNotFoundException, InterruptedException {

		JabberMessage jm = new User().connect("follow " + personToFollow);
	
		if(jm.getMessage().equals("posted")) {
			JOptionPane.showMessageDialog(null,"Following " + personToFollow);

			
			
		}
		else
			JOptionPane.showMessageDialog(null,"Error following " + personToFollow);
		
	}
	*/
}

