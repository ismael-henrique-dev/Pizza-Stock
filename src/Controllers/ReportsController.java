package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.RelatorioDAO;
import Models.Relatorio;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReportsController {

	@FXML
	private TableView<Relatorio> tbRelatorios;

	@FXML
	private TableColumn<Relatorio, Integer> codigoColumn;

	@FXML
	private TableColumn<Relatorio, Double> quantidadeColumn;

	@FXML
	private TableColumn<Relatorio, Double> totalColumn;

	@FXML
	private TableColumn<Relatorio, Double> lucroColumn;

	@FXML
	private TableColumn<Relatorio, String> periodoColumn;

	@FXML
	private TableColumn<Relatorio, String> acoesColumn;

	@FXML
	private void switchToHomePage() throws IOException {
		App.setRoot("home");
	}

	private List<Relatorio> relatorios = new ArrayList<>();
	private ObservableList<Relatorio> obsitens;

	@FXML
	public void initialize() {

		// Preenchendo a lista de itens
		RelatorioDAO relatorioDAO = new RelatorioDAO();

		// Recuperando a lista de categorias do banc
		relatorios = relatorioDAO.carregarRelatoriosDoBanco();

		// Convertendo para ObservableList
		obsitens = FXCollections.observableArrayList(relatorios);
		tbRelatorios.setItems(obsitens);

		codigoColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		quantidadeColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().getEspacoEstoqueAtualmente()).asObject());
		totalColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalGasto()).asObject());
		lucroColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().getLucroTotal()).asObject());
		periodoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataEmissao()));
		acoesColumn.setCellValueFactory(cellData -> new SimpleStringProperty("Editar | Excluir"));

		acoesColumn.setCellFactory(column -> {
			return new TableCell<Relatorio, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (empty || getIndex() == -1) {
						setGraphic(null);
						setText(null);
					} else {
						
						Button openButton = new Button("Abrir Relatório");

						// Estilo dos botões (opcional)
						openButton.setStyle("-fx-background-color: #6C63FF; -fx-text-fill: white;");

						openButton.setOnAction(event -> {
							Relatorio relatorio = getTableView().getItems().get(getIndex());

							abrirRelatorio(relatorio.getId());
						});

						HBox actionButtons = new HBox(10, openButton);
						setGraphic(actionButtons);
						setText(null);
					}
				}
			};
		});
	}

	private void abrirRelatorio(int idRelatorio) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/reportDatailsModal.fxml"));
			Parent root = loader.load();

			ReportController reportController = loader.getController();
			reportController.carregarRelatorio(idRelatorio); 

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

		relatorioDAO.gerarRelatorioAPartirDosItens();
		System.out.println("Relatório gerado, atualizando lista...");
	}

	@FXML
    private void handleLogout() throws IOException {
       new AdminSession().clearSession();
       App.setRoot("loginPage");

    }

}
