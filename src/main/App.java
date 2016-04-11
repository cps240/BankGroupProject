package main;

import backend.Settings;
import backend.storage.StorageHandler;
import frontend.BasePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		//Only use this for gui!
		Settings.storage.readUsers();
		Settings.storage.readAccountRelationships();
		
		BasePane pane = new BasePane();
		Scene mainScene = new Scene(pane);
		
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
