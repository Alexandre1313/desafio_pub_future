package br.com.pubfuture.criar_banco;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

public class Funn {
	
	private static String dataDeHoje() {
		Date dataDeHoje = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
		String dataFormatada2 = formatador.format(dataDeHoje);
		return dataFormatada2;
	}
	
	public static void inserirDespesa(double valor_desp, String data_venc,
			int tipo_desp, int conta) {
		String dataDeHoje = dataDeHoje();
		Connection con = Conn.getConnect();
		String sql = "INSERT INTO despesas(valor_desp, data_inclusao, data_venc,"
				+ "tipo_desp, conta) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement pstmt = Conn.pstmt(con, sql);
		try {
			pstmt.setDouble(1, valor_desp);
			pstmt.setString(2, dataDeHoje);
			pstmt.setString(3, data_venc);
			pstmt.setInt(4, tipo_desp);
			pstmt.setInt(5, conta);
			pstmt.execute();
			setSaldo(con, conta);
			System.out.println("Despesa incluida com sucesso!");
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
		
	public static void inserirTipoConta(String t_conta) {
		Connection con = Conn.getConnect();
		String sql = "INSERT INTO tipo_conta(t_conta) VALUES(?)";
		PreparedStatement pstmt = Conn.pstmt(con, sql);
		try {
			pstmt.setString(1, t_conta);
			pstmt.execute();
			System.out.println("Tipo de conta incluida com sucesso!");
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static void inserirConta(String titular, String inst_financeira, int tipo_conta) {
		Connection con = Conn.getConnect();
		String sql = "INSERT INTO conta(titular, inst_financeira, tipo_conta) VALUES(?, ?, ?)";
		PreparedStatement pstmt = Conn.pstmt(con, sql);
		try {
			pstmt.setString(1, titular);
			pstmt.setString(2, inst_financeira);
			pstmt.setInt(3, tipo_conta);
			pstmt.execute();
			System.out.println("Conta incluida com sucesso!");
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static double totalDespesas(int conta) {
		double totalDespesas = 0.00;
		Connection con = Conn.getConnect();
		String sql = "SELECT valor_desp FROM despesas WHERE conta = ?";
		PreparedStatement pstmt = Conn.pstmt(con, sql);
		try {
			pstmt.setInt(1, conta);
			ResultSet resultado = pstmt.executeQuery();
			while(resultado.next()) {
				double totalParcial = resultado.getDouble("valor_desp");
				totalDespesas += totalParcial;
			}
			con.close();
			return totalDespesas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static double totalReceitas(int conta) {
		double totalReceitas = 0.00;
		Connection con = Conn.getConnect();
		String sql = "SELECT valor_rec FROM receitas WHERE conta = ?";
		PreparedStatement pstmt = Conn.pstmt(con, sql);
		try {
			pstmt.setInt(1, conta);
			ResultSet resultado = pstmt.executeQuery();
			while(resultado.next()) {
				double totalParcial = resultado.getDouble("valor_rec");
				totalReceitas += totalParcial;
			}
			con.close();
			return totalReceitas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	
	public static void inserirReceita(double valor_rec, String data_rec_esp,
			int tipo_rec, int conta) {
		String dataDeHoje = dataDeHoje();
		Connection con = Conn.getConnect();
		String sql = "INSERT INTO receitas(valor_rec, data_inclusao, data_rec_esp,"
				+ "tipo_rec, conta) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement pstmt = Conn.pstmt(con, sql);
		try {
			pstmt.setDouble(1, valor_rec);
			pstmt.setString(2, dataDeHoje);
			pstmt.setString(3, data_rec_esp);
			pstmt.setInt(4, tipo_rec);
			pstmt.setInt(5, conta);
			pstmt.execute();
			setSaldo(con, conta);
			System.out.println("Receita incluida com sucesso!");
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static double calcularSaldo(int conta) {
		double saldo = Funn.totalReceitas(conta) - Funn.totalDespesas(conta);
		return saldo;
	}
	
	private static void setSaldo(Connection con,int conta) {
		double valorParaUpdate = calcularSaldo(conta);
		String sqlUpdate = "UPDATE conta SET saldo = ? WHERE id = ?";
		PreparedStatement pstmt = Conn.pstmt(con, sqlUpdate);
		try {
			pstmt.setDouble(1, valorParaUpdate);
			pstmt.setInt(2, conta);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean conTipos(String nomeTable) {
		Connection con = Conn.getConnect();
		String sql = "SELECT * FROM " + nomeTable + "";
		PreparedStatement pstmt = Conn.pstmt(con, sql);
		try {
			ResultSet resultado = pstmt.executeQuery();
			if (resultado.next()) {
				con.close();
				return true;
			}else {
				con.close();
				return false;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
