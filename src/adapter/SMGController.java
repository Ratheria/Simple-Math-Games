/**
 *	@author Ariana Fairbanks
 */

package adapter;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.SQLiteData;
import view.SMGFrame;

public class SMGController
{
	private SQLiteData database;
	private SMGFrame frame;
	private int state; //login screen, root menu, teacher menu, student menu, settings, password change, 
	private int lastState;
	private int ID;
	private String firstName;
	private String lastName;
	private String classID;
	private int permissions; //root, teacher, student

	public void start()
	{
		database = new SQLiteData();
		frame = new SMGFrame(this);
		logout();
	}
	
	public void checkLogin(String userName, String pass)
	{
		ResultSet res = database.compareLogin(userName, pass);
		try
		{
			if(res.next())
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
			}
			else
			{
				JPanel errorPanel = new JPanel();
				JOptionPane.showMessageDialog(errorPanel, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (SQLException e){}
	}
	
	public void changePassword(String pass, String newPass)
	{
		boolean result = database.changeLogin(ID, pass, newPass);
		if(result)
		{
			JPanel errorPanel = new JPanel();
			JOptionPane.showMessageDialog(errorPanel, "Password changed.", "Done", JOptionPane.ERROR_MESSAGE);
			changeState(lastState);
		}
		else
		{
			JPanel errorPanel = new JPanel();
			JOptionPane.showMessageDialog(errorPanel, "Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void returnToMenu()
	{
		if(permissions == 0)
		{
			changeState(1);
			//root menu
		}
		else if(permissions == 1)
		{
			changeState(2);
			//teacher menu
		}
		else
		{
			changeState(3);
			//student menu
		}
	}
	
	public void logout()
	{
		state = 0;
		lastState = 0;
		ID = 0;
		firstName = "";
		lastName = "";
		classID = "";
		permissions = 2;
		frame.updateState();
	}
	
	public void changeState(int nextState)
	{
		lastState = state;
		state = nextState;
		frame.updateState();
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
	
	public int getState()
	{
		return state;
	}

}
