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
		boolean result = false;
		ResultSet res = database.compareLogin(userName, pass);
		try
		{
			if(res.next())
			{
				System.out.println("Done.");
				state = 1;
				int id = res.getInt("ID");
				System.out.println(id);
				currentUser = id;
				frame.updateState();
				//TODO fix update states
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
