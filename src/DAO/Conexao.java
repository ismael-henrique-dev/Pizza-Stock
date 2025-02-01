package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static final String URL = "jdbc:mysql://localhost:3306/javaConnectionTeste";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Connection getConexao() {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Erro ao se comunicar com MySQL");
			e.printStackTrace();
			return null;
		}
	}
}
