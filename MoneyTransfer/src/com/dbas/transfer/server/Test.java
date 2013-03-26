package com.dbas.transfer.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	private static volatile Connection conn;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/TransferService?" +
                    "user=root");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static void main(String[] args) {
		String hej = "hej";
		System.out.println(hej.getClass().getMethods()[0]);
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Currencies");
			System.out.println("code");
			System.out.println("----");
			while (rs.next())
				System.out.println(rs.getString("code"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
