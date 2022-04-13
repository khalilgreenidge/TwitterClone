package com.bham.fsd.assignments.jabberserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class TimelineController extends AnchorPane implements Initializable {
	
	@FXML public TableView<Timeline> table = new TableView<Timeline>();
		@FXML public TableColumn<Timeline, String>  ColJabberUser = new TableColumn<Timeline, String>();
		@FXML public TableColumn<Timeline, String>  ColJabText = new TableColumn<Timeline, String>();
		@FXML public TableColumn<Timeline, Button>  ColLikeButton = new TableColumn<Timeline, Button>();
		@FXML public TableColumn<Timeline, Integer> ColNumLikes = new TableColumn<Timeline, Integer>();
	@FXML public TextField T2;
	public static ObservableList<Timeline> timeline = FXCollections.observableArrayList();
	public String username;
	
	public Thread update;
	public boolean isRunning = true;

		
	/**
	 * ClientRQ9
	 * @param event
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	@FXML public void postJab(ActionEvent event) throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException{
		
		if(T2.getText().equals("")) 
			JOptionPane.showMessageDialog(null, "Please enter text. Your textfield is empty!");
		else{
			JabberMessage jm = new User().connect(username, "post " + T2.getText());

			T2.setText("");

			if(jm.getMessage().equals("posted")) {
				JOptionPane.showMessageDialog(null, jm.getMessage());
			}
		}
		
	}
	
	/**
	 * ClientRQ10
	 *
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws UnknownHostException 
	 */
	
	@FXML public void globalChanges() throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException {
		
		while(isRunning) {
			updateTable();
			
			//pause for 2 secs
			Thread.sleep(2000);
		}
		
	}
	
	public void showTimeline() {
		Platform.runLater(new Runnable() {
			
			
			@Override public void run() {
				
					//while(true) {
						//System.out.println("ShowTimeline Called! Username: " + username);

						JabberMessage jd = null;
						try {
							jd = new User().connect(username, "timeline");
						} catch (ClassNotFoundException | IOException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
										
						for (int i = 0; i < jd.getData().size(); i++) {
							String juser = jd.getData().get(i).get(0);
							String jab = jd.getData().get(i).get(1);
							Integer numberOfLikes = Integer.parseInt(jd.getData().get(i).get(3));
							Button likeButton = new Button(username);
							likeButton.setId(username);

							Timeline t = new Timeline(juser, jab, numberOfLikes, likeButton);
							t.setUsername(username);
							timeline.add(t); 
							
						}

						
						
						table.setItems(timeline);
						ColJabberUser.setCellValueFactory(new PropertyValueFactory<>("jabberUser"));
						ColJabText.setCellValueFactory(new PropertyValueFactory<>("jabText"));
						ColNumLikes.setCellValueFactory(new PropertyValueFactory<>("numberOfLikes"));
						ColLikeButton.setCellValueFactory(new PropertyValueFactory<>("likeButton"));

						/*
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
						
				//}
				
					
				
			}
		});
	}
	
	//Updates table after new jab
	@FXML public void updateTable() throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException{
		
		timeline.clear();
		showTimeline();
	}
	
	@Override public void initialize(URL location, ResourceBundle resources) {		
			
		update = new Thread(() -> {
			try {
				globalChanges();
			} catch (ClassNotFoundException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		update.start();
		
	}
	
	/**
	 * CLient RQ6
	 * @param user
	 * @param jabID
	 * @throws UnknownHostException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	public void likeJab(String user, String jabID) throws UnknownHostException, ClassNotFoundException, IOException, InterruptedException {
		
		//System.out.println("About to like using username: " + user + " and \"like \"" + jabID);
		
		JabberMessage jd = new User().connect(user, "like " + jabID);
		
		if(jd.getMessage().equals("posted")) {
			//JOptionPane.showMessageDialog(null, "You liked a Jab " + jabID + "!"); //success			
			JOptionPane.showMessageDialog(null, jd.getMessage()); //success			
			//updateTable();			
		}
		else
			JOptionPane.showMessageDialog(null,"You liked the jab already!");
	}
	
	public String getUsername() {
		return username;
	}
	
	
}