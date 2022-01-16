package br.com.pubfuture.criar_banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;


public class Conn {
	
	private Conn() {}
	
	public static Connection getConnect() {
		try {
			final String URL = "jdbc:mysql://localhost:3306/pub_future?"
					+ "verifyServerCertificate=false&useSSL=true";
			final String USUARIO = "root";
			final String SENHA = "18100707n&#";
			
			return DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static PreparedStatement pstmt(Connection con, String sql) {
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			return pstmt;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void createDataBase() {
		Connection con;
		try {
			final String URL = "jdbc:mysql://localhost:3306?"
					+ "verifyServerCertificate=false&useSSL=true";
			final String USUARIO = "root";
			final String SENHA = "18100707n&#";
			con = DriverManager.getConnection(URL, USUARIO, SENHA);
			Statement stmt = stmt(con);
			stmt.execute("create database if not exists pub_future");
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Statement stmt(Connection connection) {
		try {
			Statement stmt = connection.createStatement();
			return stmt;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void createTable(String sql_string) {
		try {
			String sql = sql_string;
			Connection con = getConnect();
			Statement stmt = stmt(con);
			stmt.execute(sql);
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void start() {
        createDataBase();
		
		createTable("CREATE TABLE IF NOT EXISTS tipo_conta("
				+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "t_conta VARCHAR(40) NOT NULL)");
		
		createTable("CREATE TABLE IF NOT EXISTS tipo_receita("
				+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "t_receita VARCHAR(40) NOT NULL)");
		
		createTable("CREATE TABLE IF NOT EXISTS tipo_despesa("
				+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "t_despesa VARCHAR(40) NOT NULL)");
		
		createTable("CREATE TABLE IF NOT EXISTS conta("
				+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "titular VARCHAR(50) NOT NULL,"
				+ "saldo DECIMAL(9, 2) DEFAULT '0.00' ,"
				+ "inst_financeira VARCHAR(50) NOT NULL,"
				+ "tipo_conta INT UNSIGNED NOT NULL,"
				+ "FOREIGN KEY (tipo_conta) REFERENCES tipo_conta (id))");
				
		
		createTable("CREATE TABLE IF NOT EXISTS despesas("
				+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "valor_desp DECIMAL(9, 2) NOT NULL,"
				+ "data_inclusao VARCHAR(11) NOT NULL,"
				+ "data_venc VARCHAR(11) DEFAULT NULL,"
				+ "tipo_desp INT UNSIGNED NOT NULL,"
				+ "conta INT UNSIGNED NOT NULL,"
				+ "FOREIGN KEY (conta) REFERENCES conta (id),"
				+ "FOREIGN KEY (tipo_desp) REFERENCES tipo_despesa (id))");
		
		createTable("CREATE TABLE IF NOT EXISTS receitas("
				+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "valor_rec DECIMAL(9, 2) NOT NULL,"
				+ "data_inclusao VARCHAR(11) NOT NULL,"
				+ "data_rec_esp VARCHAR(11) DEFAULT NULL,"
				+ "tipo_rec INT UNSIGNED NOT NULL,"
				+ "conta INT UNSIGNED NOT NULL,"
				+ "FOREIGN KEY (conta) REFERENCES conta (id),"
				+ "FOREIGN KEY (tipo_rec) REFERENCES tipo_receita (id))");
		
		popularTabelaTipoContas();
		popularTabelaTipoDespesas();
		popularTabelaTipoReceitas();
			
	}
	
	private static void popularTabelaTipoContas() {
		boolean resul = Funn.conTipos("tipo_conta");
		if(!resul == true) {
		List<String> valores = Arrays.asList("Carteira", "Conta Corrente",
				"Poupança", "Outros");
		Connection con = getConnect();
		String sql = "INSERT INTO tipo_conta(t_conta) VALUES(?)";
		PreparedStatement pstmt = pstmt(con, sql);
		for(String tipo: valores) {
			try {
				pstmt.setString(1,  tipo);
				pstmt.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
			}
		}else{
			return;}
		}
	
	
	private static void popularTabelaTipoDespesas() {
		boolean resul = Funn.conTipos("tipo_despesa");
		if(!resul == true) {
		List<String> valores = Arrays.asList("Alimentação", "Educação",
				"Lazer", "Moradia", "Vestuário", "Saúde", "Transporte",
				"Energia elétrica", "Água", "Internet", "Combustível", "Outros");
		Connection con = getConnect();
		String sql = "INSERT INTO tipo_despesa(t_despesa) VALUES(?)";
		PreparedStatement pstmt = pstmt(con, sql);
		for(String tipo: valores) {
			try {
				pstmt.setString(1,  tipo);
				pstmt.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		}else{
			return;}
		}
	
	
	private static void popularTabelaTipoReceitas() {
		boolean resul = Funn.conTipos("tipo_receita");
		if(!resul == true) {
		List<String> valores = Arrays.asList("Salário", "Presente",
				"Prêmio", "Doação", "Empréstimo", "Outros");
		Connection con = getConnect();
		String sql = "INSERT INTO tipo_receita(t_receita) VALUES(?)";
		PreparedStatement pstmt = pstmt(con, sql);
		for(String tipo: valores) {
			try {
				pstmt.setString(1,  tipo);
				pstmt.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		}else{
				return;}
		}
}
