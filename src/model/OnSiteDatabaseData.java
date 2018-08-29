/**
 *	@author Ariana Fairbanks
 */

package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import adapter.Controller;

public class OnSiteDatabaseData
{
	private Controller base;
	private static Connection con;
	private static boolean hasData;

	public OnSiteDatabaseData(Controller base)
	{
		this.base = base;
		hasData = false;
		getConnection();
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
		catch (SQLException e) {	e.printStackTrace();	}
		return res;
	}
	
	public ResultSet unlockedAdminCount()
	{
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT SUM(ID) AS adminCount FROM USER WHERE permission = ? AND isLocked = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, 0);
			preparedStatement.setBoolean(2, false);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e) {	e.printStackTrace();	}
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
				//
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
	
	public boolean resetPassword(int ID)
	{
		boolean result = true;
		if(base.getPerms() < 2)
		{
			try
			{
				if (con == null)
				{	getConnection();	}
				String query = "UPDATE USER SET pass = ? WHERE ID = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, ID);
				preparedStatement.setInt(2, ID);
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
	
	public void loginSuccess(int ID)
	{
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "UPDATE USER SET failedAttempts = ? WHERE ID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, ID);
			preparedStatement.executeUpdate();
			query = "UPDATE USER SET isLocked = ? WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setInt(2, ID);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e){ e.printStackTrace(); }
	}
	
	public void loginFailure(int ID) 
	{
		try
		{
			if(!isLocked(ID))
			{
				ResultSet res = null;
				if (con == null)
				{	getConnection();	}
				String query = "SELECT failedAttempts FROM USER WHERE ID = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, ID);
				res = preparedStatement.executeQuery();
				//
				int result = res.getInt("failedAttempts");
				result += 1;
				if(ID != 0)
				{
					query = "UPDATE USER SET failedAttempts = ? WHERE ID = ?";
					preparedStatement = con.prepareStatement(query);
					preparedStatement.setInt(1, result);
					preparedStatement.setInt(2, ID);
					preparedStatement.executeUpdate();
					if (result > 5)
					{
						query = "UPDATE USER SET isLocked = ? WHERE ID = ?";
						preparedStatement = con.prepareStatement(query);
						preparedStatement.setBoolean(1, true);
						preparedStatement.setInt(2, ID);
						preparedStatement.executeUpdate();
					}
				}
			}
		}
		catch (SQLException e){e.printStackTrace();}
	}
	
	public boolean isLocked(int ID)
	{
		boolean result = false;
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT isLocked FROM USER WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			res = preparedStatement.executeQuery();
			//
			//
			result = res.getBoolean("isLocked");
		}		
		catch (SQLException e){e.printStackTrace();}
		return result;
	}
	
	public boolean deleteUser(int ID)
	{
		boolean result = true;
		if(base.getPerms() < 2)
		{
			try
			{
				if (con == null)
				{	getConnection();	}
				String query = "DELETE FROM GAME_RECORDS WHERE studentID = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, ID);
				preparedStatement.executeUpdate();
				query = "DELETE FROM USER WHERE ID = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, ID);
				preparedStatement.executeUpdate();
				query = "DELETE FROM GAME_RECORDS WHERE studentID = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, ID);
				preparedStatement.executeUpdate();				
				query = "DELETE FROM SESSION_RECORDS WHERE studentID = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, ID);
				preparedStatement.executeUpdate();				
				query = "DELETE FROM GAME_HIGH_SCORES WHERE studentID = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, ID);
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
	
	public ResultSet getUserInfo(int id)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT firstName, lastName, permission, classID FROM USER WHERE ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public ResultSet getCustomEquationInfo(String classID)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT * FROM CUSTOM_EQUATIONS WHERE classID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, classID);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public void updateCustomEquations(String classID, String equationString, int frequency, int numberOfEquations)
	{
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "UPDATE CUSTOM_EQUATIONS SET questionList = ?, frequency = ?, numberOfEquations = ? WHERE classID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, equationString);
			preparedStatement.setInt(2, frequency);
			preparedStatement.setInt(3, numberOfEquations);
			preparedStatement.setString(4, classID);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace(); 
		}
	}
	
	public int importUsers(File csvFile)
	{
		//CSV file should have user ID, first name, last name, class ID, permission level, new line.
		//Username default - first letter of first name, first letter of last name, last 3 digits of user ID 
		//Password default - user ID
		
		//Username default subject to change.
		//Duplicate users are ignored.
        BufferedReader br = null;
        String line = "";
        String delimiter = ",";
        int currentLine = 0;
        if(base.getPerms() < 2)
        {
            try 
            {
                br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) 
                {
                	currentLine++;
                	//TODO catch invalid input
                    String[] values = line.split(delimiter);
                    String idString = values[0].trim();
                    String firstName = values[1].trim();
                    String lastName = values[2].trim();
                    String classID = values[3].trim();
                    String userName = firstName.substring(0, 1) 
                    				+ lastName.substring(0, 1) 
                    				+ idString.substring(idString.length() - 4);
                    System.out.println(userName);
                    int id = Integer.parseInt(idString);
                    if(userExists(id))
                    {
                    	System.out.println("User with ID " + idString + " already exists.");
                    }
                    else
                    {
                    	addUser(id, userName, idString, firstName, lastName, classID, Integer.parseInt(values[4].trim()));
                    }
                }
                currentLine = -1;
            } 
    		catch (IOException e) {	e.printStackTrace();	}
            finally 
            {
                if (br != null) 
                {
                    try 
                    {	br.close();	} 
                    catch (IOException e) 
                    {	 e.printStackTrace();	}
                }
            }
        }
        return currentLine;
    }

	private void addCustomEquations(String classID, String questionList, int numberOfEquations, int frequency)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO CUSTOM_EQUATIONS VALUES( ?, ?, ?, ?);");		
			preparedStatement.setString(1, classID);
			preparedStatement.setString(2, questionList);
			preparedStatement.setInt(3, numberOfEquations);
			preparedStatement.setInt(4, frequency);
			preparedStatement.execute();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	public ResultSet getStudents(String classID)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT ID, userName, firstName, lastName from USER WHERE classID = ? AND permission = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, classID);
			preparedStatement.setInt(2, 3);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e) { e.printStackTrace(); }		
		return res;
	}
	
	public ResultSet getAllUsers()
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT ID, userName, firstName, lastName, classID from USER WHERE NOT ID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, 0);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e) { e.printStackTrace(); }		
		return res;
	}
	
	private boolean userExists(int ID)
	{
		boolean result = false;
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT ID FROM USER WHERE ID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			res = preparedStatement.executeQuery();
			result = res.next();
		}
		catch (SQLException e) {	e.printStackTrace();	}
		return result;
	}
	
	public void addGameRecord(int studentID, int gameID, int questionsAnswered, int questionsCorrect, int guesses, int totalSeconds, int score, String classID, String firstName, String lastName)
	{
		String date = Controller.dtf.format(LocalDateTime.now());
		if (con == null)
		{	getConnection();	}
		try 
		{
			//(studentID, gameID, questionsAnswered, questionsCorrect, guesses, totalSeconds, datePlayed, score, classID)
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO GAME_RECORDS(studentID, gameID, questionsAnswered, questionsCorrect, guesses, totalSeconds, datePlayed, score, classID) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);");
			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, gameID);
			preparedStatement.setInt(3, questionsAnswered);
			preparedStatement.setInt(4, questionsCorrect);
			preparedStatement.setInt(5, guesses);
			preparedStatement.setInt(6, totalSeconds);
			preparedStatement.setString(7, date);
			preparedStatement.setInt(8, score);
			preparedStatement.setString(9, classID);
			preparedStatement.executeUpdate();
			
			updateGameHighscore(score, studentID, firstName, lastName, classID, gameID);
			updateSession(studentID, date, questionsAnswered, questionsCorrect, score);
		}
		catch (SQLException e) {e.printStackTrace();}
	}

	public boolean addUser(int id, String userName, String pass, String firstName, String lastName, String classID, int permissions)
	{
		boolean result = false;
		if (con == null)
		{	getConnection();	}
		try 
		{
			if(!userExists(id))
			{
				PreparedStatement preparedStatement;
				preparedStatement = con.prepareStatement("INSERT INTO USER VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, userName);
				preparedStatement.setString(3, pass);
				preparedStatement.setString(4, firstName);
				preparedStatement.setString(5, lastName);
				preparedStatement.setString(6, classID);
				preparedStatement.setInt(7, permissions);
				preparedStatement.setInt(8, 0);
				preparedStatement.setBoolean(9, false);
				preparedStatement.setBoolean(10, true);
				preparedStatement.setBoolean(11, true);
				preparedStatement.setBoolean(12, true);
				preparedStatement.execute();
				result = true;
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	
	public void updateGameHighscore(int score, int studentID, String firstName, String lastName, String classID, int gameID)
	{
		try
		{
			ResultSet res = null;
			if (con == null)
			{	getConnection();	}
			String query = "SELECT score FROM GAME_HIGH_SCORES WHERE studentID = ? AND gameID = ?;";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, gameID);
			res = preparedStatement.executeQuery();
			if(res.next())
			{
				if(res.getInt("score") < score)
				{
					query = "UPDATE GAME_HIGH_SCORES SET score = ? WHERE studentID = ? AND gameID = ?;";
					preparedStatement = con.prepareStatement(query);
					preparedStatement.setInt(1, score);
					preparedStatement.setInt(2, studentID);
					preparedStatement.setInt(3, gameID);
					preparedStatement.executeUpdate();
				}
			}
			else
			{
				insertGameHighscore(score, studentID, firstName, lastName, classID, gameID);
			}
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	private void insertGameHighscore(int score, int studentID, String firstName, String lastName, String classID, int gameID)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO GAME_HIGH_SCORES VALUES(?, ?, ?, ?, ?, ?);");		
			preparedStatement.setInt(1, score);
			preparedStatement.setInt(2, studentID);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, lastName);
			preparedStatement.setString(5, classID);
			preparedStatement.setInt(6, gameID);
			preparedStatement.execute();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	public int getPersonalHighscore(int studentID, int gameID)
	{
		int result = 0;
		try
		{
			ResultSet res = null;
			if (con == null)
			{	getConnection();	}
			String query = "SELECT score FROM GAME_HIGH_SCORES WHERE studentID = ? AND gameID = ?;";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, gameID);
			res = preparedStatement.executeQuery();
			if(res.next())
			{
				result = res.getInt("score");
			}
		}
		catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	
	public boolean userNameExists(String userName) 
	{
		boolean result = false;
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT userName FROM USER WHERE userName = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, userName);
			res = preparedStatement.executeQuery();
			result = res.next();
		}
		catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	
	public int getID(String userName)
	{
		ResultSet res = null;
		int result = -1;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT ID FROM USER WHERE userName = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, userName);
			res = preparedStatement.executeQuery();
			res.next();
			result = res.getInt("ID");
		}
		catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	
	public ResultSet getClassHighscore(String classID, int gameID)
	{
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT MAX(score) AS maxScore FROM GAME_HIGH_SCORES WHERE gameID = ? AND classID = ?;";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, gameID);
			preparedStatement.setString(2, classID);
			res = preparedStatement.executeQuery();
			res.next();
			int max = res.getInt("maxScore");
			query = "SELECT score, firstName, lastName FROM GAME_HIGH_SCORES WHERE score = ? AND gameID = ? AND classID = ?;";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, max);
			preparedStatement.setInt(2, gameID);
			preparedStatement.setString(3, classID);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e) {e.printStackTrace();}
		return res;
	}
	
	public ResultSet getHighscore(int gameID)
	{
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT MAX(score) AS maxScore FROM GAME_HIGH_SCORES WHERE gameID = ?;";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, gameID);
			res = preparedStatement.executeQuery();
			res.next();
			int max = res.getInt("maxScore"); //find the owner of the max score next
			query = new String("SELECT score, firstName, lastName FROM GAME_HIGH_SCORES WHERE score = ? AND gameID = ?;");
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, max);
			preparedStatement.setInt(2, gameID);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e) {e.printStackTrace();}
		return res;
	}
	
	public boolean wantInstructions(String gameInstructions, int studentID)
	{
		boolean result = true;
		try
		{
			ResultSet res = null;
			if (con == null)
			{	getConnection();	}
			String query = "SELECT " + gameInstructions + " FROM USER WHERE ID = ?;";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, studentID);
			res = preparedStatement.executeQuery();
			if(res.next())
			{
				result = res.getBoolean(gameInstructions);
			}
		}
		catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	
	public void setWantInstructions(String gameInstructions, int studentID, boolean value)
	{
		//int valueAsInt = value ? 1 : 0;
		try
		{
			if (con == null)
			{	getConnection();	}
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE USER SET " + gameInstructions + " = ? WHERE ID = ?;");
			preparedStatement.setBoolean(1, value);
			preparedStatement.setInt(2, studentID);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	public ResultSet getGameRecords(int studentID)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT gameID, questionsAnswered, questionsCorrect, datePlayed, score FROM GAME_RECORDS WHERE studentID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, studentID);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public ResultSet getStats(int studentID)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT SUM(score) AS scoreSum, SUM(gamesPlayed) AS gameSum, SUM(questionsAnswered) AS answerSum, SUM(questionsCorrect) AS correctSum FROM SESSION_RECORDS WHERE studentID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, studentID);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public ResultSet getSessionRecords(int studentID)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT datePlayed, gamesPlayed, questionsAnswered, questionsCorrect, score FROM SESSION_RECORDS WHERE studentID = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, studentID);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public ResultSet getCurrentSessionRecords(int studentID, String date)
	{
		ResultSet res = null;
		PreparedStatement preparedStatement;
		if (con == null)
		{	getConnection();	}
		try 
		{
			String query = "SELECT * FROM SESSION_RECORDS WHERE studentID = ? AND datePlayed = ?";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, date);
			res = preparedStatement.executeQuery();
		}		
		catch (SQLException e){e.printStackTrace();}
		return res;
	}
	
	public void updateSession(int studentID, String date, int questionsAnswered, int questionsCorrect, int score)
	{
		if (con == null)
		{	getConnection();	}
		ResultSet res = getCurrentSessionRecords(studentID, date);
		try
		{
			if(res.next())
			{
				int gamesPlayed = res.getInt("gamesPlayed") + 1;
				questionsAnswered += res.getInt("questionsAnswered");
				questionsCorrect += res.getInt("questionsCorrect");
				score += res.getInt("score");
				String query = "UPDATE SESSION_RECORDS SET gamesPlayed = ?, questionsAnswered = ?, questionsCorrect = ?, score = ? WHERE studentID = ? AND datePlayed = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, gamesPlayed);
				preparedStatement.setInt(2, questionsAnswered);
				preparedStatement.setInt(3, questionsCorrect);
				preparedStatement.setInt(4, score);
				preparedStatement.setInt(5, studentID);
				preparedStatement.setString(6, date);
				preparedStatement.executeUpdate();
			}
			else
			{
				addSessionRecord(studentID, date, questionsAnswered, questionsCorrect, score);
			}
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	public void addSessionRecord(int studentID, String date, int questionsAnswered, int questionsCorrect, int score)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			//studentID, datePlayed, gamesPlayed, questionsAnswered, questionsCorrect, score
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO SESSION_RECORDS VALUES(?, ?, ?, ?, ?, ?);");
			preparedStatement.setInt(1, studentID);
			preparedStatement.setString(2, date);
			preparedStatement.setInt(3, 1);
			preparedStatement.setInt(4, questionsAnswered);
			preparedStatement.setInt(5, questionsCorrect);
			preparedStatement.setInt(6, score);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	/*
	public void addClass(String classID)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO CLASS VALUES(?);");
			preparedStatement.setString(1, classID);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	
	private boolean classExists(String classID)
	{
		boolean result = false;
		ResultSet res = null;
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT * FROM CLASS WHERE classID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, classID);
			res = preparedStatement.executeQuery();
			result = res.next();
		}
		catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	*/
	
	private void getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			String databaseFilePath = "jdbc:sqlite:C:/ProgramData/MPDKWID";
			con = DriverManager.getConnection(databaseFilePath);
		}
		catch (SQLException | ClassNotFoundException e)
		{
			//TODO Mac?
			try			
			{
				Class.forName("org.sqlite.JDBC");
				File homedir = new File(System.getProperty("user.home"));
				String databaseFilePath = "jdbc:sqlite:" + homedir + "/MPDKWID";
				con = DriverManager.getConnection(databaseFilePath);
			}
			catch (SQLException | ClassNotFoundException e2)
			{
				e2.printStackTrace();
				System.out.println("linux fix didn't work");
			}
		}

		
		if (!hasData)
		{
			hasData = true;
			Statement state;
			try
			{
				state = con.createStatement();
				
				// drop table if exists
				//state.execute("DROP TABLE IF EXISTS USER;");
				ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='USER'");
				if (!res.next())
				{
					System.out.println("Building the User table.");
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE USER("
							+ "ID INTEGER," + "userName VARCHAR(15)," + "pass VARCHAR(15)," + "firstName VARCHAR(30)," + "lastName VARCHAR(30)," 
							+ "classID VARCHAR(5)," + "permission INTEGER," + "failedAttempts INTEGER," + "isLocked BOOLEAN,"
							+ "game1Instructions BOOLEAN," + "game2Instructions BOOLEAN," + "game3Instructions BOOLEAN,"
							+ "PRIMARY KEY (ID));");
					
					addUser(000000, "root", "root", "Root", "User", "00", 0);
					addUser(888888, "Rin", "root", "Rin", " ", "2F", 2);
					addUser(111111, "deft", "deft", "Default", "Teacher", "1A", 2);
					addUser(222222, "defs", "defs", "Default", "Student", "1A", 3);
					addUser(131313, "Aaron", "0451", "Aaron", "Smith", "2F", 3);
				}
				
				//Drop table			
				//state.execute("DROP TABLE IF EXISTS CUSTOM_EQUATIONS;");
				ResultSet customEq = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='CUSTOM_EQUATIONS'");
				if (!customEq.next())
				{
					System.out.println("Building the custom equations table.");
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE CUSTOM_EQUATIONS("
							+ "classID VARCHAR(5)," + "questionList VARCHAR(600)," + "numberOfEquations INTEGER," + "frequency INTEGER," 
							+ "FOREIGN KEY (classID) REFERENCES USER(classID),"
							+ "PRIMARY KEY (classID));");
					
					addCustomEquations("1A", "5+10:7-2", 2, 5);
				}
				
				// drop table if exists
				//state.execute("DROP TABLE IF EXISTS GAME_RECORDS;");
				ResultSet gameRecords = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='GAME_RECORDS'");
				
				if (!gameRecords.next())
				{
					System.out.println("Building Game Records table.");
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE GAME_RECORDS("
							+ "studentID INTEGER," + "gameID INTEGER," 
							+ "questionsAnswered INTEGER," + "questionsCorrect INTEGER," + "guesses INTEGER," + "totalSeconds INTEGER,"
							+ "datePlayed VARCHAR(19)," + "score INTEGER," + "classID VARCHAR(6),"
							//+ "PRIMARY KEY (recordID),"  
							+ "FOREIGN KEY (studentID) REFERENCES USER(ID));");
				}
				
				//drop
				//state.execute("DROP TABLE IF EXISTS GAME_HIGH_SCORES");
				ResultSet gameHighScores = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='GAME_HIGH_SCORES'");
				if(!gameHighScores.next())
				{
					System.out.println("Building Game High Scores Table");
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE GAME_HIGH_SCORES("
							+ "score INTEGER," + "studentID INTEGER," 
							+ "firstName VARCHAR(30)," + "lastName VARCHAR(30)," + "classID VARCHAR(6)," 
							+ "gameID INTEGER);");
				}
				
				//drop
				//state.execute("DROP TABLE IF EXISTS SESSION_RECORDS");
				ResultSet sessions = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='SESSION_RECORDS'");
				if(!sessions.next())
				{
					System.out.println("Building Session Records Table");
					state = con.createStatement();
					state.executeUpdate("CREATE TABLE SESSION_RECORDS("
							+ "studentID INTEGER," + "datePlayed VARCHAR(19)," + "gamesPlayed INTEGER," + "questionsAnswered INTEGER," 
							+ "questionsCorrect INTEGER,"
							+ "score INTEGER);");
				}
				
			}
			catch (SQLException e){ e.printStackTrace(); }
		}
	}

}
