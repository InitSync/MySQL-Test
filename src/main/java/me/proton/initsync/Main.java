package me.proton.initsync;

import me.proton.initsync.storage.DatabaseConnection;
import me.proton.initsync.storage.DatabaseQueries;
import me.proton.initsync.storage.Level;

import java.util.UUID;

public class Main
{
	// Creating a variable called `databaseConnection` of type `DatabaseConnection`.
	private DatabaseConnection databaseConnection;
	// Creating a variable called `level` of type `Level`.
	private Level level;
	
	/**
	 * This function returns the database connection.
	 *
	 * @return The databaseConnection object.
	 */
	public DatabaseConnection databaseConnection()
	{
		return this.databaseConnection;
	}
	
	/**
	 * Returns the level of the current log message.
	 *
	 * @return The level of the logger.
	 */
	public Level level()
	{
		return this.level;
	}
	
	/**
	 * It creates a random UUID, stores it at the database, increases the level of the UUID by 10, and
	 * then updates the database with the new level
	 */
	private void init()
	{
		final UUID uuid = UUID.randomUUID();
		System.out.println("Using the uuid: " + uuid);
		
		this.establishConnection();
		this.level = new Level();
		
		DatabaseQueries.storeData(
			 this.databaseConnection().connection(),
			 uuid,
			 "InitSync", 1
		);
		
		System.out.println("Level of: " +
			 DatabaseQueries.name(this.databaseConnection().connection(), uuid) + " -> " +
			 DatabaseQueries.level(this.databaseConnection().connection(), uuid)
		);
		
		this.level.increase(10);
		
		if (DatabaseQueries.existsAtTheDatabase(
			 this.databaseConnection().connection(),
			 uuid))
		{
			DatabaseQueries.updateData(
				 this.databaseConnection().connection(),
				 uuid,
				 "Marcelo",
				 this.level.amount()
			);
		}
		
		System.out.println("Now the level of: " +
			 DatabaseQueries.name(this.databaseConnection().connection(), uuid) + " is -> " +
			 DatabaseQueries.level(this.databaseConnection().connection(), uuid)
		);
		
		DatabaseQueries.closeConnection(this.databaseConnection().connection());
	}
	
	/**
	 * It creates a new DatabaseConnection object, and then creates a table in the database
	 */
	private void establishConnection()
	{
		this.databaseConnection = new DatabaseConnection(
			 "localhost",
			 3306,
			 "levelsdb",
			 "root",
			 ""
		);
		
		DatabaseQueries.createTable(this.databaseConnection.connection());
	}
	
	/**
	 * The main function is the entry point of the program, and it calls the init function.
	 */
	public static void main(String[] args)
	{
		new Main().init();
	}
}
