package Controllers;

import java.io.IOException;

import DAO.AdminDAO;
import Models.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToRegisterPage() throws IOException {
        System.out.println("Switching to Register Page...");
        App.setRoot("registerPage");
    }

    @FXML
    private void switchToForgotPasswordPage() throws IOException {
        System.out.println("Switching to Forgot Password Page...");
        App.setRoot("verificationCodePage");
    }

    @FXML
    private void handleLoginAdmin() {
        String email = emailInput.getText();
        String password = passwordInput.getText();

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setSenha(password);

        new AdminDAO().loginAdmin(admin);
    }
}
