package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.AdminDAO;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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

    @FXML
    public void initialize() {

        // Preenchendo a lista de itens
        ItemDAO itemDAO = new ItemDAO();

        int pizzasDisponiveis = itemDAO.getQuantidadePizzasNoEstoque();
        pizzasDisponiveisLabel.setText(String.valueOf(pizzasDisponiveis));

        double valorPizzas = pizzasDisponiveis * 50;

        double totalGasto = itemDAO.getTotalGastoDeItens();
        totalGastoLabel.setText(String.valueOf(totalGasto));

        double totalDisponivelNoEstoque = itemDAO.getEspacoNoEstoque();
        totalDisponivelNoEstoqueLabel.setText(String.valueOf(totalDisponivelNoEstoque));

        double cache = valorPizzas - totalGasto;
        lucroLabel.setText(String.valueOf(cache));

        // Recuperando a lista de categorias do banco
        itens = itemDAO.carregarItensDoBanco();

        // Convertendo para ObservableList
        obsitens = FXCollections.observableArrayList(itens);

        // Vinculando o ObservableList ao ListView
        // lvItens.setItems(obsitens);

        // lvItens.setCellFactory(param -> new ListCell<Item>() {
        // private final HBox container = new HBox();
        // private final HBox dataContainer = new HBox(); // Container para os dados da
        // categoria
        // private final Button editButton = new Button("Editar");
        // private final Button deleteButton = new Button("Excluir");

        // @Override
        // protected void updateItem(Item item, boolean empty) {
        // super.updateItem(item, empty);

        // if (empty || item == null) {
        // setText(null);
        // setGraphic(null);
        // } else {
        // // Limpa o container para evitar duplicações
        // container.getChildren().clear();
        // dataContainer.getChildren().clear();

        // // Configurando os botões
        // editButton.setStyle(
        // "-fx-background-color: #6C63FF; -fx-text-fill: white; -fx-font-size: 14px;
        // -fx-padding: 5px; -fx-spacing: 40px;");
        // deleteButton.setStyle(
        // "-fx-background-color: #FF4D4D; -fx-text-fill: white; -fx-font-size: 14px;
        // -fx-padding: 5px;");

        // editButton.setOnAction(event -> {
        // // System.out.println("Editar: " + item.getNome());
        // try {
        // FXMLLoader loader = new
        // FXMLLoader(getClass().getResource("/View/editItemModal.fxml"));
        // Parent root = loader.load();

        // EditItemController controller = loader.getController();
        // controller.setItem(item, HomeController.this); // Passa o item para o modal

        // Stage stage = new Stage();
        // stage.initModality(Modality.APPLICATION_MODAL);
        // stage.setTitle("Editar Item");
        // stage.setScene(new Scene(root));
        // stage.showAndWait();
        // } catch (IOException e) {
        // e.printStackTrace();
        // System.out.println("Erro ao abrir modal");
        // }
        // });

        // deleteButton.setOnAction(event -> {
        // lvItens.getSelectionModel().select(item); // Seleciona o item
        // handleDeleteItem();
        // // Lógica para exclusão
        // });

        // // Exibindo os dados da categoria
        // // Insets insets = new Insets(0, 320, 0, 0);

        // dataContainer.setSpacing(40);
        // // dataContainer.setPadding(insets);
        // dataContainer.getChildren().addAll(new javafx.scene.control.Label("" +
        // item.getItemId()),
        // new javafx.scene.control.Label(" " + item.getNome()),
        // new javafx.scene.control.Label(" " + item.getPeso()),
        // new javafx.scene.control.Label(" " + item.getPreco()),
        // new javafx.scene.control.Label(" " + item.getQuantidadeOcupada()));

        // // Adicionando os elementos ao container principal
        // container.setSpacing(10);
        // container.getChildren().addAll(dataContainer, editButton, deleteButton);

        // setText(null); // Limpa o texto padrão
        // setGraphic(container); // Define o layout da célula
        // }
        // }
        // });

        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filterItems(newValue);
        });

        // código da tabela aqui (não remova o do listView que eu quero fazer um teste)

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
        // Dentro do método initialize()

        acoesColumn.setCellFactory(column -> {
            return new TableCell<Item, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getIndex() == -1) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Criação dos botões
                        Button editButton = new Button("Editar");
                        Button deleteButton = new Button("Excluir");

                        // Estilo dos botões (opcional)
                        editButton.setStyle("-fx-background-color: #6C63FF; -fx-text-fill: white;");
                        deleteButton.setStyle("-fx-background-color: #FF4D4D; -fx-text-fill: white;");

                        // Lógica para o botão de Editar
                        editButton.setOnAction(event -> {
                            Item ITEM1 = getTableView().getItems().get(getIndex());
                            // Lógica para editar o ITEM1
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/editItemModal.fxml"));
                                Parent root = loader.load();

                                EditItemController controller = loader.getController();
                                controller.setItem(ITEM1, HomeController.this); // Passando o ITEM1 para o modal

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
                            Item ITEM1 = getTableView().getItems().get(getIndex());
                            // Lógica para excluir o ITEM1
                            ItemDAO itemDAO = new ItemDAO();
                            itemDAO.deletarItem(ITEM1);
                            obsitens.remove(ITEM1);
                        });

                        // Adicionando os botões na célula
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

    @FXML
    private void handleLogout() throws IOException {
       new AdminSession().clearSession();
       App.setRoot("loginPage");

    }

}
