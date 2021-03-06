package com.dbas.transfer.server;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;

import com.dbas.transfer.client.Customer;

public class DBConnection {

	private static volatile Connection conn;

	// public static void main(String[] args) {
	// Currency eur = new Currency("EUR");
	// Currency yen = new Currency("JPY");
	// Customer andreas = new Customer("Andreas", "Finn", "3");
	// Customer hasse = new Customer("Hasse", "Aro", "4");
	// ExchangeRate eur_yen = new ExchangeRate(eur, yen, 124);
	// ExchangeRate yen_eur = new ExchangeRate(yen, eur, 1.0 / 124);
	// Kiosk madrid = new Kiosk(3, "Madrid", eur, 200, 0);
	// Kiosk tokio = new Kiosk(4, "Tokio", yen, 10000, 0);
	// }

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/TransferService?"
							+ "user=root");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// TESTED
	public static boolean addCurrency(Currency currency) {

		String query = "INSERT INTO Currencies VALUES (?)";

		PreparedStatement pstm = null;
		boolean success = false;
		try {
			pstm = conn.prepareStatement(query);

			pstm.setString(1, currency.getCode());

			int ret = pstm.executeUpdate();
			success = ret != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
		}
		return success;
	}

	// TESTED
	public static boolean addCustomer(Customer customer) {

		String query = "INSERT INTO Customers VALUES (?, ?, ?)";

		PreparedStatement pstm = null;
		boolean success = false;
		try {
			pstm = conn.prepareStatement(query);

			pstm.setString(1, customer.getPassportNum());
			pstm.setString(2, customer.getFirstName());
			pstm.setString(3, customer.getLastName());

			int ret = pstm.executeUpdate();
			success = ret != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
		}
		return success;
	}

	// TESTED
	public static boolean addExchangeRate(ExchangeRate exRate) {

		String query = "INSERT INTO ExchangeRates VALUES (?, ?, ?)";

		PreparedStatement pstm = null;
		boolean success = false;
		try {
			pstm = conn.prepareStatement(query);

			pstm.setString(1, exRate.getSendCurrency().getCode());
			pstm.setString(2, exRate.getRecCurrency().getCode());
			pstm.setDouble(3, exRate.getRate());

			int ret = pstm.executeUpdate();
			success = ret != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
		}
		return success;
	}

	// TESTED
	public static boolean addKiosk(Kiosk kiosk) {

		String query = "INSERT INTO Kiosks VALUES (?, ?, ?, ?, ?)";

		PreparedStatement pstm = null;
		boolean success = false;
		try {
			pstm = conn.prepareStatement(query);

			pstm.setInt(1, kiosk.getId());
			pstm.setString(2, kiosk.getAddress());
			pstm.setString(3, kiosk.getCurrency().getCode());
			pstm.setDouble(4, kiosk.getOperatingBalance());
			pstm.setDouble(5, kiosk.getRevenue());

			int ret = pstm.executeUpdate();
			success = ret != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
		}
		return success;
	}

	// TESTED
	public static boolean addTransfer(Transfer transfer) {

		String query = "INSERT INTO Transfers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement pstm = null;
		boolean success = false;
		try {
			pstm = conn.prepareStatement(query);

			pstm.setInt(1, transfer.getId());
			pstm.setString(2, getDateTimeString(transfer.getTime()));
			pstm.setDouble(3, transfer.getSendAmount());
			pstm.setString(4, transfer.getStatus().toString());
			pstm.setString(5, transfer.getSender().getPassportNum());
			pstm.setString(6, transfer.getReceiver().getPassportNum());
			pstm.setInt(7, transfer.getSendKiosk().getId());
			pstm.setInt(8, transfer.getRecKiosk().getId());
			pstm.setString(9, transfer.getRecKiosk().getCurrency().getCode());
			pstm.setString(10, transfer.getRecKiosk().getCurrency().getCode());

			int ret = pstm.executeUpdate();
			success = ret != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
		}
		return success;
	}

	public static boolean sendMoney(double amount, String senderPPN,
			String receiverPPN, String sendKioskAddress, String recKioskAddress) {

		int sendKioskId = getKioskId(sendKioskAddress);
		int recKioskId = getKioskId(recKioskAddress);
		return sendMoney(amount, senderPPN, receiverPPN, sendKioskId,
				recKioskId);
	}

	// TESTED
	public static boolean sendMoney(double amount, String senderPPN,
			String receiverPPN, int sendKioskId, int recKioskId) {

		String call = "{call sendMoney(?, ?, ?, ?, ?, ?)}";

		CallableStatement cstm = null;
		boolean success = false;
		try {
			cstm = conn.prepareCall(call);

			cstm.setDouble(1, amount);
			cstm.setString(2, senderPPN);
			cstm.setString(3, receiverPPN);
			cstm.setInt(4, sendKioskId);
			cstm.setInt(5, recKioskId);

			cstm.registerOutParameter(6, Types.BOOLEAN);

			cstm.execute();

			success = cstm.getBoolean(6);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cstm);
		}
		return success;
	}

	public static String receiveMoney(String receiverPassPortNum, String recKioskAddress) {
		int recKioskId = getKioskId(recKioskAddress);
		return receiveMoney(receiverPassPortNum, recKioskId);
	}

	// TESTED
	/**
	 * Returns null if unable to receive.
	 */
	public static String receiveMoney(String receiverPassPortNum, int recKioskId) {

		String query = "SELECT receiveMoney(?, ?)";

		PreparedStatement pstm = null;
		ResultSet rs = null;
		Double amount = null;
		try {
			pstm = conn.prepareStatement(query);

			pstm.setString(1, receiverPassPortNum);
			pstm.setInt(2, recKioskId);

			rs = pstm.executeQuery();

			rs.next();
			amount = rs.getDouble(1);
			if (rs.wasNull())
				amount = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(rs);
		}
		
		query = "SELECT currency FROM Kiosks WHERE id = \"" + recKioskId + "\"";

		Statement stmt = null;
		rs = null;
		String currency = null;
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			rs.next();
			currency = rs.getString("currency");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}
		
		if(amount == null || currency == null)
			return null;
		
		return String.format("%.2f %s", amount, currency);
	}

	public static Integer getKioskId(String address) {
		String query = "SELECT id FROM Kiosks WHERE address = \"" + address
				+ "\"";

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			rs.next();
			return rs.getInt("id");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}
		return null;
	}

	public static Kiosk[] getKiosks() {
		String query = "SELECT * FROM Kiosks";

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Kiosk> kiosks = new ArrayList<Kiosk>();
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			while (rs.next())
				kiosks.add(new Kiosk(rs.getInt("id"), rs.getString("address"),
						new Currency(rs.getString("currency")), rs
								.getDouble("operatingBalance"), rs
								.getDouble("revenue")));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}
		return kiosks.toArray(new Kiosk[0]);
	}

	private static String getDateTimeString(Date dt) {
		return String.format("%1$tF %1$tT", dt);
	}

	private static void close(Statement... stmts) {
		for (Statement stmt : stmts) {
			if (stmt == null)
				return;

			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void close(ResultSet... rss) {
		for (ResultSet rs : rss) {
			if (rs == null)
				return;

			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
