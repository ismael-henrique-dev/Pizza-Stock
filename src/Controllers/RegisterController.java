package Controllers;

import java.io.IOException;

import DAO.AdminDAO;
import Models.Admin;
import javafx.fxml.FXML;
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
		String name = nameInput.getText();
		String email = emailInput.getText();
		String password = passwordInput.getText();

		Admin admin = new Admin();
		admin.setNome(name);
		admin.setLogin(name);
		admin.setSenha(password);
		admin.setEmail(email);

		new AdminDAO().cadastrarAdmin(admin);
		System.out.println("Admin registrado");
	}
}
