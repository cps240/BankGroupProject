package main;

import frontend.TransferPane;
import backend.Settings;
import frontend.DepositPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
	
	public static Pane pane = new TransferPane();
	public static Scene mainScene = new Scene(pane);
		
	@Override
	public void start(Stage primaryStage) throws Exception{
		//Only use this for gui!
		Settings.storage.readUsers();
		Settings.storage.readAccountRelationships();
		
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(e -> Settings.storage.printData());
	}

	public static void main(String[] args) {
		/*
		* Only use this for gui!
		* for console stuff, use the main in frontend.console.CosoleApp
		*/
		
		launch(args);
	}
}