package Controllers;

import DAO.ItemDAO;
import Models.Item;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditItemController {

	@FXML
	private TextField nameInput;

	@FXML
	private TextField weightInput;

	@FXML
	private TextField priceInput;

	@FXML
	private TextField ocupedQuantityInput;

	@FXML
	private TextField maxQuantityInput;

	private Item item; // Item a ser editado
	private HomeController homeController; // Referência para atualizar a lista

	public void setItem(Item item, HomeController homeController) {
		this.item = item;
		this.homeController = homeController;

		// Preencher os campos com os dados atuais do item
		nameInput.setText(item.getNome());
		weightInput.setText(String.valueOf(item.getPeso()));
		priceInput.setText(String.valueOf(item.getPreco()));
		ocupedQuantityInput.setText(String.valueOf(item.getQuantidadeOcupada()));
		maxQuantityInput.setText(String.valueOf(item.getQuantidadeMaxima()));
	}

	@FXML
	private void handleSave() {
		item.setNome(nameInput.getText());
		item.setPeso(Double.parseDouble(weightInput.getText()));
		item.setPreco(Double.parseDouble(priceInput.getText()));
		item.setQuantidadeOcupada(Integer.parseInt(ocupedQuantityInput.getText()));
		item.setQuantidadeMaxima(Integer.parseInt(maxQuantityInput.getText()));

		new ItemDAO().editarItem(item);

		homeController.refreshListView();

		Stage stage = (Stage) nameInput.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleCancel() {
		// Fechar o modal sem salvar
		Stage stage = (Stage) nameInput.getScene().getWindow();
		stage.close();
	}
}
