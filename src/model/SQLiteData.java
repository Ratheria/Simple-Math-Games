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
	
	public ResultSet query(String SQLCommand)
	{
		ResultSet res = null;
		try
		{
			if (con == null)
			{
				getConnection();
			}
			Statement statement = con.createStatement();
			res = statement.executeQuery(SQLCommand);
		}
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public ResultSet compareLogin(String userName, String pass)
	{
		ResultSet res = null;
		try
		{
			if (con == null)
			{
				getConnection();
			}
			String query = "SELECT ID FROM USER WHERE userName = ? AND pass = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, pass);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public boolean isTeacher(int id)
	{
		boolean result = false;
		ResultSet res = null;
		PreparedStatement preparedStatement;
		try 
		{
			String query = "SELECT teacher FROM USER WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			result = res.getBoolean("teacher");
		}		
		catch (SQLException e){e.printStackTrace();}
		return result;
	}
	
	public String firstName(int id)
	{
		String result = "";
		ResultSet res = null;
		PreparedStatement preparedStatement;
		try 
		{
			String query = "SELECT firstName FROM USER WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			result = res.getString("firstName");
		}		
		catch (SQLException e){e.printStackTrace();}
		return result;
	}
	
	public String lastName(int id)
	{
		String result = "";
		ResultSet res = null;
		PreparedStatement preparedStatement;
		try 
		{
			String query = "SELECT lastName FROM USER WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			result = res.getString("lastName");
		}		
		catch (SQLException e){e.printStackTrace();}
		return result;
	}
	
	public String classID(int id)
	{
		String result = "";
		ResultSet res = null;
		PreparedStatement preparedStatement;
		try 
		{
			String query = "SELECT classID FROM USER WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			result = res.getString("classID");
		}		
		catch (SQLException e){e.printStackTrace();}
		return result;
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
			Statement statement = con.createStatement();
			res = statement.executeQuery("SELECT firstName, lastName FROM USER");
		}
		catch (SQLException e){e.printStackTrace();}
		return res;
	}

	public void addUser(String userName, String pass, String firstName, String lastName, String classID, boolean teacher)
	{
		if (con == null)
		{
			getConnection();
		}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO USER VALUES( ?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setString(2, userName);
			preparedStatement.setString(3, pass);
			preparedStatement.setString(4, firstName);
			preparedStatement.setString(5, lastName);
			preparedStatement.setString(6, classID);
			preparedStatement.setBoolean(7, teacher);
			preparedStatement.execute();

		} 
		catch (SQLException e) {e.printStackTrace();}
	}

	private void getConnection()
	{
		try
		{
			// sqlite driver
			Class.forName("org.sqlite.JDBC");
			// database path, if it's new database, it will be created in the
			// project folder
			String databaseFilePath = "jdbc:sqlite:C:/ProgramData/MPDKWID";
			con = DriverManager.getConnection(databaseFilePath);
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

					addUser("root", "root", "Root", "User", "0", true);
					addUser("deft", "deft", "Default", "Teacher", "1A", true);
					addUser("defs", "defs", "Default", "Student", "1A", false);
				}
			}
			catch (SQLException e){e.printStackTrace();}
		}
	}
}
