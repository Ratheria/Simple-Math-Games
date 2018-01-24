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
	private int state; //login screen, 
	private int currentUser;

	public void start()
	{
		database = new SQLiteData();
		state = 0;
		currentUser = 0;
		frame = new SMGFrame(this);
	}
	
	public void checkLogin(String userName, String pass)
	{
		//System.out.println("1");
		boolean result = false;
		ResultSet res = database.compareLogin(userName, pass);
		try
		{
			if(res.next())
			{
				System.out.println("Done.");
				state = 1;
				currentUser = res.getInt(1);
				//TODO change screens
			}
			else
			{
				JPanel errorPanel = new JPanel();
				JOptionPane.showMessageDialog(errorPanel, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (SQLException e){}
	}
	
	public int getState()
	{
		return state;
	}

}
