package frontend;

import javafx.scene.layout.BorderPane;

/**
 * All of our gui stuff should be added to this. This will be the main background for the gui.
 * @author kirkp1ia
 *
 */
public class BasePane extends BorderPane {
	
	public CreateUserForm createUserForm = new CreateUserForm();
	
	public BasePane() {
		this.setCenter(this.createUserForm);
	}
}
