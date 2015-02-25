package data.controller;

import java.sql.*;

import javax.swing.JOptionPane;

/**
 * The class that works directly with the database.
 * @author Davis
 * @version 1.0
 */
public class DataController 
{
	/**
	 * The database URL
	 */
	private String connectionString;
	/**
	 * The connection of the database.
	 */
	private Connection databaseConnection;
	/**
	 * The controller.
	 */
	private DataAppController baseController;
	
	/**
	 * Constructs the class.
	 * @param baseController The AppController
	 */
	public DataController(DataAppController baseController)
	{
		connectionString = "jdbc:mysql://localhost/dungeons_and_dragons_tables?user=root";//the user=root is important!
		this.baseController = baseController;
		checkDriver();
		setupConnection();
	}
	
	/**
	 * Sets up the connection. Creates a list of errors if it fails.
	 */
	private void setupConnection() 
	{
		
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
		
	}
	
	/**
	 * checks for the driver program. Exits if it fails.
	 */
	private void checkDriver() 
	{
		
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}
	
	/**
	 * Displays the errors.
	 * @param currentException The error message.
	 */
	private void displayErrors(Exception currentException) 
	{
		//baseController.getAppFrame chooses a place for the popup.
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getErrorCode());
		}
		
		
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public void closeConnection()
	{
		try 
		{
			databaseConnection.close();
		} 
		catch (SQLException e) 
		{
			displayErrors(e);
		}
	}
		
	
	
	
	
}

