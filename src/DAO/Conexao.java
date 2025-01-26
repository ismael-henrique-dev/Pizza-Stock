package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static final String url = "jdbc:mysql://localhost:3306/javaConnectionTeste";
	private static final String user = "root";
	private static final String password = "";

	private static Connection conn;

	public static Connection getConexao() {
		try {
			if (conn == null) {
				conn = DriverManager.getConnection(url, user, password);
				System.out.println("Conexão estabelecida");

				return conn;
			} else {
				return conn;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao se comunicar com mysql");
			e.printStackTrace();
			return null;
		}
	}
}
