
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

public class MySQLData
{
	private Controller base;
	private static Connection con;

	public MySQLData(Controller base)
	{
		this.base = base;
		getConnection();
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
				res.next();
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
	
	public void loginFailure(int ID, String userName)
	{
		try
		{
			ResultSet res = null;
			if (con == null)
			{	getConnection();	}
			System.out.println("userNameExists");
			String getID = "SELECT ID from USER WHERE userName = ?";
			PreparedStatement prepState = con.prepareStatement(getID);
			prepState.setString(1, userName);
			res = prepState.executeQuery();
			res.next();
			ID = res.getInt("ID");
			System.out.println("ID: " + ID);

			String query = "SELECT failedAttempts FROM USER WHERE ID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, ID);
			res = preparedStatement.executeQuery();
			res.next();
			int result = res.getInt("failedAttempts");
			System.out.println("failedAttempts: " + result);
			result += 1;
			if (ID != 0) {
				query = "UPDATE USER SET failedAttempts = ? WHERE ID = ?";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, result);
				preparedStatement.setInt(2, ID);
				preparedStatement.executeUpdate();
				if (result > 5) {
					System.out.println("should be checking if locked");
					query = "UPDATE USER SET isLocked = ? WHERE ID = ?";
					preparedStatement = con.prepareStatement(query);
					preparedStatement.setInt(1, 1);
					preparedStatement.setInt(2, ID);
					preparedStatement.executeUpdate();
				}
			}
		}
		catch (SQLException e){e.printStackTrace();}
	}
	
	public int isLocked(int ID)
	{
		int result = 1;
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
			
			// isLocked is an Int, cosplaying as a boolean
			// fix it
//			System.out.println(res.getBoolean("isLocked"));
//			result = res.getBoolean("isLocked");
			res.next();
			result = res.getInt("isLocked");
		}		
		catch (SQLException e){ e.printStackTrace(); }
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
            catch (IOException e) 
            {
                e.printStackTrace();
            } 
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
	
	@SuppressWarnings("unused")
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
		catch (SQLException e){}
		return result;
	}

	private void getConnection()
	{
		String dbName = "smg";
		String host = "smg-database.cxdny0vkhuno.us-west-2.rds.amazonaws.com";
		String userName = "smg_database";
		String password = "5mg.Pa55w0rd";
        String jdbcUrl = "jdbc:mysql://" + host + ":" + "3306" + "/" + dbName + "?user=" + userName + "&password=" + password;
		try 
		{	con = DriverManager.getConnection(jdbcUrl);	}
		catch(SQLException se)
		{	se.printStackTrace();	}
	}
	
	
	
	//TODO User high scores and general stats database (games played, total score(s))
	//TODO overall high scores
	
	
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
			
			updateGameHighscore(studentID, gameID, score, classID, firstName, lastName);
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
				if(!classExists(classID))
				{
					addClass(classID);
				}
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
				preparedStatement.setInt(10, 1);
				preparedStatement.setInt(11, 1);
				preparedStatement.setInt(12, 1);
				preparedStatement.execute();
				result = true;
			}
		} 
		catch (SQLException e) 
		{	e.printStackTrace();	}
		return result;
	}
	
	public void updateGameHighscore(int studentID, int gameID, int score, String classID, String firstName, String lastName)
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
				insertGameHighscore(studentID, gameID, score, classID, firstName, lastName);
			}
		}
		catch (SQLException e){}
	}
	
	
	private void insertGameHighscore(int studentID, int gameID, int score, String classID, String firstName, String lastName)
	{
		if (con == null)
		{	getConnection();	}
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement("INSERT INTO GAME_HIGH_SCORES VALUES(?, ?, ?, ?, ?, ?);");		
			preparedStatement.setInt(1, studentID);
			preparedStatement.setInt(2, gameID);
			preparedStatement.setInt(3, score);
			preparedStatement.setString(4, classID);
			preparedStatement.setString(5, firstName);
			preparedStatement.setString(6, lastName);
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
			res.next();
			result = res.getInt("score");
		}
		catch (SQLException e){}
		return result;
	}
	
	public boolean userNameExists(String userName) {
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
		catch (SQLException e){}
		return result;
	}
	
	public int getID(String userName) {
		ResultSet res = null;
		
		try
		{
			if (con == null)
			{	getConnection();	}
			String query = "SELECT ID FROM USER WHERE userName = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, userName);
			res = preparedStatement.executeQuery();
			res.next();
			return res.getInt("ID");
		}
		catch (SQLException e){}
		return -1;
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
		catch (SQLException e){}
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
			int max = res.getInt("maxScore");
			query = new String("SELECT score, firstName, lastName FROM GAME_HIGH_SCORES WHERE score = ? AND gameID = ?;");
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, max);
			preparedStatement.setInt(2, gameID);
			res = preparedStatement.executeQuery();
		}
		catch (SQLException e){}
		return res;
	}
	
	public int wantInstructions(String gameID, int studentID)
	{
		int result = 0;
		try
		{
			ResultSet res = null;
			if (con == null)
			{	getConnection();	}
			String query = "SELECT " + gameID + " FROM USER WHERE ID = ?;";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, studentID);
			res = preparedStatement.executeQuery();
			if(res.next())
			{
				result = res.getInt(gameID);
			}
		}
		catch (SQLException e){}
		return result;
	}
	
	public void setWantInstructions(String gameID, int studentID, boolean value)
	{
		int valueAsInt = value ? 1 : 0;
		try
		{
			if (con == null)
			{	getConnection();	}
			PreparedStatement preparedStatement = con.prepareStatement("UPDATE USER SET " + gameID + " = ? WHERE ID = ?;");
			preparedStatement.setInt(1, valueAsInt);
			preparedStatement.setInt(2, studentID);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
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
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		catch (SQLException e){}
		return result;
	}
	
	//preparedStatement = con.prepareStatement("INSERT INTO GAME_RECORDS(studentID, gameID, " +
	//"questionsAnswered, questionsCorrect, guesses, totalSeconds, datePlayed, score) " +
	
}