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
	private int permissions; //root, subroot, teacher, student
	private int frequency;
	private String firstName;
	private String lastName;
	private String classID;
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
			{
				JOptionPane.showMessageDialog(errorPanel, "This account has been locked due to too many failed login attempts.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(hasRes)
			{
				System.out.println("Done.");
				ID = res.getInt("ID");
				permissions = database.permission(ID);
				firstName = database.firstName(ID);
				lastName = database.lastName(ID);
				classID = database.getClassID(ID);
				customEquations = this.getCustomEquations(classID);
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
		catch (SQLException e){	}
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
		if(permissions < 2)
		{	changeState(ViewStates.rootMenu);	}
		else if(permissions == 2)
		{	changeState(ViewStates.teacherMenu);	}
		else
		{	changeState(ViewStates.studentMenu);	}
	}
	
	public void logout()
	{
		state = ViewStates.login;
		lastState = ViewStates.login;
		ID = 0;
		firstName = "";
		lastName = "";
		classID = "";
		permissions = 3;
		frequency = 5;
		customEquations = null;
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
		if(permissions < 2)
		{
			database.loginSuccess(userName);
		}
		if(database.isLocked(userName))
		{
			JOptionPane.showMessageDialog(errorPanel, "Failed to unlock account.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(errorPanel, "Account successfully unlocked.", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void resetPassword(String userName)
	{
		boolean change = false;
		if(permissions < 2)
		{
			change = database.resetPassword(userName);
		}
		if(!change)
		{
			JOptionPane.showMessageDialog(errorPanel, "Password not reset.", "", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(errorPanel, "Password successfully reset.", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void importUsers(File file)
	{	
		int result = database.importUsers(file);	
		if(result == -1)
		{
			JOptionPane.showMessageDialog(errorPanel, "Users successfully imported.", "", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(errorPanel, "Something went wrong at line " + result + ".", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String getName()
	{	return firstName;	}
	
	public String getLastName()
	{	return lastName; }
	
	public String getFullName()
	{	return firstName + " " + lastName;	}
	
	public String getClassID()
	{	return classID;	}
	
	public ViewStates getState()
	{	return state;	}
	
	public int getPerms()
	{	return permissions;	}

	public int getFrequency()
	{	return frequency;	}

	public List<String> getEquations()
	{	return customEquations;	}

	public ResultSet lookupStudent(int studentID) 
	{
		ResultSet result = null;
		result = database.selectStudentRecord(studentID);
		return result;
	}
	
	private List<String> getCustomEquations(String classID)
	{
		List<String> result = null;
		String questionList = null;
		try
		{
			ResultSet res = database.getCustomEquation(classID);
			if(res.next())
			{
				questionList = res.getString("questionList");
				List<String> customEquations = Arrays.asList(questionList.split(":"));
				result = customEquations;
			}
		}
		catch (SQLException e){	}
		return result;
	}
}
