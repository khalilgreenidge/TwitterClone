package com.bham.fsd.assignments.jabberserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class UsersNotFollowingController implements Initializable {
	@FXML public TableView<UsersNotFollowing> usersNotFollowing = new TableView<UsersNotFollowing>();
		@FXML public TableColumn<UsersNotFollowing, String> colUsername = new TableColumn<UsersNotFollowing, String>();
		@FXML public TableColumn<UsersNotFollowing, Button> B5 = new TableColumn<UsersNotFollowing, Button>();
		
	public ObservableList<UsersNotFollowing> notFollowing = FXCollections.observableArrayList();
	public String username;
	public JabberMessage unknownFollowers = null;
	
		
	/**
	 * ClientRQ8
	 * @param username
	 * @throws IOException
	 */
	
	
	
	@Override public void initialize(URL url, ResourceBundle resourceBundle) {
		
		showUnfollowers();
		
	}
	

	
	//Updates table after new jab
	@FXML public void updateTable() throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException{
		
		notFollowing.clear();
		usersNotFollowing.getItems().clear();
		showUnfollowers();
	}
	
	
	public void showUnfollowers() {
		Platform.runLater(new Runnable() {
			
			
			@Override public void run() {
				
				//System.out.println("ShowUnfollowers Method----    User: " + username);
				
				try {
					unknownFollowers = new User().connect(username, "users");
				} catch (ClassNotFoundException | IOException | InterruptedException e) {
					e.printStackTrace();
				}
				
				
				for (int i = 0; i < unknownFollowers.getData().size(); i++) {
					Button followButton = new Button();
				
					String unknownFollower = unknownFollowers.getData().get(i).get(0);
					followButton.setId(unknownFollower);
					
					UsersNotFollowing follower = new UsersNotFollowing(unknownFollowers.getData().get(i).get(0),followButton);
					follower.user = username;
					
					notFollowing.add(follower);
				}
				
				
				usersNotFollowing.setItems(notFollowing);
				
				colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
				B5.setCellValueFactory(new PropertyValueFactory<>("newFollow"));
				
			}
		});
	}
	

	
}
