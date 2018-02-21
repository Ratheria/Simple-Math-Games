/**
 *	@author Ariana Fairbanks
 */
package adapter;
import java.io.File;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.SQLiteData;
import view.Frame;

public class Controller
{
	private JPanel errorPanel;
	private SQLiteData database;
	private Frame frame;
	private ViewStates state;
	private ViewStates lastState;
	private int ID;
	private String firstName;
	private String lastName;
	private String classID;
	private int permissions; //root, subroot, teacher, student

	public void start()
	{
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
			if(database.isLocked(userName))
			{
				JOptionPane.showMessageDialog(errorPanel, "This account has been locked due to too many failed login attempts.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(res.next())
			{
				System.out.println("Done.");
				ID = res.getInt("ID");
				permissions = database.permission(ID);
				firstName = database.firstName(ID);
				lastName = database.lastName(ID);
				classID = database.classID(ID);
				returnToMenu();
				System.out.println(ID);
				frame.updateState();
				database.loginSuccess(userName);
			}
			else
			{
				JOptionPane.showMessageDialog(errorPanel, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
				if(!(userName.equals("root")))
				{
					database.loginFailure(userName);
				}
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
	{	database.importUsers(file);	}
	
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

	public ResultSet lookupStudent(int studentID) //throws SQLException 
	{
		ResultSet studentRecords = null;
		
		studentRecords = database.selectStudentRecord(studentID);
		return studentRecords;
	}
}
