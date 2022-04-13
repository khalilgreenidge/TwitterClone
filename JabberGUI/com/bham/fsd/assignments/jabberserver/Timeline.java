package com.bham.fsd.assignments.jabberserver;

import java.io.IOException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Timeline {

	private final SimpleStringProperty  jabberUser;
	private final SimpleStringProperty  jabText ;
	private final SimpleIntegerProperty numberOfLikes;
	private Button likeButton;
	private String username;
	//Creating a graphic (image)
	@FXML private Image img = new Image("images/a.png");
    @FXML private ImageView view = new ImageView(img);

	public Timeline(String jabberUser, String jabText, Integer numberOfLikes, Button likeButton) {
		super();
		this.jabberUser = new SimpleStringProperty(jabberUser);
		this.jabText = new SimpleStringProperty(jabText);
		this.numberOfLikes = new SimpleIntegerProperty(numberOfLikes);
		this.likeButton = new Button("Like");
		this.likeButton.setGraphic(view);
		this.likeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				
				//System.out.println("username: " + username );
				
				JabberMessage jd = null;
				try {
					jd = new User().connect( username, "timeline");
				} catch (ClassNotFoundException | IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (int i = 0; i < jd.getData().size(); i++) {
					for(int j = 0; j < 1; j++) {
						if(jd.getData().get(i).get(1).equals(jabText)) {
							String jabID = (jd.getData().get(i).get(2));
							try {
								new TimelineController().likeJab(username, jabID);
							} catch (ClassNotFoundException | IOException | InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
		
	}

	public String getJabberUser() {
		return jabberUser.get();
	}
	public String getJabText() {
		return jabText.get();
	}
	public Integer getNumberOfLikes() {
		return numberOfLikes.get();
	}
	public Button getLikeButton() {
		return likeButton;
	}
	
	public void setUsername(String user) {
		username = user;
	}
}
