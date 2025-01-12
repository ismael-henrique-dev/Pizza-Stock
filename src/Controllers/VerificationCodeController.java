package Controllers;

import java.io.IOException;

import javafx.fxml.FXML;

public class VerificationCodeController {
	@FXML
	private void switchToForgotPasswordPage() throws IOException {
        System.out.println("Switching to Forgot Password Page...");
        App.setRoot("forgotPasswordPage");
    }
}
