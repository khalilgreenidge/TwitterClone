package com.bham.fsd.assignments.jabberserver;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UsersNotFollowing {
	private final SimpleStringProperty username;
	private Button newFollow;
	
	public String user;

	public UsersNotFollowing(String username, Button newFollow) {
		super();
		this.username  = new SimpleStringProperty(username);
		Image i = new Image("images/b.png");
		ImageView add = new ImageView(i);
		this.newFollow = new Button("", add);
		this.newFollow.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				try {
					try {
						new MainController().newFollow(user, username);
					} catch (ClassNotFoundException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	public String getUsername(){
		return username.get();
	}
	public Button getNewFollow(){
		return newFollow;
	}
}
