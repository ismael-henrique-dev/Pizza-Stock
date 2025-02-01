package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import DAO.RelatorioDAO;
import Models.Relatorio;

public class ReportController {

    @FXML
    private Label totalGastoLabel;
    @FXML
    private Label lucroTotalLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label quantidadePizzasLabel;
    @FXML
    private Label espacoEstoqueLabel;

    private RelatorioDAO relatorioDAO;

    public void initialize() {
        relatorioDAO = new RelatorioDAO();
    }

    public void carregarRelatorio(int idRelatorio) {
        Relatorio relatorio = relatorioDAO.carregarRelatorio(idRelatorio);

        if (relatorio != null) {
            totalGastoLabel.setText("Total Gasto: R$ " + formatarValor(relatorio.getTotalGasto()));
            lucroTotalLabel.setText("Lucro Total: R$ " + formatarValor(relatorio.getLucroTotal()));
            totalLabel.setText("Total: R$ " + formatarValor(relatorio.getLucroTotal() - relatorio.getTotalGasto()));
            quantidadePizzasLabel.setText("Qtd. Pizzas: " + relatorio.getQuantidadePizzas());
            espacoEstoqueLabel.setText("Espaço Estoque: " + relatorio.getEspacoEstoqueAtualmente() + " m²");
        } else {
            System.out.println("Nenhum relatório encontrado para o ID: " + idRelatorio);
        }
    }

    private String formatarValor(double valor) {
        return String.format("%.2f", valor);
    }
}
