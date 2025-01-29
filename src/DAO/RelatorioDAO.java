package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Item;
import Models.Relatorio;

public class RelatorioDAO {

	 public void gerarRelatorio(Item item) {
        String sql = "INSERT INTO tbRelatorio (nome, quantidade_ocup, preco, peso, quantidade_max) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getNome());
            ps.setInt(2, item.getQuantidadeOcupada());
            ps.setDouble(3, item.getPreco());
            ps.setDouble(4, item.getPeso());
            ps.setInt(5, item.getQuantidadeMaxima());

            ps.executeUpdate();
            System.out.println("Relatório salvo no banco com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar relatório no banco!");
            e.printStackTrace();
        }
    }

	public void gerarRelatorioAPartirDosItens() {
    String sqlSelect = "SELECT nome, quantidade_ocup, preco, peso, quantidade_max FROM tbItem";
    String sqlInsert = "INSERT INTO tbRelatorios (total_gasto, lucro_total, espaco_estoque_atualmente, quantidade_pizzas, cod_estoque) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
         ResultSet rs = psSelect.executeQuery();
         PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {

        double totalGasto = 0;
        double lucroTotal = 0;
        double espacoOcupado = 0;
        int totalPizzas = 0;
        int codEstoque = 1; // Defina como obter esse código corretamente

        // Percorre os itens do banco e calcula os dados do relatório
        while (rs.next()) {
            int quantidade = rs.getInt("quantidade_ocup");
            double preco = rs.getDouble("preco");
            double peso = rs.getDouble("peso");

            totalGasto += quantidade * preco;
            lucroTotal += (quantidade * preco) * 1.3; // Supondo 30% de margem de lucro
            espacoOcupado += quantidade * peso;
            totalPizzas += quantidade;
        }

        // Insere o relatório no banco
        psInsert.setDouble(1, totalGasto);
        psInsert.setDouble(2, lucroTotal);
        psInsert.setDouble(3, espacoOcupado);
        psInsert.setInt(4, totalPizzas);
        psInsert.setInt(5, codEstoque);

        psInsert.executeUpdate();
        System.out.println("Relatório gerado com sucesso!");

    } catch (SQLException e) {
        System.out.println("Erro ao gerar relatório: " + e.getMessage());
        e.printStackTrace();
    }
}


	public List<Relatorio> carregarRelatoriosDoBanco() {
		List<Relatorio> relatorios = new ArrayList<>();
		String sql = "SELECT * FROM TbRelatorios";
		PreparedStatement ps = null;
		try {
			ps = Conexao.getConexao().prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				double totalGasto = resultSet.getDouble("total_gasto");
				double lucroTotal = resultSet.getDouble("lucro_total");
				double espacoEstoqueAtualmente = resultSet.getDouble("espaco_estoque_atualmente");
				int quantidadeDePizzas = resultSet.getInt("quantidade_pizzas");
				int codEstoque = resultSet.getInt("cod_estoque");
				;
				String dataEmissao = resultSet.getString("createAt");

				System.out.println(dataEmissao);

				relatorios.add(new Relatorio(id, totalGasto, lucroTotal, quantidadeDePizzas, espacoEstoqueAtualmente,
						codEstoque, dataEmissao));
			}

		} catch (SQLException e) {
			System.out.println("Erro ao carregar relatorios do banco: " + e.getMessage());
			e.printStackTrace();
		}

		return relatorios;
	}
}
