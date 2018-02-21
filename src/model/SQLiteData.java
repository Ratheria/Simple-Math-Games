/**
 *	@author Ariana Fairbanks
 */
package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import adapter.Controller;

public class SQLiteData
{
	private Controller base;
	private static Connection con;
	private static boolean hasData;

	public SQLiteData(Controller base)
	{
		this.base = base;
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
			{	getConnection();	}
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
			{	getConnection();	}
			String query = "SELECT ID FROM USER WHERE userName = ? AND pass = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, pass);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e){}
		return res;
	}
	
	public boolean changeLogin(int ID, String pass, String newPass)
	{
		boolean match = false;
		if(base.getPerms() < 3)
		{
			try
			{
				ResultSet res = null;
				if (con == null)
				{	getConnection();	}
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
		}
		return match;
	}
	
	public boolean resetPassword(String userName)
	{
		boolean result = true;
		if(base.getPerms() < 2)
		{
			try
			{
				if (con == null)
				{	getConnection();	}
				ResultSet res = null;
				String query = "SELECT ID FROM USER WHERE userName = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, userName);
				res = preparedStatement.executeQuery();
				String id = "" + res.getInt("ID");
				query = "UPDATE USER SET pass = ? WHERE userName = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, userName);
				preparedStatement.executeUpdate();
			}
			catch (SQLException e)
			{
				e.printStackTrace(); 
				result = false;
			}
		}
		return result;
	}

	
	public void loginSuccess(String userName)
	{
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "UPDATE USER SET failedAttempts = ? WHERE userName = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, userName);
			preparedStatement.executeUpdate();
			query = "UPDATE USER SET isLocked = ? WHERE userName = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setString(2, userName);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e){ e.printStackTrace(); }
	}
	
	public void loginFailure(String userName)
	{
		try
		{
			ResultSet res = null;
			if (con == null)
			{	getConnection();	}
			String query = "SELECT failedAttempts FROM USER WHERE userName = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, userName);
			res = preparedStatement.executeQuery();
			int result = res.getInt("failedAttempts");
			result += 1;
			query = "UPDATE USER SET failedAttempts = ? WHERE userName = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, result);
			preparedStatement.setString(2, userName);
			preparedStatement.executeUpdate();
			if (result > 5)
			{
				query = "UPDATE USER SET isLocked = ? WHERE userName = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setBoolean(1, true);
				preparedStatement.setString(2, userName);
				preparedStatement.executeUpdate();
			}
		}
		catch (SQLException e){e.printStackTrace();}
	}
	
	public boolean isLocked(String userName)
	{
		boolean result = true;
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT isLocked FROM USER WHERE userName = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, userName);
			res = preparedStatement.executeQuery();
			result = res.getBoolean("isLocked");
		}		
		catch (SQLException e){e.printStackTrace();}
		return result;
	}
	
	public int permission(int id)
	{
		int result = 2;
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
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
		if (con == null)
		{	getConnection();	}
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
		if (con == null)
		{	getConnection();	}
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
	
	public String getClassID(int id)
	{
		String result = "";
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
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
	
	public ResultSet getCustomEquation(String classID)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			
			//TODO make this relevent
			String query = "SELECT questionList FROM CUSTOMEQUATION WHERE classID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, classID);
			res = preparedStatement.executeQuery();
//			result = res.getString("classID");
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public boolean importUsers(File csvFile)
	{
		boolean result = false;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try 
        {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) 
            {
                String[] currentRow = line.split(cvsSplitBy);


            }

        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            if (br != null) 
            {
                try 
                {
                    br.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        return false;

    }

	private void addUser(int id, String userName, String pass, String firstName, String lastName, String classID, int permissions)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO USER VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, userName);
			preparedStatement.setString(3, pass);
			preparedStatement.setString(4, firstName);
			preparedStatement.setString(5, lastName);
			preparedStatement.setString(6, classID);
			preparedStatement.setInt(7, permissions);
			preparedStatement.setInt(8, 0);
			preparedStatement.setBoolean(9, false);
			preparedStatement.execute();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	private void addCustomEquations(String classID, String questionList, int numberOfEquations, int frequency)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO CUSTOMEQUATION VALUES( ?, ?, ?, ?);");
			preparedStatement.setString(1, classID);
			preparedStatement.setString(2, questionList);
			preparedStatement.setInt(3, numberOfEquations);
			preparedStatement.setInt(4, frequency);
			preparedStatement.execute();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	private void getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			//database path
			//if there is no database there a new one will be created
			String databaseFilePath = "jdbc:sqlite:C:/ProgramData/MPDKWID";
			con = DriverManager.getConnection(databaseFilePath);
		}
		catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
			try
			{
				Class.forName("org.sqlite.JDBC");
				File homedir = new File(System.getProperty("user.home"));
				String databaseFilePath = "jdbc:sqlite:" + homedir + "/MPDKWID";
				con = DriverManager.getConnection(databaseFilePath);
			}
			catch (SQLException | ClassNotFoundException e2)
			{
				System.out.println("linux fix didn't work");
			}
			
		}
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
							+ "permission INTEGER," + "failedAttempts INTEGER," + "isLocked BOOLEAN,"
							+ "PRIMARY KEY (ID));");
					
					System.out.println("Building the custom equations table.");
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE CUSTOMEQUATION(classID VARCHAR(5),"
							+ "questionList VARCHAR(600)," + "numberOfEquations INTEGER," + "frequency INTEGER," 
							+ "FOREIGN KEY (classID) REFERENCES USER(classID),"
							+ "PRIMARY KEY (classID));");

					addUser(000000, "root", "root", "Root", "User", "00", 0);
					addUser(111111, "deft", "deft", "Default", "Teacher", "1A", 2);
					addUser(222222, "defs", "defs", "Default", "Student", "1A", 3);
					
					addCustomEquations("1A", "5+10:7-2", 2, 5);
				}
			}
			catch (SQLException e){e.printStackTrace();}
		}
	}

}
