package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CreateAccountServlet")

public class CreateAccountServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public CreateAccountServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		String userName= request.getParameter("userName");
		String balance = request.getParameter("balance");
		if(userName=="" || balance =="" || balance.charAt(0)=='-'){
			out.print("Enter user name and a positive vlaue for balance");
		}
		else
		{
			double balanceAmount;
			try
			{
				balanceAmount = Double.parseDouble(balance);
				Connection con = SQLCommand.connect();
				PreparedStatement stmt = con.prepareStatement("insert into UserAccount (name,balance) values(?, ?)");
		        stmt.setString(1, userName);
		        stmt.setDouble(2, balanceAmount);
		        stmt.execute();
		        stmt.close();
		        con.close();
		        out.println("New account has been added");
			}
			catch(NumberFormatException ex)
			{
				out.println("Balance should be numric value");
			}
			catch (SQLException ex) 
			{
				out.println("There is a problem to connect with database");
			}
			finally 
			{
				out.close();
			}  
		}
		
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request,response);
	}
}
