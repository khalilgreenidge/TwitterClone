package com.bham.fsd.assignments.jabberserver;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));		
		Scene scene = new Scene(root);
		primaryStage.getIcons().add(new Image("images/jab.png"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Jabber");
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		
		launch(args);
		
		

	}
}
