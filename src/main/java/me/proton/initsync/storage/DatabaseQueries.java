package me.proton.initsync.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class DatabaseQueries
{
	/**
	 * It creates a table in the database if it doesn't already exist
	 *
	 * @param connection The connection to the database.
	 */
	public static void createTable(Connection connection)
	{
		Objects.requireNonNull(connection, "Connection is null.");
		
		try (PreparedStatement statement =
			 connection.prepareStatement("CREATE TABLE IF NOT EXISTS `userData` "
				  + "(`ID` INT(11) NOT NULL AUTO_INCREMENT, `UUID` VARCHAR(50) NULL, `Name` "
				  + "VARCHAT(100) NOT NULL, `Level` INT(100) NULL, PRIMARY KEY (`ID`)"))
		{
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to create the table at the database.");
			
			e.printStackTrace();
		}
	}
	
	/**
	 * > This function stores a user's data in the database
	 *
	 * @param connection The connection to the database.
	 * @param uuid The UUID of the player.
	 * @param name The name of the player.
	 * @param level The level of the user.
	 */
	public static void storeData(Connection connection, UUID uuid, String name, int level)
	{
		Objects.requireNonNull(connection, "Connection is null.");
		Objects.requireNonNull(uuid, "Uuid is null.");
		
		try (PreparedStatement statement =
			 connection.prepareStatement("INSERT INTO `userData` (`ID`, `UUID`, `Name`, `Level`) "
				  + "VALUES (null, ?, ?, ?);"))
		{
			statement.setString(1, uuid.toString());
			statement.setString(2, name);
			statement.setInt(3, level);
			statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * "It checks if a user exists at the database."
	 * <p>
	 * The first thing we do is check if the connection and uuid are null. If they are, we throw a
	 * NullPointerException
	 *
	 * @param connection The connection to the database.
	 * @param uuid The UUID of the user.
	 * @return A boolean value.
	 */
	public static boolean existsAtTheDatabase(Connection connection, UUID uuid)
	{
		Objects.requireNonNull(connection, "Connection is null.");
		Objects.requireNonNull(uuid, "Uuid is null.");
		
		try (PreparedStatement statement =
			 connection.prepareStatement("SELECT * FROM userData WHERE UUID = ?"))
		{
			statement.setString(1, uuid.toString());
			
			final ResultSet result = statement.executeQuery();
			return result.next();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * "Update the user's name and level in the database."
	 * <p>
	 * The first thing we do is check if the connection is null. If it is, we throw a
	 * `NullPointerException` with a message
	 *
	 * @param connection The connection to the database.
	 * @param uuid The UUID of the player.
	 * @param name The name of the user.
	 * @param level The level of the user.
	 */
	public static void updateData(Connection connection, UUID uuid, String name, int level)
	{
		Objects.requireNonNull(connection, "Connection is null.");
		Objects.requireNonNull(uuid, "Uuid is null.");
		
		try (PreparedStatement statement =
			 connection.prepareStatement("UPDATE `userData` SET `Name`, `Level` = ? WHERE UUID = ?"))
		{
			statement.setString(1, uuid.toString());
			statement.setString(2, name);
			statement.setInt(3, level);
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * "Get the level of the user with the given UUID from the database."
	 * <p>
	 * The first thing we do is check if the connection and UUID are null. If they are, we throw a
	 * NullPointerException
	 *
	 * @param connection The connection to the database.
	 * @param uuid The UUID of the player.
	 * @return The level of the user.
	 */
	public static int level(Connection connection, UUID uuid)
	{
		Objects.requireNonNull(connection, "Connection is null.");
		Objects.requireNonNull(uuid, "Uuid is null.");
		
		try (PreparedStatement statement =
			 connection.prepareStatement("SELECT Level FROM `userData` WHERE UUID = ?"))
		{
			statement.setString(1, uuid.toString());
			
			final ResultSet result = statement.executeQuery();
			if (result.next()) return result.getInt("Level");
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		return -1;
	}
	
	/**
	 * "Given a connection and a UUID, return the name of the user with that UUID."
	 * <p>
	 * The first thing we do is check that the connection and UUID are not null. If they are, we throw an
	 * exception
	 *
	 * @param connection The connection to the database.
	 * @param uuid The UUID of the player.
	 * @return The name of the user.
	 */
	public static String name(Connection connection, UUID uuid)
	{
		Objects.requireNonNull(connection, "Connection is null.");
		Objects.requireNonNull(uuid, "Uuid is null.");
		
		try (PreparedStatement statement =
			 connection.prepareStatement("SELECT Name FROM `userData` WHERE UUID = ?"))
		{
			statement.setString(1, uuid.toString());
			
			final ResultSet result = statement.executeQuery();
			if (result.next()) return result.getString("Name");
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		return null;
	}
	
	/**
	 * It closes the connection to the database
	 *
	 * @param connection The connection to close.
	 */
	public static void closeConnection(Connection connection)
	{
		try
		{
			connection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to close the connection.");
			
			e.printStackTrace();
		}
	}
}
