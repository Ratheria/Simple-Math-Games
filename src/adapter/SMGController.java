/**
 *	@author Ariana Fairbanks
 */

package adapter;

import java.sql.*;
import model.SQLiteData;
import view.SMGFrame;

public class SMGController
{
	private SQLiteData database;
	private SMGFrame frame;
	private int state; //login screen, 

	public void start()
	{
		database = new SQLiteData();
		state = 0;
		frame = new SMGFrame(this);
	}
	
	public int getState()
	{
		return state;
	}

}
