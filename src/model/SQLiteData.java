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
	
	public boolean changeLogin(int ID, String pass, String newPass)
	{
		boolean match = false;
		try
		{
			ResultSet res = null;
			if (con == null)
			{
				getConnection();
			}
			String query = "SELECT pass FROM USER WHERE ID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			res = preparedStatement.executeQuery();
			String result = res.getString("pass");
			if (pass.equals(result))
			{
				match = true;
				query = "UPDATE USER SET pass = ? WHERE ID = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, newPass);
				preparedStatement.setInt(2, ID);
				preparedStatement.executeUpdate();
			}
		}
		catch (SQLException e){e.printStackTrace();}
		return match;
	}
	
	public int permission(int id)
	{
		int result = 2;
		ResultSet res = null;
		PreparedStatement preparedStatement;
		try 
		{
			String query = "SELECT permission FROM USER WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			result = res.getInt("permission");
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

	public void addUser(String userName, String pass, String firstName, String lastName, String classID, int permissions)
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
			preparedStatement.setInt(7, permissions);
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
							+ "permission INTEGER," + "PRIMARY KEY (ID));");

					addUser("root", "root", "Root", "User", "0", 0);
					addUser("deft", "deft", "Default", "Teacher", "1A", 1);
					addUser("defs", "defs", "Default", "Student", "1A", 2);
				}
			}
			catch (SQLException e){e.printStackTrace();}
		}
	}
}
