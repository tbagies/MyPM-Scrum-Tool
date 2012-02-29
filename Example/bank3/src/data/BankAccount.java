package data;

import servlet.InsufficientFundsException;

public class BankAccount {
	private int userID;
	private String userName;
	private double balance;
	
	public void withdraw(double amount) throws InsufficientFundsException{
		if (balance<amount || amount<=0)
		    throw new InsufficientFundsException();
		else
		{
			balance -= amount;
		}
	}
	
	public void deposit(double amount)throws InsufficientFundsException{
		if(amount<=0)
			throw new InsufficientFundsException();
		else
			balance += amount;
	}
	public int getUserID() {
		return userID;
	}

	public String toString() {
		return "BankAccount [account Number=" + userID + ", user Name=" + userName
				+ ", Balance=" + balance + "]";
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
