/**
 *	@author Ariana Fairbanks
 */

package adapter;

import java.sql.*;
import model.SQLiteData;

public class SMGController
{

	private SQLiteData database;

	public void start()
	{

	}

	public ResultSet test() throws ClassNotFoundException, SQLException
	{
		ResultSet result = null;
		database = new SQLiteData();
		result = database.displayUsers();
		return result;
	}
}
