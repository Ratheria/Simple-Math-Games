package model;

import static org.junit.Assert.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import adapter.Controller;

public class DatabaseTesting 
{
	@Test
	public void testCorrectDatabase() 
	{
		Controller control = new Controller();
		MySQLData database = new MySQLData(control);

		try 
		{
			ResultSet result = database.query("SELECT DATABASE();");
			while (result.next()) 
			{
				String name = result.getString(1);
				assertEquals(name, "smg");
			}
		} 
		catch (SQLException e) 
		{	e.printStackTrace();	}
	}
}
