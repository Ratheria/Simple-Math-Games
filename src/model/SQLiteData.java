/**
 *	@author Ariana Fairbanks
 */

package model;

import java.sql.*;

public class SQLiteData
{
	private static Connection con;
	private static boolean hasData;

	public SQLiteData()
	{
		hasData = false;
		getConnection();
		initialise();
	}

	public ResultSet displayUsers()
	{
		ResultSet res = null;
		try
		{
			if (con == null)
			{
				getConnection();
			}
			Statement state = con.createStatement();
			res = state.executeQuery("SELECT firstName, lastName FROM USER");
		}
		catch (SQLException e){e.printStackTrace();}
		return res;
	}

	public void addUser(String userName, String pass, String firstName, String lastName, String classID, boolean teacher) throws ClassNotFoundException, SQLException
	{
		if (con == null)
		{
			getConnection();
		}
		PreparedStatement prep = con.prepareStatement("INSERT INTO USER VALUES( ?, ?, ?, ?, ?, ?, ?);");
		prep.setString(2, userName);
		prep.setString(3, pass);
		prep.setString(4, firstName);
		prep.setString(5, lastName);
		prep.setString(6, classID);
		prep.setBoolean(7, teacher);
		prep.execute();

	}

	private void getConnection()
	{
		try
		{
			// sqlite driver
			Class.forName("org.sqlite.JDBC");
			// database path, if it's new database, it will be created in the
			// project folder
			String dbURL = "jdbc:sqlite:C:/ProgramData/MPDKWID";
			con = DriverManager.getConnection(dbURL);
		}
		catch (SQLException | ClassNotFoundException e){e.printStackTrace();}
		initialise();
	}

	private void initialise() 
	{
		if (!hasData)
		{
			hasData = true;
			Statement state;
			try
			{
				state = con.createStatement();
				ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='USER'");
				if (!res.next())
				{
					System.out.println("Building the User table.");
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE USER(ID INTEGER," + "userName VARCHAR(15)," + "pass VARCHAR(15),"
							+ "firstName VARCHAR(30)," + "lastName VARCHAR(30)," + "classID VARCHAR(5),"
							+ "teacher BOOLEAN," + "PRIMARY KEY (ID));");

					PreparedStatement prep = con.prepareStatement("INSERT INTO USER VALUES( ?, ?, ?, ?, ?, ?, ?);");
					prep.setString(2, "root");
					prep.setString(3, "root");
					prep.setString(4, "Root");
					prep.setString(5, "User");
					prep.setString(6, "0");
					prep.setBoolean(7, true);
					prep.execute();
				}
			}
			catch (SQLException e){e.printStackTrace();}
		}
	}
}
