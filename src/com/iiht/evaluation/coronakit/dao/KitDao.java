package com.iiht.evaluation.coronakit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.iiht.evaluation.coronakit.model.ProductMaster;



public class KitDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	public String getJdbcURL() {
		return jdbcURL;
	}

	public void setJdbcURL(String jdbcURL) {
		this.jdbcURL = jdbcURL;
	}

	public String getJdbcUsername() {
		return jdbcUsername;
	}

	public void setJdbcUsername(String jdbcUsername) {
		this.jdbcUsername = jdbcUsername;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}

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
			setJdbcURL("jdbc:mysql://localhost:3307/covidKitDb1");
			setJdbcUsername("root");
			setJdbcPassword("root");
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	
	public boolean addNewVisitor(String name, String email, String phone) throws ClassNotFoundException, SQLException {
		String sql = "insert into users1 (userName,userEmail,phoneNumber) values(?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setString(2, email);
		pstmt.setInt(3, Integer.parseInt(phone));
		
		boolean added = pstmt.executeUpdate()>0;
		
		pstmt.close();
		this.disconnect();
		return added;
		
	}	
	
	public Integer addCovidkit(String personName, String email, String contactNumber,int totalAmount,String deliveryAddress,String orderDate,boolean orderFinalized) throws ClassNotFoundException, SQLException {
		
		String sql = "insert into covidKit1 (PersonName,Email,ContactNumber,TotalAmount,DeliveryAddress,OrderDate,OrderFinalized) values(?,?,?,?,?,?,?)";
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
		
		String sql = "insert into kitdetails1 (covidKitId,productId,quantity,amount) values(?,?,?,?)";
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