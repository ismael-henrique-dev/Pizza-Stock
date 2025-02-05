package Controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.AdminDAO;
import Models.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController {
    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private void switchToHomaPage() throws IOException {
        App.setRoot("home");
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
    private void handleLoginAdmin() throws IOException {
        try {
            String email = emailInput.getText();
            String password = passwordInput.getText();

            if (password == null || password.isEmpty()) {
                showAlert("Erro", "A senha não pode ser vazia!", "Por favor, insira a sua senha corretamente.");
                return;
            }

            if (!isValidEmail(email)) {

                showAlert("Erro", "E-mail inválido!", "Por favor, insira um e-mail válido.");
                return;
            }

            Admin admin = new Admin();
            admin.setEmail(email);
            admin.setSenha(password);

            new AdminDAO().loginAdmin(admin);
            switchToHomaPage();
        } catch (IOException e) {
            showAlert("Erro", "Credenciais incorretas", "Digite suas credenciais corretas.");

            System.out.println("Erro ao logar: " + e);

        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }
}
