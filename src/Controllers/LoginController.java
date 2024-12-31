package Controllers;

import java.io.IOException;

import javafx.fxml.FXML;

public class LoginController {
	@FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

	@FXML
	private void switchToRegisterPage() throws IOException {
		System.out.println("Switching to Register Page...");
        App.setRoot("registerPage");
    }
}
    