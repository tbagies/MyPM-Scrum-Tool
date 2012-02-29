package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteServlet")

public class DeleteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public DeleteServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int userIDValue=0;
        ResultSet rs;
        try
		{
			String userId = request.getParameter("userId");
			userIDValue = Integer.parseInt(userId);
			rs = SQLCommand.checkAccountNumber(userIDValue);
			Connection con = SQLCommand.connect();
			PreparedStatement stmt = con.prepareStatement("delete from userAccount where id= ?");
			stmt.setInt(1, userIDValue);
			stmt.execute();
			out.println("Delete is done Succesffully");
			rs.close();
			stmt.close();
			con.close();		
		}
        catch(NumberFormatException ex){
			out.println("userID should be numric value");
		}
        catch(AccountNotFoundException ex){
			out.println("" + userIDValue + " is invlaid user account number");
		}
        catch(SQLException ex){
			out.println("SQL Exception");
		}
        finally 
		{
			out.close();
		}
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request,response);
	}

}
