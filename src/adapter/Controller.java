/**
 *	@author Ariana Fairbanks
 */
package adapter;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import model.SQLiteData;
import view.Frame;

public class Controller
{
	public static Random rng;
	public Frame frame;
	public JPanel errorPanel;
	private SQLiteData database;
	private ViewStates state;
	private ViewStates lastState;
	private int ID;
	private int permission; //root, subroot, teacher, student
	private int frequency;
	private int numberOfEquations;
	private String firstName;
	private String lastName;
	private String classID;
	private String equationString;
	private List<String> customEquations;

	public void start()
	{
		rng = new Random();
		errorPanel = new JPanel();
		database = new SQLiteData(this);
		frame = new Frame(this);
		logout();
	}
	
	public void checkLogin(String userName, String pass)
	{
		JPanel errorPanel = new JPanel();
		ResultSet res = database.compareLogin(userName, pass);
		try
		{
			boolean hasRes = res.next();
			if(database.isLocked(userName) && hasRes)
			{	JOptionPane.showMessageDialog(errorPanel, "This account has been locked due to too many failed login attempts.", "Error", JOptionPane.ERROR_MESSAGE);	}
			else if(hasRes)
			{
				System.out.println("Done.");
				ID = res.getInt("ID");
				res = database.getUserInfo(ID);
				if(res.next())
				{
					permission = res.getInt("permission");
					firstName = res.getString("firstName");
					lastName = res.getString("lastName");
					classID = res.getString("classID");
				}
				res = database.getCustomEquationInfo(classID);
				if(res.next())
				{
					equationString = res.getString("questionList");
					customEquations = getCustomEquationList(equationString);
					frequency = res.getInt("frequency"); 
					numberOfEquations = res.getInt("numberOfEquations");
				}
				returnToMenu();
				System.out.println(ID);
				frame.updateState();
				database.loginSuccess(userName);
			}
			else
			{
				JOptionPane.showMessageDialog(errorPanel, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
				if(!(userName.equals("root")))
				{	database.loginFailure(userName);	}
			}
		}
		catch (SQLException e){ e.printStackTrace(); }
	}
	
	public void changePassword(String pass, String newPass)
	{
		boolean result = database.changeLogin(ID, pass, newPass);
		if(result)
		{
			JOptionPane.showMessageDialog(errorPanel, "Password changed.", "Done", JOptionPane.INFORMATION_MESSAGE);
			changeState(lastState);
		}
		else
		{
			JOptionPane.showMessageDialog(errorPanel, "Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void returnToMenu()
	{
		if(permission < 2)
		{	changeState(ViewStates.rootMenu);	}
		else if(permission == 2)
		{	changeState(ViewStates.teacherMenu);	}
		else
		{	changeState(ViewStates.studentMenu);	}
	}
	
	public void returnToStudentRecords()
	{	changeState(ViewStates.viewRecords);	}
	
	public void logout()
	{
		state = ViewStates.login;
		lastState = ViewStates.login;
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
	
	public void changeState(ViewStates nextState)
	{
		lastState = state;
		state = nextState;
		frame.updateState();
	}
	
	public void unlockAccount(String userName)
	{
		JPanel errorPanel = new JPanel();
		if(permission < 2)
		{	database.loginSuccess(userName);	}
		if(database.isLocked(userName))
		{	JOptionPane.showMessageDialog(errorPanel, "Failed to unlock account.", "Error", JOptionPane.ERROR_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Account successfully unlocked.", "", JOptionPane.INFORMATION_MESSAGE);	}
	}
	
	public void resetPassword(String userName)
	{
		boolean change = false;
		if(permission < 2)
		{	change = database.resetPassword(userName);	}
		if(!change)
		{	JOptionPane.showMessageDialog(errorPanel, "Password not reset.", "", JOptionPane.ERROR_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Password successfully reset.", "", JOptionPane.INFORMATION_MESSAGE);}
	}
	
	public void importUsers(File file)
	{	
		int result = database.importUsers(file);	
		if(result == -1)
		{	JOptionPane.showMessageDialog(errorPanel, "Users successfully imported.", "", JOptionPane.INFORMATION_MESSAGE);	}
		else
		{	JOptionPane.showMessageDialog(errorPanel, "Something went wrong at line " + result + ".", "", JOptionPane.ERROR_MESSAGE);	}
	}
	
	public void addEquation(String newEquation)
	{
		newEquation = newEquation.trim();
		if(newEquation != null)
		{
			int whitespace = newEquation.indexOf(" "); 
			while(whitespace != -1)
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
			while(tempPlus > -1 || tempMinus > -1)
			{
				int operatorLocation = 0;
				operatorLocation = tempPlus;
				if(operatorLocation == -1)
				{	operatorLocation = tempMinus;	}
				int currentLength = tempEquation.length();
				tempEquation = tempEquation.substring(0, operatorLocation) + tempEquation.substring(operatorLocation + 1, currentLength);
				tempPlus = tempEquation.indexOf("+");
				tempMinus = tempEquation.indexOf("-");
				operators++;
			}
			if(operators < 1)
			{	JOptionPane.showMessageDialog(errorPanel, "Please enter an addition or subtraction problem.", "", JOptionPane.ERROR_MESSAGE);	}
			else if(operators > 1)
			{	JOptionPane.showMessageDialog(errorPanel, "The problem you entered contained too many operators.", "", JOptionPane.ERROR_MESSAGE);	}
			else if(stringLength < 3 || stringLength > 7)
			{	JOptionPane.showMessageDialog(errorPanel, "The problem you entered was of invalid length.", "", JOptionPane.ERROR_MESSAGE);	}
			else if(plusLocation == 0 || plusLocation == stringLength - 1 || minusLocation == 0 || minusLocation == stringLength - 1)
			{	JOptionPane.showMessageDialog(errorPanel, "The problem you entered contained only one integer.", "", JOptionPane.ERROR_MESSAGE);	}
			else
			{
				int answer = 0;
				if(minusLocation > 0)
				{
					List<String> expressionHalves = Arrays.asList(newEquation.split("-"));
					answer = Integer.parseInt(expressionHalves.get(0)) - Integer.parseInt(expressionHalves.get(1));
				}
				if(answer < 0)
				{	JOptionPane.showMessageDialog(errorPanel, "Negative answer values are not supported.", "", JOptionPane.ERROR_MESSAGE);	}
				else
				{
					boolean repeat = false;
					for(String equation : customEquations)
					{
						if(equation.equals(newEquation))
						{	repeat = true;	}
					}
					if(repeat)
					{	JOptionPane.showMessageDialog(errorPanel, "That problem has already been added.", "", JOptionPane.ERROR_MESSAGE);	}
					else
					{
						equationString = equationString + ":" + newEquation;
						numberOfEquations++;
						changeCustomEquations(equationString, frequency, numberOfEquations);
						JOptionPane.showMessageDialog(errorPanel, "Equation added.", "", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}
	
	public void changeCustomEquations(String equationString, int frequency, int numberOfEquations)
	{
		database.updateCustomEquations(classID, equationString, frequency, numberOfEquations);
		ResultSet res = database.getCustomEquationInfo(classID);
		try
		{
			if(res.next())
			{
				this.equationString = res.getString("questionList");
				customEquations = getCustomEquationList(equationString);
				this.frequency = res.getInt("frequency"); 
				this.numberOfEquations = res.getInt("numberOfEquations");
			}
		}
		catch(SQLException e) {	}
		System.out.println(equationString);
		System.out.println(this.frequency);
		System.out.println(numberOfEquations);
	}
	
	public ResultSet lookupStudent() 
	{
		ResultSet result = null;
		result = database.selectStudentRecord(classID);
		return result;
	}
	
	public String getName()
	{	return firstName;	}
	
	public String getLastName()
	{	return lastName; }
	
	public String getFullName()
	{	return firstName + " " + lastName;	}
	
	public String getClassID()
	{	return classID;	}
	
	public String getEquationString()
	{	return equationString;	}
	
	public ViewStates getState()
	{	return state;	}
	
	public int getPerms()
	{	return permission;	}

	public int getFrequency()
	{	return frequency;	}
	
	public int getNumberOfEquations()
	{	return numberOfEquations;	}

	public List<String> getEquations()
	{	return customEquations;	}
	
	private List<String> getCustomEquationList(String equationString)
	{
		List<String> result = null;
		if(equationString != null)
		{
			result = Arrays.asList(equationString.split(":"));
		}
		return result;
	}
	
}
