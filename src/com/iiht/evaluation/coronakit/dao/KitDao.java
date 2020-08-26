package com.iiht.evaluation.coronakit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class KitDao {

	private String jdbcURL = "jdbc:mysql://localhost:3307/covidKit;";
	private String jdbcUsername="root";
	private String jdbcPassword="root";
	private Connection jdbcConnection;

	public KitDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	
	public boolean addNewVisitor(String name, String email, String phone) throws ClassNotFoundException, SQLException {
		String sql = "insert into users (userName,userEmail,phoneNumber) values(?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setString(2, email);
		pstmt.setInt(3, Integer.parseInt(phone));
		
		boolean added = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return added;
	}
	
	public Integer addCovidkit(String personName, String email, String contactNumber,int totalAmount,String deliveryAddress,String orderDate,boolean orderFinalized) throws ClassNotFoundException, SQLException {
		
		String sql = "insert into covidKit (PersonName,Email,ContactNumber,TotalAmount,DeliveryAddress,OrderDate,pOrderFinalized) values(?,?,?,?,?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, personName);
		pstmt.setString(2, email);
		pstmt.setString(3, contactNumber);
		pstmt.setInt(4, totalAmount);
		pstmt.setString(5, deliveryAddress);
		pstmt.setString(6, orderDate);
		pstmt.setBoolean(7, orderFinalized);
		
		boolean added = pstmt.executeUpdate() > 0;
		
		sql = "select id from covidKit";
		
		Statement stmt = this.jdbcConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int covidkitid=0;
		
		while(rs.next())  {
			
			covidkitid=rs.getInt("id");
		}
		pstmt.close();
		this.disconnect();
		return covidkitid;
	}
	
	public boolean addKitdetails(int coronaKitId, int productId, int quantity,int amount) throws ClassNotFoundException, SQLException {
		
		String sql = "insert into kitdetails (covidKitId,productId,quantity,amount) values(?,?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setInt(1,coronaKitId );
		pstmt.setInt(2, productId);
		pstmt.setInt(3, quantity);
		pstmt.setInt(4, amount);
		
		boolean added = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return added;
	}
}