package me.proton.initsync.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
	// This is a variable that will be used to store the connection object.
	private Connection connection;
	
	// This function is the constructor of the class.
	public DatabaseConnection(
		 String host,
		 int port,
		 String database,
		 String username,
		 String password
	)
	{
		try
		{
			synchronized (this)
			{
				Class.forName("com.mysql.jdbc.Driver");
				this.connection = DriverManager.getConnection(
					 "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useUnicode"
						  + "=yes", username, password
				);
				
				System.out.println("MySQL connection established successful!");
			}
		}
		catch (SQLException | ClassNotFoundException e)
		{
			System.out.println("Has happens an error to connect to the database.");
			
			e.printStackTrace();
		}
	}
	
	/**
	 * This function returns the connection.
	 *
	 * @return The connection object.
	 */
	public Connection connection()
	{
		return this.connection;
	}
}
