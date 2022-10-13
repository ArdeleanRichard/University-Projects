package dataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ardelean Eugen Richard
 * Class used for linking the database from MySQL to the application
 */
public class ConnectionFactory {

	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse?autoReconnect=true&useSSL=false";
	private static final String USER = "DILIGLONT";
	private static final String PASS = "1234";

	private static ConnectionFactory instance = new ConnectionFactory(); //singleton

	/**
	 * constructor
	 */
	private ConnectionFactory() 
	{
		try 
		{
			Class.forName(DRIVER);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * method used to create a new connection
	 * @return
	 */
	private Connection newConnection() 
	{
		Connection connection = null;
		try
		{
			connection = DriverManager.getConnection(DBURL, USER, PASS);
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * method used to create a new connection
	 * @return the new connection
	 */
	public static Connection getConnection() 
	{
		return instance.newConnection();
	}

	/**
	 * method used to close a connection
	 * @param connection that will be closed
	 */
	public static void close(Connection connection) 
	{
		if (connection != null) 
		{
			try 
			{
				connection.close();
			} 
			catch (SQLException e) 
			{
				LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
			}
		}
	}

	/**
	 * @param statement that will be closed
	 */
	public static void close(Statement statement) {
		if (statement != null) 
		{
			try 
			{
				statement.close();
			}
			catch (SQLException e)
			{
				LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
			}
		}
	}

	/**
	 * method used to close the result set
	 * @param resultSet the result set you want closed
	 * 
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) 
		{
			try 
			{
				resultSet.close();
			} 
			catch (SQLException e) 
			{
				LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
			}
		}
	}
}
