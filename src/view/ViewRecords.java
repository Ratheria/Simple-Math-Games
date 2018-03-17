package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import adapter.Controller;
import adapter.ViewStates;

public class ViewRecords extends JPanel 
{
	private static final long serialVersionUID = -7386656563185975615L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel header;
	private JButton backButton;
	private JButton viewRecordsButton;
	private JTextField studentLookupField;
	private JTable studentRecordsSet;
	private JScrollPane scrollPane;
	
	public ViewRecords(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		header = new JLabel("Select Student Records");
		backButton = new JButton(" BACK ");
		studentLookupField = new JTextField();
		viewRecordsButton = new JButton(" Select Student Records ");
		studentRecordsSet = new JTable();

		setUpLayout();
		setUpListeners();
	}
	
	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 20.0};
		layout.columnWidths = new int[]{0, 0, 0};
		layout.columnWeights = new double[]{0.0, 1.0, 1.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(0, 0, 0));
		
		header.setVerticalAlignment(SwingConstants.TOP);
		header.setForeground(new Color(135, 206, 250));
		header.setFont(new Font("MV Boli", Font.PLAIN, 35));
		GridBagConstraints gbc_header = new GridBagConstraints();
		gbc_header.anchor = GridBagConstraints.NORTHWEST;
		gbc_header.gridwidth = 4;
		gbc_header.insets = new Insets(15, 10, 5, 0);
		gbc_header.gridx = 1;
		gbc_header.gridy = 0;
		
		backButton.setFont(new Font("MV Boli", Font.PLAIN, 25));
		backButton.setForeground(new Color(135, 206, 250));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;		
		
		studentLookupField.setFont(new Font("MV Boli", Font.PLAIN, 20));
		studentLookupField.setForeground(new Color(135, 206, 250));
		studentLookupField.setBackground(new Color(0, 0, 0));
		studentLookupField.setToolTipText("Username");
		studentLookupField.setBorder(new CompoundBorder(new LineBorder(new Color(30, 144, 255)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_studentLookupField = new GridBagConstraints();
		gbc_studentLookupField.gridwidth = 4;
		gbc_studentLookupField.insets = new Insets(5, 100, 5, 5);
		gbc_studentLookupField.fill = GridBagConstraints.HORIZONTAL;
		gbc_studentLookupField.gridx = 0;
		gbc_studentLookupField.gridy = 2;
		
		viewRecordsButton.setFont(new Font("MV Boli", Font.PLAIN, 20));
		viewRecordsButton.setForeground(new Color(135, 206, 250));
		viewRecordsButton.setBackground(new Color(70, 130, 180));
		viewRecordsButton.setFocusPainted(false);
		viewRecordsButton.setContentAreaFilled(false);
		viewRecordsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_viewRecordsButton = new GridBagConstraints();
		gbc_viewRecordsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_viewRecordsButton.insets = new Insets(5, 10, 5, 100);
		gbc_viewRecordsButton.gridx = 4;
		gbc_viewRecordsButton.gridy = 2;
		
		studentRecordsSet.setForeground(new Color(176, 224, 230));
		studentRecordsSet.setFont(new Font("Arial", Font.PLAIN, 20));
		studentRecordsSet.setBackground(new Color(0, 0, 0));
		JTableHeader tableHeader = studentRecordsSet.getTableHeader();
		tableHeader.setForeground(new Color(176, 224, 230));
		tableHeader.setFont(new Font("Arial", Font.PLAIN, 25));
		tableHeader.setBackground(new Color(0, 0, 0));
		tableHeader.setBorder(new LineBorder(new Color(70, 130, 180), 1));
		scrollPane = new JScrollPane(studentRecordsSet);
		scrollPane.getViewport().setForeground(new Color(176, 224, 230));
		scrollPane.getViewport().setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(new Color(0, 0, 0));
		scrollPane.setBorder(new LineBorder(new Color(70, 130, 180), 1));
		GridBagConstraints gbc_studentRecordsSet = new GridBagConstraints();
		gbc_studentRecordsSet.gridwidth = 5;
		gbc_studentRecordsSet.gridy = 4;
		gbc_studentRecordsSet.insets = new Insets(0, 20, 20, 20);
		gbc_studentRecordsSet.fill = GridBagConstraints.BOTH;
		gbc_studentRecordsSet.gridx = 0;
		
		add(header, gbc_header);
		add(backButton, gbc_backButton);
		add(studentLookupField, gbc_studentLookupField);
		add(viewRecordsButton, gbc_viewRecordsButton);
		add(scrollPane, gbc_studentRecordsSet);
	}
	
	private void setUpListeners() 
	{
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick)
			{	base.returnToMenu();	}
		});
		
		viewRecordsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				int studentID = Integer.parseInt(studentLookupField.getText());
				ResultSet res = base.lookupStudent(studentID);
				try 
				{	studentRecordsSet = new JTable(buildTableModel(res));	}
				catch (SQLException e) { e.printStackTrace(); }
				removeAll();
				setUpLayout();
				revalidate();
				repaint();
			}
		});
		
	}
	
	private static DefaultTableModel buildTableModel(ResultSet studentRecords) throws SQLException 
	{
		ResultSetMetaData metaData = studentRecords.getMetaData();

	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) 
	    {	columnNames.add(metaData.getColumnName(column));	}

	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (studentRecords.next()) 
	    {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
	        {
	            vector.add(studentRecords.getObject(columnIndex));
	            //System.out.println(studentRecords.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames);
	}
}
