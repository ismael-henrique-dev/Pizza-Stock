package Controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.AdminDAO;
import Models.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
	@FXML
	private void switchToLoginPage() throws IOException {
		App.setRoot("loginPage");
	}

	@FXML
	private TextField nameInput;

	@FXML
	private TextField emailInput;

	@FXML
	private PasswordField passwordInput;

	@FXML
	private void handleRegisterAdmin() {
		try {
			String name = nameInput.getText();
			String email = emailInput.getText();
			String password = passwordInput.getText();

			if (name == null || name.isEmpty()) {
                showAlert("Erro", "A o nome não pode ser vazio!", "Por favor, digite seu nome corretamente.");
                return;
            }

			if (password == null || password.isEmpty()) {
                showAlert("Erro", "A senha não pode ser vazia!", "Por favor, crie uma senha válida.");
                return;
            }

            if (!isValidEmail(email)) {

                showAlert("Erro", "E-mail inválido!", "Por favor, insira um e-mail válido.");
                return;
            }

			Admin admin = new Admin();
			admin.setNome(name);
			admin.setLogin(name);
			admin.setSenha(password);
			admin.setEmail(email);

			new AdminDAO().cadastrarAdmin(admin);
			System.out.println("Admin registrado");
			showAlertInfo("Conta criada!", "Agora você pode entrar na página de login.", null);
		} catch (Exception e) {
			showAlert("Erro", "Erro", "Erro ao criar conta.");

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

	private void showAlertInfo(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }
}
