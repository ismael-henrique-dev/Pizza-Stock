package Controllers;

import java.io.IOException;

import javafx.fxml.FXML;

public class NewPasswordController {
	@FXML
	private void switchToLoginPage() throws IOException {
		App.setRoot("loginPage");
	}
}
