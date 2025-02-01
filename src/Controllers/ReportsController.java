package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.RelatorioDAO;
import Models.Relatorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReportsController {

	private HomeController homeController;

	@FXML
	private void switchToHomePage() throws IOException {
		App.setRoot("home");
	}

	@FXML
	private ListView<Relatorio> lvRelatorios;

	private List<Relatorio> relatorios = new ArrayList<>();
	private ObservableList<Relatorio> obsitens;

	@FXML
	public void initialize() {

		// Preenchendo a lista de itens

		RelatorioDAO relatorioDAO = new RelatorioDAO();

		// Recuperando a lista de categorias do banco

		relatorios = relatorioDAO.carregarRelatoriosDoBanco();

		// Convertendo para ObservableList
		obsitens = FXCollections.observableArrayList(relatorios);

		// Vinculando o ObservableList ao ListView
		lvRelatorios.setItems(obsitens);

		lvRelatorios.setCellFactory(param -> new ListCell<Relatorio>() {
			private final HBox container = new HBox();
			private final HBox dataContainer = new HBox(); // Container para os dados da categoria
			private final Button openModalButton = new Button("Abrir Relatório");

			@Override
			protected void updateItem(Relatorio item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					// Limpa o container para evitar duplicações
					container.getChildren().clear();
					dataContainer.getChildren().clear();

					// Configurando os botões
					openModalButton.setStyle(
							"-fx-background-color: #6C63FF; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px; -fx-spacing: 40px;");

					openModalButton.setOnAction(event -> {
						System.out.println("Abrindo relatorio do id: " + item.getId());
						abrirRelatorio(item.getId());
						// Lógica para edição
					});

					// Exibindo os dados da categoria
					Insets insets = new Insets(0, 320, 0, 0);

					dataContainer.setSpacing(40);
					dataContainer.setPadding(insets);
					dataContainer.getChildren().addAll(new javafx.scene.control.Label("" + item.getId()),
							new javafx.scene.control.Label("      " + item.getEspacoEstoqueAtualmente()),
							new javafx.scene.control.Label("      " + item.getTotalGasto()),
							new javafx.scene.control.Label("      " + item.getLucroTotal()),
							new javafx.scene.control.Label("      " + item.getDataEmissao()));

					// Adicionando os elementos ao container principal
					container.setSpacing(10);
					container.getChildren().addAll(dataContainer, openModalButton);

					setText(null); // Limpa o texto padrão
					setGraphic(container); // Define o layout da célula
				}
			}
		});

	}

	private void abrirRelatorio(int idRelatorio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/reportDatailsModal.fxml"));
            Parent root = loader.load();

            ReportController reportController = loader.getController();
            reportController.carregarRelatorio(idRelatorio); // Passa o ID para carregar os detalhes

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Detalhes do Relatório");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@FXML
	public void handleGenarateReport() {
		RelatorioDAO relatorioDAO = new RelatorioDAO();

		// Gera o relatório a partir dos itens no banco
		relatorioDAO.gerarRelatorioAPartirDosItens();
		System.out.println("Relatório gerado, atualizando lista...");

		// Carrega os relatórios atualizados do banco
		homeController.refreshListView();

		// if (novosRelatorios.isEmpty()) {
		// System.out.println("Nenhum relatório encontrado! Verifique a inserção no
		// banco.");
		// } else {
		// System.out.println("Lista de relatórios atualizada com " +
		// novosRelatorios.size() + " registros.");
		// }

		// // Atualiza corretamente a ObservableList

		// homeController.refreshListView();
	}

}
