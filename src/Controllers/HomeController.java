package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.ItemDAO;
import Models.Item;
import Services.AdminSession;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private TableView<Item> tbItens;

    @FXML
    private Label pizzasDisponiveisLabel;

    @FXML
    private Label totalGastoLabel;

    @FXML
    private Label totalDisponivelNoEstoqueLabel;

    @FXML
    private Label lucroLabel;

    @FXML
    private ListView<Item> lvItens;

    private List<Item> itens = new ArrayList<>();
    private ObservableList<Item> obsitens;

    @FXML
    private TextField searchInput;

    @FXML
    private void switchToHomePage() throws IOException {
        App.setRoot("home");
    }

    public void refreshListView() {
        obsitens.setAll(new ItemDAO().carregarItensDoBanco());
    }

    @FXML
    private TableColumn<Item, Integer> codigoColumn;
    @FXML
    private TableColumn<Item, String> nomeColumn;
    @FXML
    private TableColumn<Item, Double> pesoColumn;
    @FXML
    private TableColumn<Item, Double> precoColumn;
    @FXML
    private TableColumn<Item, Integer> disponibilidadeColumn;
    @FXML
    private TableColumn<Item, String> acoesColumn;

    private String formatarValor(double valor) {
        return String.format("%.2f", valor);
    }

    @FXML
    public void initialize() {

        ItemDAO itemDAO = new ItemDAO();

        int pizzasDisponiveis = itemDAO.getQuantidadePizzasNoEstoque();
        pizzasDisponiveisLabel.setText(String.valueOf(pizzasDisponiveis));

        double valorPizzas = pizzasDisponiveis * 50;

        double totalGasto = itemDAO.getTotalGastoDeItens();
        totalGastoLabel.setText("R$ " + String.valueOf(formatarValor(totalGasto)));

        double totalDisponivelNoEstoque = itemDAO.getEspacoNoEstoque();
        totalDisponivelNoEstoqueLabel.setText(String.valueOf(totalDisponivelNoEstoque));

        double cache = valorPizzas - totalGasto;
        lucroLabel.setText("R$ " + String.valueOf(formatarValor(cache)));

        itens = itemDAO.carregarItensDoBanco();

        obsitens = FXCollections.observableArrayList(itens);

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filterItems(newValue);
        });

        tbItens.setItems(obsitens);
        System.out.println(obsitens);

        codigoColumn
                .setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getItemId()).asObject());
        nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        pesoColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPeso()).asObject());
        precoColumn
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPreco()).asObject());
        disponibilidadeColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantidadeOcupada()).asObject());
        acoesColumn.setCellValueFactory(cellData -> new SimpleStringProperty("Editar | Excluir"));

        acoesColumn.setCellFactory(column -> {
            return new TableCell<Item, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getIndex() == -1) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        Button editButton = new Button("Editar");
                        Button deleteButton = new Button("Excluir");

                        editButton.setStyle("-fx-background-color: #6C63FF; -fx-text-fill: white;");
                        deleteButton.setStyle("-fx-background-color: #FF4D4D; -fx-text-fill: white;");

                        editButton.setOnAction(event -> {
                            Item itemId = getTableView().getItems().get(getIndex());

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/editItemModal.fxml"));
                                Parent root = loader.load();

                                EditItemController controller = loader.getController();
                                controller.setItem(itemId, HomeController.this);

                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setTitle("Editar Item");
                                stage.setScene(new Scene(root));
                                stage.showAndWait();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        // Lógica para o botão de Excluir
                        deleteButton.setOnAction(event -> {
                            Item itemId = getTableView().getItems().get(getIndex());
                            // Lógica para excluir o itemId
                            ItemDAO itemDAO = new ItemDAO();
                            itemDAO.deletarItem(itemId);
                            obsitens.remove(itemId);
                        });

                        HBox actionButtons = new HBox(10, editButton, deleteButton);
                        setGraphic(actionButtons);
                        setText(null);
                    }
                }
            };
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
        try {
          
            String name = nameInput.getText();
            if (name == null || name.trim().isEmpty()) {
                showInfoAlert("Erro", "Nome inválido", "O campo Nome não pode estar vazio.");
                return;
            }

            if (!(name.equals("Massa") || name.equals("Calabresa")
                    || name.equals("Queijo"))) {
                showInfoAlert("Ingrediente Inválido", "Por favor, digite corretamente o nome do ingrediente.",
                        "Opções: Massa, Calabresa, Queijo");
                return;
            }

            double value, weight;
            int maxQuantity, ocupedQuantity;

            try {
                value = Double.parseDouble(valueInput.getText());
                if (value <= 0) {
                    showInfoAlert("Erro", "Valor inválido", "O preço deve ser maior que zero.");
                    return;
                }
            } catch (NumberFormatException e) {
                showInfoAlert("Erro", "Entrada inválida", "O campo Preço deve conter um numero válido.");
                return;
            }

            try {
                maxQuantity = Integer.parseInt(maxQuanty.getText());
                if (maxQuantity <= 0) {
                    showInfoAlert("Erro", "Quantidade Máxima inválida", "A quantidade máxima deve ser maior que zero.");
                    return;
                }
            } catch (NumberFormatException e) {
                showInfoAlert("Erro", "Entrada inválida", "O campo Quantidade Máxima deve conter um nmero inteiro.");
                return;
            }

            try {
                ocupedQuantity = Integer.parseInt(ocupedQuanty.getText());
                if (ocupedQuantity < 0 || ocupedQuantity > maxQuantity) {
                    showInfoAlert("Erro", "Quantidade Ocupada inválida",
                            "A quantidade ocupada deve estar entre 0 e " + maxQuantity);
                    return;
                }
            } catch (NumberFormatException e) {
                showInfoAlert("Erro", "Entrada inválida", "O campo quantidade Ocupada deve conter um número inteiro.");
                return;
            }

            try {
                weight = Double.parseDouble(weightInput.getText());
                if (weight <= 0) {
                    showInfoAlert("Erro", "Peso inválido", "O peso deve ser maior que zero.");
                    return;
                }
            } catch (NumberFormatException e) {
                showInfoAlert("Erro", "Entrada inválida", "O campo Peso deve conter um número válido.");
                return;
            }

            Item item = new Item(0, name, weight, value, maxQuantity, ocupedQuantity);
            new ItemDAO().cadastrarItem(item);
            obsitens.add(item);

        } catch (Exception e) {
            showInfoAlert("Erro inesperado", "Ocorreu um erro desconhecido", e.getMessage());
            e.printStackTrace(); 
        }
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

        for (Item item : itens) {
            if (item.getNome().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        obsitens.setAll(filteredItems);
    }

    @FXML
    private void switchToReportsPage() throws IOException {
        App.setRoot("reports");
    }

    @FXML
    private void handleLogout() throws IOException {
        try {
            new AdminSession();
            AdminSession.clearSession();

            showInfoAlert(null, "Você será direcionado(a) para a página de login.", null);

            App.setRoot("loginPage");
        } catch (IOException e) {
            showInfoAlert(null, "Tente fazer o logout novamente.", null);
        }

    }

    private void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }

}
