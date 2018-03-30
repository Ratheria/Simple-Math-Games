/**
 *	@author Ariana Fairbanks
 */
package view;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import adapter.Controller;
import adapter.ViewStates;
import model.CustomTableModel;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class RootMenu extends JPanel 
{
	private static final long serialVersionUID = -5873415761431343161L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel displayName;
//	private JButton logOut;
	private JButton settingsButton;
	private JTextField userField;
	private JButton operationButton;
	private JTable dataSet;
    private TableRowSorter<TableModel> rowSorter;
	private JScrollPane scrollPane;
	private JButton addUsersButton;
	
	public RootMenu(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		displayName = new JLabel(" ");
//		logOut = new JButton(" Log Out ");
		settingsButton = new JButton("    ");
		userField = new JTextField();
		operationButton = new JButton(" RESET ");
		dataSet = new JTable();
		rowSorter = new TableRowSorter<>(dataSet.getModel());
		dataSet.setRowSorter(rowSorter);
		
		//TODO
		//ResultSet res = base.getAllUsers();
		//try 
		//{	dataSet = new JTable(CustomTableModel.buildTableModel(res));	}
		//catch (SQLException e) { e.printStackTrace(); }	
		addUsersButton = new JButton(" Add Users ");

		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		layout.columnWidths = new int[]{0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(128, 128, 128), 10));
		setForeground(new Color(105, 105, 105));
		setBackground(new Color(245, 245, 245));
		
		displayName.setVerticalAlignment(SwingConstants.TOP);
		displayName.setForeground(new Color(105, 105, 105));
		displayName.setFont(new Font("Arial", Font.PLAIN, 35));
		displayName.setText(base.getFullName());
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 4;
		gbc_displayName.insets = new Insets(15, 10, 5, 0);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;
		
/*		
  		logOut.setFont(new Font("Arial", Font.PLAIN, 30));
		logOut.setForeground(new Color(220, 220, 220));
		logOut.setBackground(new Color(0, 0, 0));
		logOut.setFocusPainted(false);
		logOut.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_logOut = new GridBagConstraints();
		gbc_logOut.gridwidth = 2;
		gbc_logOut.anchor = GridBagConstraints.EAST;
		gbc_logOut.insets = new Insets(0, 0, 20, 20);
		gbc_logOut.gridx = 3;
		gbc_logOut.gridy = 5;
*/
		
		settingsButton.setFont(new Font("Arial", Font.PLAIN, 25));
		settingsButton.setForeground(new Color(192, 192, 192));
		settingsButton.setBackground(new Color(192, 192, 192));
		settingsButton.setFocusPainted(false);
		settingsButton.setBorder(new LineBorder(new Color(105, 105, 105), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_settingsButton.insets = new Insets(20, 20, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;
		
		userField.setFont(new Font("Arial", Font.PLAIN, 20));
		userField.setBackground(new Color(220, 220, 220));
		userField.setForeground(new Color(0, 0, 0));
		userField.setToolTipText("Username");
		userField.setBorder(new CompoundBorder(new LineBorder(new Color(105, 105, 105)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_userField = new GridBagConstraints();
		gbc_userField.gridwidth = 4;
		gbc_userField.insets = new Insets(0, 40, 5, 5);
		gbc_userField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField.gridx = 0;
		gbc_userField.gridy = 2;
		
		operationButton.setFont(new Font("Arial", Font.PLAIN, 20));
		operationButton.setForeground(new Color(105, 105, 105));
		operationButton.setBackground(new Color(0, 0, 0));
		operationButton.setFocusPainted(false);
		operationButton.setContentAreaFilled(false);
		operationButton.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_operationButton = new GridBagConstraints();
		gbc_operationButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_operationButton.insets = new Insets(0, 10, 5, 40);
		gbc_operationButton.gridx = 4;
		gbc_operationButton.gridy = 2;
		
		
		dataSet.setForeground(new Color(0, 0, 128));
		dataSet.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dataSet.setBackground(new Color(240, 240, 245));
		dataSet.setRowHeight(30);
		JTableHeader header = dataSet.getTableHeader();
		header.setReorderingAllowed(false);
		header.setForeground(new Color(245, 245, 245));
		header.setFont(new Font("Arial", Font.PLAIN, 25));
		header.setBackground(new Color(70, 130, 180));
		header.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		scrollPane = new JScrollPane(dataSet);
		scrollPane.setViewportBorder(new LineBorder(new Color(70, 130, 180)));
		scrollPane.getViewport().setForeground(Color.BLACK);
		scrollPane.getViewport().setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(new Color(245, 245, 245));
		scrollPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_dataSet = new GridBagConstraints();
		gbc_dataSet.gridheight = 2;
		gbc_dataSet.gridwidth = 5;
		gbc_dataSet.gridy = 3;
		gbc_dataSet.insets = new Insets(15, 20, 15, 20);
		gbc_dataSet.fill = GridBagConstraints.BOTH;
		gbc_dataSet.gridx = 0;
		
  		addUsersButton.setFont(new Font("Arial", Font.PLAIN, 30));
  		addUsersButton.setForeground(new Color(105, 105, 105));
  		addUsersButton.setBackground(new Color(105, 105, 105));
  		addUsersButton.setFocusPainted(false);
		addUsersButton.setContentAreaFilled(false);
  		addUsersButton.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_manageUsersButton = new GridBagConstraints();
		gbc_manageUsersButton.gridwidth = 2;
		gbc_manageUsersButton.anchor = GridBagConstraints.EAST;
		gbc_manageUsersButton.insets = new Insets(0, 0, 20, 20);
		gbc_manageUsersButton.gridx = 3;
		gbc_manageUsersButton.gridy = 5;
		
		add(displayName, gbc_displayName);
//		add(logOut, gbc_logOut);
		add(settingsButton, gbc_settingsButton);
		add(userField, gbc_userField);
		add(operationButton, gbc_operationButton);
		add(scrollPane, gbc_dataSet);
		add(addUsersButton, gbc_manageUsersButton);
	}
	
	private void setUpListeners() 
	{
		
		settingsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	base.changeState(ViewStates.settings);	}
		});
		
		operationButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				String userName = userField.getText();
				int length = userName.length();
				if(length > 1)
				{
					if(length > 15)
					{	userName = userName.substring(0, 15);	}
					base.resetPassword(userName);
				}
				userField.setText("");
			}
		});
		
		addUsersButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	base.changeState(ViewStates.manageUsers);	}
		});
	}
}
