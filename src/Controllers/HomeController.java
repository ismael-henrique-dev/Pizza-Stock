package Controllers;

import java.util.ArrayList;
import java.util.List;

import Models.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HomeController {
    @FXML
    private ListView<Categoria> lvCateogorias;

    private List<Categoria> categorias = new ArrayList<>();
    private ObservableList<Categoria> obsCategorias;

    @FXML
    public void initialize() {
        // Preenchendo a lista de categorias
        categorias.add(new Categoria(1, "Categoria 1", 12.4, 123.1, 4));
        categorias.add(new Categoria(2, "Categoria 2", 12.4, 123.1, 4));
        categorias.add(new Categoria(3, "Categoria 3", 12.4, 123.1, 4));

        // Convertendo para ObservableList
        obsCategorias = FXCollections.observableArrayList(categorias);

        // Vinculando o ObservableList ao ListView
        lvCateogorias.setItems(obsCategorias);

        // Customizando as células para incluir botões
        lvCateogorias.setCellFactory(param -> new ListCell<Categoria>() {
            private final HBox container = new HBox();
            private final HBox dataContainer = new HBox(); // Container para os dados da categoria
            private final Button editButton = new Button("Editar");
            private final Button deleteButton = new Button("Excluir");

            @Override
            protected void updateItem(Categoria item, boolean empty) {
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
                        System.out.println("Excluir: " + item.getNome());
                        // Lógica para exclusão
                    });

                    // Exibindo os dados da categoria
                    Insets insets = new Insets(0, 320, 0, 0);

                    dataContainer.setSpacing(40);
                    dataContainer.setPadding(insets);
                    dataContainer.getChildren().addAll(new javafx.scene.control.Label("" + item.getId()),
                            new javafx.scene.control.Label("      " + item.getNome()),
                            new javafx.scene.control.Label("      " + item.getPeso()),
                            new javafx.scene.control.Label("      " + item.getPreco()),
                            new javafx.scene.control.Label("      " + item.getDisponibilidade()));

                    // Adicionando os elementos ao container principal
                    container.setSpacing(10);
                    container.getChildren().addAll(dataContainer, editButton, deleteButton);

                    setText(null); // Limpa o texto padrão
                    setGraphic(container); // Define o layout da célula
                }
            }
        });

    }
}
