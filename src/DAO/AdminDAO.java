package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Models.Admin;

public class AdminDAO {

	public void cadastrarAdmin(Admin admin) {
		String sql = "insert into tbAdmin (nome, login, senha, email) values (?, ?, ?, ?)";

		PreparedStatement ps = null;

		try {
			ps = Conexao.getConexao().prepareStatement(sql);
			ps.setString(1, admin.getNome());
			ps.setString(2, admin.getLogin());
			ps.setString(3, admin.getSenha());
			ps.setString(4, admin.getEmail());

			ps.execute();
			ps.close();
			System.out.println("Conexão realizada");
		} catch (SQLException e) {
			System.out.println("Conexão -- Erro");
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
