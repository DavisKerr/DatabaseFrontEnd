package data.controller;

import java.sql.*;

import javax.swing.JOptionPane;

/**
 * The class that works directly with the database.
 * @author Davis
 * @version 1.4 Began creating more query methods.
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
	 * The data to be displayed in a table in a later version.
	 */
	private String[][] data;
	/**
	 * the current query.
	 */
	private String currentQuery;
	
	/**
	 * Constructs the class.
	 * @param baseController The AppController
	 */
	public DataController(DataAppController baseController, String dbName)
	{
//		connectionString = "jdbc:mysql://localhost/dungeons_and_dragons_tables?user=root";//the user=root is important!
		if(dbName.equals(""))
		{
			dbName = JOptionPane.showInputDialog("Enter databse name :");
		}
		
		connectionStringBuilder("localhost", dbName, "Davis", "davis1");
		this.baseController = baseController;
		checkDriver();
		setupConnection();
	}
	
	/**
	 * This method builds the connection string in the required format.
	 * The ? means its the end of the path,
	 * and the & means Im giving you new information.
	 * @param pathToDBServer The main path to the server.
	 * @param databaseName The name of the database.
	 * @param userName The username of the user.
	 * @param password The password the users password.
	 */
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;
		connectionString += "&password=" + password;
	}
	
	/**
	 * Sets up the connection. Creates a list of errors if it fails.
	 */
	public void setupConnection() 
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
			Class.forName("com.mysql.jdbc.Driver");
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
	
	/**
	 * Runs the SHOW TABLES query.
	 * @return results the result of the query
	 */
	public String displayTables(/*String input*/)
	{
		String results = "";
		String query = "SHOW TABLES"; // in order to ask a query, we need connection and a statement.
		//String query = input;
		try 
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			
			
			while(answer.next())
			{
				results += answer.getString(1) + "\n";
				
			}
			answer.close();
			firstStatement.close();
		} 
		catch (SQLException currentSQLError) 
		{
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	public String[] displayDBS()
	{
		String[] results;
		String query = "SHOW DATABASES"; // in order to ask a query, we need connection and a statement.
		//String query = input;
		int rowCount = 0;
		try 
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			
			answer.last(); // find the last number of rows
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount];
			
			while(answer.next())
			{
				for(int col = 0; col < results.length; col++ )
				{
					results[answer.getRow()-1] = answer.getString(1);
				}
				
				
			}
			answer.close();
			firstStatement.close();
		} 
		catch (SQLException currentSQLError) 
		{
			displayErrors(currentSQLError);
			results = null;
		}
		
		return results;
	}
	
	/**
	 * sends the query.
	 * @param query the query that was sent.
	 */
	public void sendQuery(String query)
	{
		createArray(query);
	}
	
	public void fixPermissions()
	{
		String fix = "GRANT ALL ON *.* TO `root`@`localhost`";
		try
		{
			databaseConnection.createStatement().executeQuery(fix);
		}
		catch(SQLException e)
		{
			displayErrors(e);
		}
	}
	
	
	/**
	 * Runs the insert Query.
	 * @return result the result of the query
	 */
	public int insertTable()
	{ 
		
		int rowsEffected = 0;
		String query = "INSERT INTO tester ( name, favorite_color, age) VALUES (\'Jim\', \'red\', 5)";
		
		
		
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsEffected = insertStatement.executeUpdate(query);
			insertStatement.close();
		}
		catch(SQLException e)
		{
			displayErrors(e);
		}
		
		return rowsEffected;
	}
	/**
	 * Creates and returns an array.
	 * @return
	 */
	public String[][] getResultArray()
	{
		
		String[][] results;
		String query = "SHOW TABLES";
		
		try 
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			
			int rowCount;
			
			answer.last(); // find the last number of rows
			rowCount = answer.getRow();
			answer.beforeFirst();
			
			ResultSetMetaData metaData = answer.getMetaData();
			int colCount = metaData.getColumnCount(); // find the column count
			
			results = new String[rowCount][colCount];
			
			
			
			while(answer.next())
			{
				for(int col = 0; col < results[0].length; col++)
				{
					results[answer.getRow()-1][0] = answer.getString(1);
				}
				
			}
			answer.close();
			firstStatement.close();
		} 
		catch (SQLException currentSQLError) 
		{
			results = new String[][]{{"empty"}};
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	/**
	 * Returns the array built in the select query.
	 * @return data the array of data
	 */
	public String[][] getDataArray()
	{
		return data;
	}
	/**
	 * Gets the column names for the array.
	 * @param query
	 * @return columnInformation the columns name.
	 */
	public String[] getMetaData( String query)
	{
		String[] columnInformation;
//		String query = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			ResultSetMetaData myMeta = answer.getMetaData();
			
			columnInformation = new String[myMeta.getColumnCount()];
			for(int spot = 0; spot < myMeta.getColumnCount(); spot++)
			{
				columnInformation[spot] = myMeta.getColumnName(spot + 1);
			}
			
			
			answer.close();
			firstStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			 columnInformation = new String[] {"Nothing exhists."};
			displayErrors(currentSQLError);
		}
		
		return columnInformation;
		
	}

	/**
	 * Builds a 2D array that displays information from a table.
	 * @param query
	 */
	public void createArray(String query)
	{
		currentQuery = query;
		try 
		{
			if(checkForDataViolation())
			{
				throw new SQLException("Attempted illegal modification of data", ":( Done tried to mess up da data State...", Integer.MIN_VALUE);
			}
			else
			{
				Statement firstStatement = databaseConnection.createStatement();
				ResultSet answer = firstStatement.executeQuery(query);
				
				ResultSetMetaData metaData = answer.getMetaData();
				
				int colNum = metaData.getColumnCount();
				int rowNum = 0;
				
				answer.last();
				rowNum = answer.getRow();
				answer.beforeFirst();
				answer.close();
				firstStatement.close();
				
				data = new String[rowNum][colNum];
				
				firstStatement = databaseConnection.createStatement();
				answer = firstStatement.executeQuery(query);
				
				int currentRow = 0;
					
				while(answer.next())
				{
					for(int col = 0; col < data[0].length; col++)
					{
						
						data[currentRow][col] = answer.getString(col + 1);
						
					}
					currentRow++;
				}
				
				answer.close();
				firstStatement.close();
			}
			
		
			
				
		} 
		catch (SQLException currentSQLError) 
		{
			displayErrors(currentSQLError);
		}
		
	}
	
	public String[][] selectQueryResults(String query)
	{
		this.currentQuery = query;
		String[][] results;
		
		try
		{
			if(checkForDataViolation())
			{
				throw new SQLException("Attempted illegal modification of data", ":( Done tried to mess up da data State...", Integer.MIN_VALUE);
				
			}
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();
			
		}
		catch(SQLException exception)
		{
			
		}
		return null;
	}
	
	/**
	 * Checks to see if the statement would destroy the data.
	 * @return true/false If its violated.
	 */
	private boolean checkForDataViolation()
	{
		if(currentQuery.toUpperCase().contains("DROP ")
				|| currentQuery.toUpperCase().contains("TRUNCATE ")
				|| currentQuery.toUpperCase().contains("SET ")
				|| currentQuery.toUpperCase().contains("ALTER "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Makes sure that a query will not drop an entire database.
	 * @return boolean Did the user try to drop the table.
	 */
	private boolean checkForStructureViolation()
	{
		if(currentQuery.toUpperCase().contains(" DATABASE "))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	/**
	 * method for dropping tables or indexes from a database.
	 * @throws SQLException when the query contains a DROP DATABSE command.
	 */
	public void dropStatement()
	{
		String results;
		try
		{
			if(checkForStructureViolation())
			{
				throw new SQLException("It is illegal to delete a db.", "DONT DO IT AGAIN!", Integer.MIN_VALUE);
			}
			
			if(currentQuery.toUpperCase().contains("INDEX"))
			{
				results = "The index was ";
			}
			else
			{
				results = "The table was ";
			}
			Statement dropStatement = databaseConnection.createStatement();
			int affected = dropStatement.executeUpdate(currentQuery);
			
			dropStatement.close();
			if(affected == 0)
			{
				results +=  "dropped";
			}
			
			JOptionPane.showMessageDialog(baseController.getAppFrame() , results);
			
		}
		catch(SQLException e)
		{
			displayErrors(e);
		}
	}

	public String[] showTables() 
	{
		String[] results;
		String query = "SHOW TABLES"; // in order to ask a query, we need connection and a statement.
		//String query = input;
		int rowCount = 0;
		try 
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			
			answer.last(); // find the last number of rows
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount + 1];
			
			while(answer.next())
			{
				for(int col = 1; col < results.length; col++ )
				{
					results[answer.getRow()] = answer.getString(1);
				}
				results[0] = "";
				
				
			}
			answer.close();
			firstStatement.close();
		} 
		catch (SQLException currentSQLError) 
		{
			displayErrors(currentSQLError);
			results = null;
		}
		
		return results;
	}
}

