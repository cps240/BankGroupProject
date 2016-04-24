package frontend;

import backend.auth.Authentication;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import main.App;

/**
 * All of our gui stuff should be added to this. This will be the main background for the gui.
 * @author kirkp1ia
 *
 */
public class BasePane extends BorderPane {
	
	public Pane content;
	
	/**
	 * Will contain buttons for navigation such as add account
	 */
	public FlowPane navBar = new FlowPane();
		public AnchorPane addAccountButtonContainer = new AnchorPane();
			public Label addAccountLabel = new Label("New Account");
		public AnchorPane depositButtonContainer = new AnchorPane();
			public Label depositLabel = new Label("Make Deposit");
		public AnchorPane withdrawalButtonContainer = new AnchorPane();
			public Label withdrawalLabel = new Label("Make Withdrawal");
		public AnchorPane transferButtonContainer = new AnchorPane();
			public Label transferLabel = new Label("Make Transfer");
		public AnchorPane homeButtonContainer = new AnchorPane();
			public Label homeLabel = new Label("My Accounts");
			
	public void buildNavBar() {
		this.navBar.getChildren().add(this.addAccountButtonContainer);
		this.setAddAccountsButtonAttributes();
		
		this.navBar.getChildren().add(this.depositButtonContainer);
		this.setDepositButtonAttributes();
		
		this.navBar.getChildren().add(this.withdrawalButtonContainer);
		this.setWithdrawalButtonAttributes();
		
		this.navBar.getChildren().add(this.transferButtonContainer);
		this.setTransferButtonAttributes();
		
		this.navBar.getChildren().add(this.homeButtonContainer);
		this.setHomeButtonAttributes();
		
		Insets buttonGap = new Insets(7.5);
		
		this.navBar.setMargin(this.addAccountButtonContainer, buttonGap);
		this.navBar.setMargin(this.depositButtonContainer, buttonGap);
		this.navBar.setMargin(this.withdrawalButtonContainer, buttonGap);
		this.navBar.setMargin(this.transferButtonContainer, buttonGap);
		this.navBar.setMargin(this.homeButtonContainer, buttonGap);
		
		double extraSpace = (buttonGap.getLeft() +buttonGap.getRight()) * 5;
		double buttonSpace = 500;
		/*
		 * This is the space taken up by the navbar. set the width of the window at least to this.
		 */
		double totalSpace = extraSpace + buttonSpace;
		
		this.setPrefWidth(totalSpace);
		
		this.navBar.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, null, null)));
	}
			
	public void setAddAccountsButtonAttributes() {
		this.addAccountButtonContainer.getChildren().add(this.addAccountLabel);
		AnchorPane.setRightAnchor(this.addAccountLabel, 0.0);
		AnchorPane.setLeftAnchor(this.addAccountLabel, 0.0);
		this.addAccountButtonContainer.setPadding(new Insets(5, 5, -3, 5));
		
		this.addAccountButtonContainer.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		this.addAccountButtonContainer.setOpacity(0.5);
		this.addAccountLabel.setOnMouseEntered(e -> {
				// TODO Auto-generated method stub
				addAccountButtonContainer.setOpacity(1);
				addAccountButtonContainer.setCursor(Cursor.HAND);
		});
		this.addAccountLabel.setOnMouseExited(e -> {
				// TODO Auto-generated method stub
				addAccountButtonContainer.setOpacity(0.5);
		});
		this.addAccountLabel.setOnMouseClicked(e -> {
			this.setContent(new CreateAccountForm());
		});
	}
	
	public void setDepositButtonAttributes() {
		this.depositButtonContainer.getChildren().add(this.depositLabel);
		AnchorPane.setRightAnchor(this.depositLabel, 0.0);
		AnchorPane.setLeftAnchor(this.depositLabel, 0.0);
		this.depositButtonContainer.setPadding(new Insets(5, 5, -3, 5));
		
		this.depositButtonContainer.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		this.depositButtonContainer.setOpacity(0.5);
		this.depositLabel.setOnMouseEntered(e -> {
				// TODO Auto-generated method stub
				depositButtonContainer.setOpacity(1);
				depositButtonContainer.setCursor(Cursor.HAND);
		});
		this.depositLabel.setOnMouseExited(e -> {
				// TODO Auto-generated method stub
				depositButtonContainer.setOpacity(0.5);
		});
		this.depositLabel.setOnMouseClicked(e -> {
			this.setContent(new DepositPane());
		});
	}
	
	public void setWithdrawalButtonAttributes() {
		this.withdrawalButtonContainer.getChildren().add(this.withdrawalLabel);
		AnchorPane.setRightAnchor(this.withdrawalLabel, 0.0);
		AnchorPane.setLeftAnchor(this.withdrawalLabel, 0.0);
		this.withdrawalButtonContainer.setPadding(new Insets(5, 5, -3, 5));
		
		this.withdrawalButtonContainer.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		this.withdrawalButtonContainer.setOpacity(0.5);
		this.withdrawalLabel.setOnMouseEntered(e -> {
				// TODO Auto-generated method stub
				withdrawalButtonContainer.setOpacity(1);
				withdrawalButtonContainer.setCursor(Cursor.HAND);
		});
		this.withdrawalLabel.setOnMouseExited(e -> {
				// TODO Auto-generated method stub
				withdrawalButtonContainer.setOpacity(0.5);
		});
		this.withdrawalLabel.setOnMouseClicked(e -> {
			this.setContent(new WithdrawalPane());
		});
	}
	
	public void setTransferButtonAttributes() {
		this.transferButtonContainer.getChildren().add(this.transferLabel);
		AnchorPane.setRightAnchor(this.transferLabel, 0.0);
		AnchorPane.setLeftAnchor(this.transferLabel, 0.0);
		this.transferButtonContainer.setPadding(new Insets(5, 5, -3, 5));
		
		this.transferButtonContainer.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		this.transferButtonContainer.setOpacity(0.5);
		this.transferLabel.setOnMouseEntered(e -> {
				// TODO Auto-generated method stub
				transferButtonContainer.setOpacity(1);
				transferButtonContainer.setCursor(Cursor.HAND);
		});
		this.transferLabel.setOnMouseExited(e -> {
				// TODO Auto-generated method stub
				transferButtonContainer.setOpacity(0.5);
		});
		this.transferLabel.setOnMouseClicked(e -> {
			this.setContent(new TransferPane());
		});
	}
	
	public void setHomeButtonAttributes() {
		this.homeButtonContainer.getChildren().add(this.homeLabel);
		AnchorPane.setRightAnchor(this.homeLabel, 0.0);
		AnchorPane.setLeftAnchor(this.homeLabel, 0.0);
		this.homeButtonContainer.setPadding(new Insets(5, 5, -3, 5));
		
		this.homeButtonContainer.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		this.homeButtonContainer.setOpacity(0.5);
		this.homeButtonContainer.setOnMouseEntered(e -> {
				// TODO Auto-generated method stub
				homeButtonContainer.setOpacity(1);
				homeButtonContainer.setCursor(Cursor.HAND);
		});
		this.homeButtonContainer.setOnMouseExited(e -> {
				// TODO Auto-generated method stub
				homeButtonContainer.setOpacity(0.5);
		});
		this.homeLabel.setOnMouseClicked(e -> {
			this.setContent(new CustomerMainPage());
		});
	}
	
	public void setContent(Pane pane) {
		this.content = pane;
		this.setCenter(this.content);
	}
		
	public BasePane() {
		this.content = new CreateAccountForm();
		this.setCenter(this.content);
		
		this.setTop(this.navBar);
		this.buildNavBar();
	}
}
