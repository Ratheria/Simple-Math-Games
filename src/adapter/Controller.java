/**
 *	@author Ariana Fairbanks
 *	Special thanks to Ahmed Yakout and Kelsey Fairbanks.
 */

package adapter;

import java.io.File;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.MySQLData;
import view.Frame;

public class Controller
{
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	public static Random rng;
	public static String[] selectStudentRecordHeader = { "Student ID", "First Name", "Last Name" };
	public static String[] allUsersHeader = { "User ID", "Username", "First Name", "Last Name", "Class ID" };
	public static String[] gamesHeader = { "Game", "Total Answers", "Correct Answers", "Date", "Score" };
	public static String[] sessionsHeader = { "Date", "Games Played", "Total Answers", "Correct Answers", "Total Score" };
	public Frame frame;
	public JPanel messagePanel;
	public int questionTypes;
//	private SQLiteData database;
	private MySQLData database;
	private ViewStates state;
	private ViewStates lastState;
	private int ID;
	private int permission; // root, subroot, teacher, student
	private int frequency;
	private int numberOfEquations;
	private String firstName;
	private String lastName;
	private String classID;
	private String equationString;
	private ArrayList<String> customEquations;

	public void start()
	{
		rng = new Random();
		messagePanel = new JPanel();
//		database = new SQLiteData(this); 
		database = new MySQLData(this);
		    
		frame = new Frame(this);
		logout();
	}

	public void logout()
	{
		questionTypes = 0;
		state = ViewStates.LOGIN;
		lastState = ViewStates.LOGIN;
		ID = 0;
		firstName = "";
		lastName = "";
		classID = "";
		permission = 3;
		frequency = 0;
		numberOfEquations = 0;
		customEquations = null;
		equationString = "";
		frame.updateState();
	}
	
	public void checkLogin(String userName, String pass)
	{
		JPanel errorPanel = new JPanel();	
		
		// check if user exists here
		if (database.userNameExists(userName)) {
			ID = database.getID(userName);
			// these two steps are basically the same, but it works
			ResultSet res = database.compareLogin(userName, pass);
			try
			{
				boolean hasRes = res.next();
							
				if ((database.isLocked(ID) != 0) && hasRes)
				{
					JOptionPane.showMessageDialog(errorPanel, "This account has been locked due to too many failed login attempts.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if (hasRes)
				{
					ID = res.getInt("ID");
					res = database.getUserInfo(ID);
					if (res.next())
					{
						permission = res.getInt("permission");
						firstName = res.getString("firstName");
						lastName = res.getString("lastName");
						classID = res.getString("classID");
					}
					res = database.getCustomEquationInfo(classID);
					if (res.next())
					{
						equationString = res.getString("questionList");
						customEquations = getCustomEquationList(equationString);
						frequency = res.getInt("frequency");
						numberOfEquations = res.getInt("numberOfEquations");
					}
					returnToMenu();
					frame.updateState();
					database.loginSuccess(ID);
				}
				else
				{
					JOptionPane.showMessageDialog(errorPanel, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
					if (!(userName.equals("root")))
					{					
						database.loginFailure(ID, userName);
					}
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			}
		else
			JOptionPane.showMessageDialog(errorPanel, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void changePassword(String pass, String newPass)
	{
		boolean result = database.changeLogin(ID, pass, newPass);
		if (result)
		{
			JOptionPane.showMessageDialog(messagePanel, "Password changed.", "Done", JOptionPane.INFORMATION_MESSAGE);
			changeState(lastState);
		}
		else
		{
			JOptionPane.showMessageDialog(messagePanel, "Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void changeState(ViewStates nextState)
	{
		lastState = state;
		state = nextState;
		frame.updateState();
	}
	
	public void changeState(int studentID, int value)
	{
		lastState = state;
		if(studentID == 0)
		{	
			studentID = this.ID;	
		}
		state = ViewStates.VIEWGAMERECORDS;
		if(value == -1)
		{
			state = ViewStates.VIEWSTUDENTSTATS;
		}
		frame.updateState(studentID, value);
	}
	
	public void returnToMenu()
	{
		if (permission < 2)
		{
			changeState(ViewStates.ROOTMENU);
		}
		else if (permission == 2)
		{
			changeState(ViewStates.TEACHERMENU);
		}
		else
		{
			changeState(ViewStates.STUDENTMENU);
		}
	}
	
	public void returnToLastState()
	{
		state = lastState;
		frame.updateState();
	}

	public void unlockAccount(int ID)
	{
		JPanel errorPanel = new JPanel();
		if (permission < 2)
		{
			database.loginSuccess(ID);
		}
		if (database.isLocked(ID) != 0)
		{
			JOptionPane.showMessageDialog(errorPanel, "Failed to unlock account.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(errorPanel, "Account successfully unlocked.", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void resetPassword(int ID)
	{
		boolean change = false;
		if (permission < 2)
		{
			change = database.resetPassword(ID);
		}
		if (!change)
		{
			JOptionPane.showMessageDialog(messagePanel, "Password not reset.", "", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(messagePanel, "Password successfully reset.", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void deleteUser(int ID)
	{
		boolean change = false;
		if (permission < 2)
		{
			change = database.deleteUser(ID);
		}
		if (!change)
		{
			JOptionPane.showMessageDialog(messagePanel, "User not deleted.", "", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(messagePanel, "User successfully deleted.", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void importUsers(File file)
	{
		int result = database.importUsers(file);
		if (result == -1)
		{
			JOptionPane.showMessageDialog(messagePanel, "Users successfully imported.", "", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(messagePanel, "Something went wrong at line " + result + ".", "", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void addEquation(String newEquation)
	{
		newEquation = newEquation.trim();
		if (newEquation != null && newEquation.length() > 2)
		{
			int whitespace = newEquation.indexOf(" ");
			while (whitespace != -1)
			{
				int currentLength = newEquation.length();
				newEquation = newEquation.substring(0, whitespace) + newEquation.substring(whitespace + 1, currentLength);
				whitespace = newEquation.indexOf(" ");
			}
			int plusLocation = newEquation.indexOf("+");
			int minusLocation = newEquation.indexOf("-");
			int stringLength = newEquation.length();
			int operators = 0;

			String tempEquation = "" + newEquation;
			int tempPlus = plusLocation;
			int tempMinus = minusLocation;
			while (tempPlus > -1 || tempMinus > -1)
			{
				int operatorLocation = 0;
				operatorLocation = tempPlus;
				if (operatorLocation == -1)
				{
					operatorLocation = tempMinus;
				}
				int currentLength = tempEquation.length();
				tempEquation = tempEquation.substring(0, operatorLocation) + tempEquation.substring(operatorLocation + 1, currentLength);
				tempPlus = tempEquation.indexOf("+");
				tempMinus = tempEquation.indexOf("-");
				operators++;
			}
			if (operators < 1)
			{
				JOptionPane.showMessageDialog(messagePanel, "Please enter an addition or subtraction problem.", "", JOptionPane.ERROR_MESSAGE);
			}
			else if (operators > 1)
			{
				JOptionPane.showMessageDialog(messagePanel, "The problem you entered contained too many operators.", "", JOptionPane.ERROR_MESSAGE);
			}
			else if (stringLength < 3 || stringLength > 7)
			{
				JOptionPane.showMessageDialog(messagePanel, "The problem you entered was of invalid length.", "", JOptionPane.ERROR_MESSAGE);
			}
			else if (plusLocation == 0 || plusLocation == stringLength - 1 || minusLocation == 0 || minusLocation == stringLength - 1)
			{
				JOptionPane.showMessageDialog(messagePanel, "The problem you entered contained only one integer.", "", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				int answer = 0;
				try
				{
					if (minusLocation > 0)
					{
						List<String> expressionHalves = Arrays.asList(newEquation.split("-"));
						answer = Integer.parseInt(expressionHalves.get(0)) - Integer.parseInt(expressionHalves.get(1));
					}
					else
					{
						List<String> expressionHalves = Arrays.asList(newEquation.split("\\+"));
						answer = Integer.parseInt(expressionHalves.get(0)) + Integer.parseInt(expressionHalves.get(1));
					}
					if (answer < 0)
					{
						JOptionPane.showMessageDialog(messagePanel, "Negative answer values are not supported.", "", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						boolean repeat = false;
						for (String equation : customEquations)
						{
							if (equation.equals(newEquation))
							{
								repeat = true;
							}
						}
						if (repeat)
						{
							JOptionPane.showMessageDialog(messagePanel, "That problem has already been added.", "", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							if (numberOfEquations > 0)
							{
								equationString += ":";
							}
							equationString += newEquation;
							numberOfEquations++;
							changeCustomEquations(equationString, frequency, numberOfEquations);
							JOptionPane.showMessageDialog(messagePanel, "Equation added.", "", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				catch (NumberFormatException e)
				{
					JOptionPane.showMessageDialog(messagePanel, "Invalid characters detected.", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public ResultSet getAllUsers()
	{
		ResultSet result = null;
		result = database.getAllUsers();
		return result;
	}

	public ResultSet getStudents()
	{
		ResultSet result = null;
		result = database.getStudents(classID);
		return result;
	}
	
	public String getName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getFullName()
	{
		return firstName + " " + lastName;
	}

	public String getClassID()
	{
		return classID;
	}

	public String getEquationString()
	{
		return equationString;
	}

	public ViewStates getState()
	{
		return state;
	}

	public int getPerms()
	{
		return permission;
	}

	public int getFrequency()
	{
		return frequency;
	}

	public int getNumberOfEquations()
	{
		return numberOfEquations;
	}

	public ArrayList<String> getEquations()
	{
		return customEquations;
	}

	private ArrayList<String> getCustomEquationList(String equationString)
	{
		ArrayList<String> result = null;
		if (equationString != null)
		{
			result = new ArrayList<String>(Arrays.asList(equationString.split(":")));
		}
		return result;
	}

	public void addUser(String firstName, String lastName, String idString, String classID, int permissionLevel)
	{
		int id = Integer.parseInt(idString);
		String pass = idString;
		String userName = firstName.substring(0, 1) + lastName.substring(0, 1) + idString.substring(idString.length() - 4);
		boolean change = database.addUser(id, userName, pass, firstName, lastName, classID, permissionLevel);
		if (!change)
		{
			JOptionPane.showMessageDialog(messagePanel, "Invalid input or duplicate ID. User not added.", "", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(messagePanel, "User successfully added.", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void addGameRecord(int gameID, int questionsAnswered, int questionsCorrect, int guesses, int totalSeconds, int score)
	{
		database.addGameRecord(ID, gameID, questionsAnswered, questionsCorrect, guesses, totalSeconds, score, classID, firstName, lastName);
	}

	public void changeCustomEquations(String equationString, int frequency, int numberOfEquations)
	{
		database.updateCustomEquations(classID, equationString, frequency, numberOfEquations);
		ResultSet res = database.getCustomEquationInfo(classID);
		try
		{
			if (res.next())
			{
				this.equationString = res.getString("questionList");
				customEquations = getCustomEquationList(equationString);
				this.frequency = res.getInt("frequency");
				this.numberOfEquations = res.getInt("numberOfEquations");
			}
		}
		catch (SQLException e)
		{}
	}
	
	public boolean getInstructionPreference(String gameInstructions)
	{
		boolean result = true;
		int displayInstructions = database.wantInstructions(gameInstructions, ID);
		System.out.println(result);
		if(displayInstructions == 0)
		{
			result = false;
		}
		return result;
	}
	
	public void setInstructionPreferences(String gameInstructions, boolean value)
	{
		database.setWantInstructions(gameInstructions, ID, value);
	}
	
	public int getPersonalHighscore(int studentID, int gameID)
	{
		return database.getPersonalHighscore(studentID, gameID);
	}
	
	public ResultSet getClassHighscore(int gameID)
	{
		return database.getClassHighscore(classID, gameID);
	}
	
	public ResultSet getHighscore(int gameID)
	{
		return database.getHighscore(gameID);
	}
		
	public ResultSet getGameRecords(int studentID)
	{
		return database.getGameRecords(studentID);
	}
	
	public ResultSet getSessionRecords(int studentID)
	{
		return database.getSessionRecords(studentID);
	}
	
	public ResultSet getStats(int studentID)
	{
		return database.getStats(studentID);
	}
}


