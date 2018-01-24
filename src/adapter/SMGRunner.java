/**
 *	@author Ariana Fairbanks
 */

package adapter;

import java.sql.ResultSet;

public class SMGRunner
{
	public static void main(String[] args)
	{
		SMGController test = new SMGController();
		ResultSet rs;

		try
		{
			rs = test.test();

			while (rs.next())
			{
				System.out.println(rs.getString("firstName") + " " + rs.getString("lastName"));
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
