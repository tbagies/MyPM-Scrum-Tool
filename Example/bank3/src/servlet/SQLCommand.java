package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.BankAccount;

public class SQLCommand {
	 public static Connection connect() throws SQLException{
		Connection con=null;
		try
		{
		new com.mysql.jdbc.Driver();
        con = DriverManager.getConnection("jdbc:mysql://localhost/banktransaction", "root", "1401");
		}catch(SQLException e){
			System.out.print("Fail to connect with the database");
			System.exit(0);
		}
		return con;
	}
	 
	 static ResultSet checkAccountNumber(int acountnumber) throws SQLException, AccountNotFoundException{
			Connection con = connect();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from userAccount where id = " + acountnumber);
			if(!rs.next()){
				throw new AccountNotFoundException();
			}
			else
			{
				return rs;
			}
		}
	 
	static BankAccount map (ResultSet rs) throws SQLException {
		    return map(rs.getInt(1), rs.getString(2),rs.getDouble(3));
		}
	static BankAccount map(int id, String name, double balance) {
		    BankAccount user = new BankAccount();
			user.setUserID(id);
		    user.setUserName(name);
		    user.setBalance(balance);
		    return user;
		}
	static String updateQuery(int userID, double balance){
		try{
			Connection con = SQLCommand.connect();
			PreparedStatement stmt = con.prepareStatement("update userAccount set balance = ? where id = ?");
			stmt.setInt(2, userID);
			stmt.setDouble(1, balance);
			stmt.execute();
			stmt.close();
			con.close();
		}catch(SQLException ex){
			return "SQL Excpetion";
		}
		return "Transaction is done Succesffully";
		
	}
}
