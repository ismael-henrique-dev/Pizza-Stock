package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			e.printStackTrace();
		}
	}

	public void loginAdmin(Admin admin) {
		String sql = "SELECT id FROM tbAdmin WHERE email = ? AND senha = ?";
		String sqlSession = "INSERT INTO tbSessions (idAdmin) VALUES (?)";
		int idAdmin = 0;

		PreparedStatement psLogin = null;
		PreparedStatement psSession = null;

		try {
			// Consulta para verificar o login
			psLogin = Conexao.getConexao().prepareStatement(sql);
			psLogin.setString(1, admin.getEmail());
			psLogin.setString(2, admin.getSenha());

			ResultSet rs = psLogin.executeQuery();

			if (rs.next()) {

				idAdmin = rs.getInt("id");
				System.out.println("Login bem-sucedido. ID do Admin: " + idAdmin);

				psSession = Conexao.getConexao().prepareStatement(sqlSession);
				psSession.setInt(1, idAdmin);
				psSession.executeUpdate();
				System.out.println("Sessão criada para o Admin ID: " + idAdmin);
			} else {
				System.out.println("Credenciais inválidas. Nenhum admin encontrado.");
			}

		} catch (SQLException e) {
			System.out.println("Erro ao fazer login ou criar sessão");
			e.printStackTrace();
		} finally {
			try {
				if (psLogin != null)
					psLogin.close();
				if (psSession != null)
					psSession.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
