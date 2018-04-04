package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel
{
	private static final long serialVersionUID = -1675752138627589999L;

	public CustomTableModel(Vector<Vector<Object>> data, Vector<String> columnNames)
	{
		super(data, columnNames);
	}

	public CustomTableModel(Object[][] data, Object[] columnNames)
	{
		super(data, columnNames);
	}

	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

	public static CustomTableModel buildTableModel(ResultSet res, String[] header) throws SQLException
	{
		Vector<String> columnNames = new Vector<String>();
		int columnCount = header.length;

		for (int column = 0; column < columnCount; column++)
		{
			columnNames.add(header[column]);
		}

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (res.next())
		{
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
			{
				vector.add(res.getObject(columnIndex));
			}
			data.add(vector);
		}
		return new CustomTableModel(data, columnNames);
	}

	public static CustomTableModel buildTableModel(Object[][] data, Object[] columnNames)
	{
		return new CustomTableModel(data, columnNames);
	}
}
