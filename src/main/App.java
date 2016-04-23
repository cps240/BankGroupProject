package main;

import frontend.TransferPane;
import backend.Settings;
import frontend.BasePane;
import frontend.DepositPane;
import frontend.LogInPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
	
	public static Stage primaryStage;
	public static Pane pane = new LogInPane();
	public static Scene mainScene = new Scene(pane);
		
	@Override
	public void start(Stage _primaryStage) throws Exception{
		//Only use this for gui!
		Settings.storage.readUsers();
		Settings.storage.readAccountRelationships();
		
		primaryStage = _primaryStage;
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