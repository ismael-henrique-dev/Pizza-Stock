package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Item;

public class ItemDAO {
	public void cadastrarItem(Item item) {
		String sql = "insert into tbItem (nome,  quantidade_ocup , preco, peso, quantidade_max) values (?, ?, ?, ?, ?)";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConexao().prepareStatement(sql);

			ps.setString(1, item.getNome());
			ps.setString(2, String.valueOf(item.getQuantidadeOcupada()));
			ps.setString(3, String.valueOf(item.getPreco()));
			ps.setString(4, String.valueOf(item.getPeso()));
			ps.setString(5, String.valueOf(item.getQuantidadeMaxima()));

			ps.execute();
			ps.close();
			System.out.println("Item criado!");
		} catch (SQLException e) {
			System.out.println("Conexão -- Erro");
			e.printStackTrace();
		}
	}

	public List<Item> carregarItensDoBanco() {
		List<Item> itens = new ArrayList<>();
		String sql = "SELECT codItem, nome, peso, preco, quantidade_ocup, quantidade_max  FROM tbItem";
		PreparedStatement ps = null;
		try {
			ps = Conexao.getConexao().prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("codItem");
				String nome = resultSet.getString("nome");
				double peso = resultSet.getDouble("peso");
				double preco = resultSet.getDouble("preco");
				int quantidade_min = resultSet.getInt("quantidade_max");
				int quantidade_max = resultSet.getInt("quantidade_max");

				itens.add(new Item(id, nome, peso, preco, quantidade_max, quantidade_min));
			}

		} catch (SQLException e) {
			System.out.println("Erro ao carregar itens do banco: " + e.getMessage());
			e.printStackTrace();
		}

		return itens;
	}
}
