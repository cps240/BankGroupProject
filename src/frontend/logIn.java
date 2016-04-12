package frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class logIn extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		/*
		 * Declare:
		 * BorderPane
		 * HBox
		 * VBox
		 */
		BorderPane border = new BorderPane();
		Pane Hpane = new HBox(100);
		VBox pane = new VBox();
		
		
		border.setTop(Hpane);
		border.setLeft(pane);
		/*
		 * Creates the HBox for the header and the Image
		 */

		Hpane.setPadding(new Insets(10, 10, 10, 10));

		/*
		 *Settings for VBox
		 */

		pane.setPadding(new Insets(10));
		pane.setSpacing(8);



		/*
		 * makes the header bold and larger
		 */		
		Text header = new Text("Log In");
		header.setFont(Font.font("Courier", FontWeight.BOLD, 
				FontPosture.REGULAR, 25));
		Hpane.getChildren().add(header);

		/*
		 *  Declares and the image and saves it into the HBox
		 */
		//Image image = new Image("download.png");
		//Hpane.getChildren().add(new ImageView(image));

		/*
		 * rectangle dividing the title and picture from the 
		 * boxes that you type in
		 */
		Rectangle r = new Rectangle();
		r.setWidth(300);
		r.setHeight(3);
		pane.getChildren().add(r);

		/*
		 * Text and labels 
		 *  "Name: " ___________
		 *  "Grade: " ___________
		 */
		pane.getChildren().add(new Label("User Name: "));
		TextField TextField_UserName = new TextField();
		pane.getChildren().add(TextField_UserName);

		pane.getChildren().add(new Label("Password: ")); 
		TextField TextField_Password = new TextField();
		pane.getChildren().add(TextField_Password);

		
		//-------------------------------------------------------------------
		/*
		 * Makes the button that reads "Add Grade"
		 */
		Button Btn_LogIn = new Button("Log In");
		
		Btn_LogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
//***************  Needs the action when clicked  **********************
            }
        });
		
		pane.getChildren().add(Btn_LogIn);
		GridPane.setHalignment(Btn_LogIn, HPos.LEFT);


		// Create a scene and place it in the stage
		Scene scene = new Scene(border,400,400);
		primaryStage.setTitle("Log In"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}