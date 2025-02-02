package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Models.Relatorio;

public class RelatorioDAO {

	public static Timestamp getUltimaDataRelatorio() {
		Timestamp ultimaData = null;
		String sql = "SELECT createAt FROM TbRelatorios ORDER BY createAt DESC LIMIT 1";

		try (Connection conn = Conexao.getConexao();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				ultimaData = rs.getTimestamp("createAt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ultimaData;
	}

	public void gerarRelatorioAPartirDosItens() {
		ItemDAO itemDAO = new ItemDAO();
		Timestamp ultimaData = getUltimaDataRelatorio();
		// String sqlSelect = "SELECT quantidade_ocup, preco, peso FROM tbItem";
		String sqlInsert = "INSERT INTO TbRelatorios (total_gasto, lucro_total, espaco_estoque_atualmente, quantidade_pizzas, cod_estoque, periodo_inicio) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			Connection conn = Conexao.getConexao();
			int pizzasDisponiveis = itemDAO.getQuantidadePizzasNoEstoque();
			double valorPizzas = pizzasDisponiveis * 50;
			double totalGasto = itemDAO.getTotalGastoDeItens();
			double totalDisponivelNoEstoque = itemDAO.getEspacoNoEstoque();
			double cache = valorPizzas - totalGasto;

			if (pizzasDisponiveis > 0) { // Só insere se houver dados válidos
				try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
					psInsert.setDouble(1, totalGasto);
					psInsert.setDouble(2, cache);
					psInsert.setDouble(3, totalDisponivelNoEstoque);
					psInsert.setInt(4, pizzasDisponiveis);
					psInsert.setInt(5, 1);
					psInsert.setTimestamp(6, ultimaData);

					psInsert.executeUpdate();
					System.out.println("Relatório gerado com sucesso!");
				}
			} else {
				System.out.println("Nenhum dado encontrado para gerar relatório.");
			}

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

	public Relatorio carregarRelatorio(int idRelatorio) {
		Relatorio relatorio = null;
		String query = "SELECT * FROM TbRelatorios WHERE id = ?";

		try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, idRelatorio);

			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) {
					int id = resultSet.getInt("id");
					double totalGasto = resultSet.getDouble("total_gasto");
					double lucroTotal = resultSet.getDouble("lucro_total");
					double espacoEstoqueAtualmente = resultSet.getDouble("espaco_estoque_atualmente");
					int quantidadeDePizzas = resultSet.getInt("quantidade_pizzas");
					int codEstoque = resultSet.getInt("cod_estoque");
					String dataEmissao = resultSet.getString("createAt");

					relatorio = new Relatorio(id, totalGasto, lucroTotal, quantidadeDePizzas, espacoEstoqueAtualmente,
							codEstoque, dataEmissao);
				}
			}

		} catch (SQLException e) {
			System.out.println("Erro ao buscar o relatório pelo ID: " + e.getMessage());
			e.printStackTrace();
		}

		return relatorio;
	}

}
