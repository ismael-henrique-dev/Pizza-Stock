package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.ItemDAO;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class HomeController {
    @FXML
    private ListView<Item> lvItens;

    private List<Item> itens = new ArrayList<>();
    private ObservableList<Item> obsitens;

    @FXML
    private TextField searchInput;

    @FXML
    public void initialize() {

        // Preenchendo a lista de itens
        ItemDAO itemDAO = new ItemDAO();

        // Recuperando a lista de categorias do banco
        itens = itemDAO.carregarItensDoBanco();

        // Convertendo para ObservableList
        obsitens = FXCollections.observableArrayList(itens);

        // Vinculando o ObservableList ao ListView
        lvItens.setItems(obsitens);

        lvItens.setCellFactory(param -> new ListCell<Item>() {
            private final HBox container = new HBox();
            private final HBox dataContainer = new HBox(); // Container para os dados da categoria
            private final Button editButton = new Button("Editar");
            private final Button deleteButton = new Button("Excluir");

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Limpa o container para evitar duplicações
                    container.getChildren().clear();
                    dataContainer.getChildren().clear();

                    // Configurando os botões
                    editButton.setStyle(
                            "-fx-background-color: #6C63FF; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px; -fx-spacing: 40px;");
                    deleteButton.setStyle(
                            "-fx-background-color: #FF4D4D; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px;");

                    editButton.setOnAction(event -> {
                        System.out.println("Editar: " + item.getNome());
                        // Lógica para edição
                    });

                    deleteButton.setOnAction(event -> {
                        lvItens.getSelectionModel().select(item); // Seleciona o item
                        handleDeleteItem();
                        // Lógica para exclusão
                    });

                    // Exibindo os dados da categoria
                    Insets insets = new Insets(0, 320, 0, 0);

                    dataContainer.setSpacing(40);
                    dataContainer.setPadding(insets);
                    dataContainer.getChildren().addAll(new javafx.scene.control.Label("" + item.getItemId()),
                            new javafx.scene.control.Label("      " + item.getNome()),
                            new javafx.scene.control.Label("      " + item.getPeso()),
                            new javafx.scene.control.Label("      " + item.getPreco()),
                            new javafx.scene.control.Label("      " + item.getQuantidadeOcupada()));

                    // Adicionando os elementos ao container principal
                    container.setSpacing(10);
                    container.getChildren().addAll(dataContainer, editButton, deleteButton);

                    setText(null); // Limpa o texto padrão
                    setGraphic(container); // Define o layout da célula
                }
            }
        });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filterItems(newValue);
        });

    }

    @FXML
    private TextField nameInput;

    @FXML
    private TextField valueInput;

    @FXML
    private TextField ocupedQuanty;

    @FXML
    private TextField maxQuanty;

    @FXML
    private TextField weightInput;

    @FXML
    private void handleCreateItem() {
        String name = nameInput.getText();
        double value = Double.parseDouble(valueInput.getText());
        int maxQuantity = Integer.parseInt(maxQuanty.getText());
        int ocupedQuantity = Integer.parseInt(ocupedQuanty.getText());
        double weight = Double.parseDouble(weightInput.getText());

        Item item = new Item(0, name, weight, value, maxQuantity, ocupedQuantity);
        item.setNome(name);
        item.setQuantidadeOcupada(ocupedQuantity);
        item.setPreco(value);
        item.setPeso(weight);
        item.setQuantidadeMaxima(maxQuantity);

        new ItemDAO().cadastrarItem(item);
        obsitens.add(item);  
    }

    @FXML
    private void handleDeleteItem() {
        Item itemSelecionado = lvItens.getSelectionModel().getSelectedItem();

        if (itemSelecionado != null) {
            ItemDAO itemDAO = new ItemDAO();
            itemDAO.deletarItem(itemSelecionado);

            obsitens.remove(itemSelecionado);
            lvItens.refresh();

            System.out.println("Item deletado: " + itemSelecionado.getNome());
        } else {
            System.out.println("Nenhum item selecionado.");
        }
    }

    private void filterItems(String searchQuery) {
        List<Item> filteredItems = new ArrayList<>();

        // Filtrando os itens pela pesquisa
        for (Item item : itens) {
            if (item.getNome().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        // Atualizando o ObservableList
        obsitens.setAll(filteredItems);
    }

    @FXML
	private void switchToReportsPage() throws IOException {
		App.setRoot("reports");
	}

}
