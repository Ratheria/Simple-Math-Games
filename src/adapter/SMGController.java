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
	private int state; //login screen, student menu,
	private int ID;
	private String firstName;
	private String lastName;
	private String classID;
	private boolean teacher;

	public void start()
	{
		database = new SQLiteData();
		frame = new SMGFrame(this);
		logout();
	}
	
	public void checkLogin(String userName, String pass)
	{
		boolean result = false;
		ResultSet res = database.compareLogin(userName, pass);
		try
		{
			if(res.next())
			{
				System.out.println("Done.");
				ID = res.getInt("ID");
				teacher = database.isTeacher(ID);
				firstName = database.firstName(ID);
				lastName = database.lastName(ID);
				classID = database.classID(ID);
				if(teacher)
				{
					state = 2;
				}
				else
				{
					state = 1;
				}
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
	
	public void logout()
	{
		state = 0;
		ID = 0;
		firstName = "";
		lastName = "";
		classID = "";
		teacher = false;
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
