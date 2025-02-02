package DAO;

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

	public void deletarItem(Item item) {
		String sql = "DELETE FROM tbItem WHERE codItem = ?";
		PreparedStatement ps = null;

		try {
			ps = Conexao.getConexao().prepareStatement(sql);
			ps.setInt(1, item.getItemId());

			int linhasAfetadas = ps.executeUpdate();
			ps.close();

			if (linhasAfetadas > 0) {
				System.out.println("Item deletado com sucesso!");
			} else {
				System.out.println("Nenhum item foi encontrado com esse ID.");
			}
		} catch (SQLException e) {
			System.out.println("Erro ao deletar o item.");
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

	public void editarItem(Item item) {
		String sql = "UPDATE tbItem SET nome = ?, quantidade_ocup = ?, preco = ?, peso = ?, quantidade_max = ? WHERE codItem = ?";
		PreparedStatement ps = null;
	
		try {
			ps = Conexao.getConexao().prepareStatement(sql);
			ps.setString(1, item.getNome());                 // Nome
			ps.setInt(2, item.getQuantidadeOcupada());       // Quantidade Ocupada
			ps.setDouble(3, item.getPreco());                // Preço
			ps.setDouble(4, item.getPeso());                 // Peso (Adicionado corretamente)
			ps.setInt(5, item.getQuantidadeMaxima());        // Quantidade Máxima
			ps.setInt(6, item.getItemId());                  // ID do item (correto agora)
	
			int linhasAfetadas = ps.executeUpdate();
			ps.close();
	
			if (linhasAfetadas > 0) {
				System.out.println("Item atualizado com sucesso!");
			} else {
				System.out.println("Nenhum item foi encontrado com esse ID.");
			}
		} catch (SQLException e) {
			System.out.println("Erro ao editar o item.");
			e.printStackTrace();
		}
	}

	public int calcularQuantasPizzasPodemSerFeitas(double pesoMassaPizza, double pesoCalabresaPizza, double pesoQueijoPizza) {
     
        List<Item> itens = carregarItensDoBanco();

        double pizzasMassa = 0, pizzasCalabresa = 0, pizzasQueijo = 0;

        for (Item item : itens) {
            if ("Massa".equals(item.getNome())) {
                pizzasMassa = item.getPeso() / pesoMassaPizza;
            } else if ("Calabresa".equals(item.getNome())) {
                pizzasCalabresa = item.getPeso() / pesoCalabresaPizza;
            } else if ("Queijo".equals(item.getNome())) {
                pizzasQueijo = item.getPeso() / pesoQueijoPizza;
            }
        }

        int quantidadePizzas = (int) Math.min(pizzasMassa, Math.min(pizzasCalabresa, pizzasQueijo));
        return quantidadePizzas;
    }
    
    public int getQuantidadePizzasNoEstoque() {
      
        double pesoMassaPizza = 200;  // Exemplo: 200g de massa por pizza
        double pesoCalabresaPizza = 100; // Exemplo: 100g de calabresa por pizza
        double pesoQueijoPizza = 150;  // Exemplo: 150g de queijo por pizza

        // Calcular quantas pizzas podem ser feitas
        int pizzasPossiveis = calcularQuantasPizzasPodemSerFeitas(pesoMassaPizza, pesoCalabresaPizza, pesoQueijoPizza);
        
        System.out.println("Quantas pizzas podem ser feitas com os ingredientes no estoque: " + pizzasPossiveis);
		return pizzasPossiveis;
    }

	public double getTotalGastoDeItens() {
		String sql = "SELECT SUM(preco) AS total_gasto FROM tbItem";
		PreparedStatement ps = null;
		double totalGasto = 0; 
		
		try {
			ps = Conexao.getConexao().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("Itens e total gasto:");
			while (rs.next()) {
				
				totalGasto = rs.getDouble("total_gasto");
				System.out.println(" - " + totalGasto + "g");
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Erro ao calcular total gasto de itens.");
			e.printStackTrace();
		}

		return totalGasto;
	}

	public double getEspacoNoEstoque() {
		String sql = "SELECT SUM(quantidade_ocup) AS total_no_estoque FROM tbItem";
		PreparedStatement ps = null;
		double totalNoEstoque = 0; 
		
		try {
			ps = Conexao.getConexao().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("Itens e total gasto:");
			while (rs.next()) {
				
				totalNoEstoque = rs.getDouble("total_no_estoque");
				System.out.println(" - " + totalNoEstoque + "g");
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Erro ao calcular total gasto de itens.");
			e.printStackTrace();
		}

		return totalNoEstoque;
	}

	
	
}
