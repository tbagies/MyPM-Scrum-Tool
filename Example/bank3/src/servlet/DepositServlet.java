package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.BankAccount;

@WebServlet("/DepositeServlet")

public class DepositServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public DepositServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int userIDValue=0;
        double amountNumber;
        ResultSet rs;
        try
		{
			String userId = request.getParameter("userId");
			userIDValue = Integer.parseInt(userId);
			rs = SQLCommand.checkAccountNumber(userIDValue);
			String amount = request.getParameter("amount");
			amountNumber = Double.parseDouble(amount);
			BankAccount user = SQLCommand.map(rs);
			user.deposit(amountNumber);
			String mesg = SQLCommand.updateQuery(userIDValue,user.getBalance());
			out.println(mesg);
		}
        catch(NumberFormatException ex){
			out.println("userID and Blanace should be numric value");
		}
        catch(AccountNotFoundException ex){
			out.println("" + userIDValue + " is invlaid user account number");
		}
        catch(InsufficientFundsException ex){
        	out.println("The amount of despoit should be positive");        	
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
