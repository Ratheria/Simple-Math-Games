/**
 *	@author Ariana Fairbanks
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import adapter.Controller;

public class ManageUsers extends JPanel
{
	private static final long serialVersionUID = 7833894194480819845L;
	private JFileChooser fileChoose;
	private Controller base;
	private GridBagLayout layout;
	private JLabel header;
	private JButton backButton;
	private JLabel importUsers;
	private JButton importUsersButton;
	private JLabel addUser;
	private JLabel addFirstName;
	private JTextField addFirstNameTextField;
	private JLabel addLastName;
	private JTextField addLastNameTextField;
	private JLabel addID;
	private JTextField addIDTextField;
	private JButton addUserButton;
	
	public ManageUsers(Controller base)
	{
		this.base = base;
		fileChoose = new JFileChooser();
		layout = new GridBagLayout();
		header = new JLabel(" Manage Users ");
		backButton = new JButton(" BACK ");
		importUsers = new JLabel("Import users from CSV file: ");
		importUsersButton = new JButton(" IMPORT CSV ");
		addUser = new JLabel("Add a user: ");
		addFirstName = new JLabel("First name: ");
		addFirstNameTextField = new JTextField();
		addLastName = new JLabel("Last name: ");
		addLastNameTextField = new JTextField();
		addID = new JLabel("Student ID number: ");
		addIDTextField = new JTextField();
		addUserButton = new JButton(" ADD USER ");

		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.4, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		layout.columnWidths = new int[]{0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(128, 128, 128), 10));
		setForeground(new Color(105, 105, 105));
		setBackground(new Color(245, 245, 245));
		
		header.setVerticalAlignment(SwingConstants.TOP);
		header.setForeground(new Color(105, 105, 105));
		header.setFont(new Font("Arial", Font.PLAIN, 35));
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 4;
		gbc_displayName.insets = new Insets(15, 10, 5, 0);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;
		
		backButton.setFont(new Font("Arial", Font.PLAIN, 25));
		backButton.setForeground(new Color(105, 105, 105));
		backButton.setBackground(new Color(105, 105, 105));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(105, 105, 105), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_settingsButton.insets = new Insets(20, 20, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;		
		
		importUsers.setVerticalAlignment(SwingConstants.TOP);
		importUsers.setForeground(new Color(105, 105, 105));
		importUsers.setFont(new Font("Arial", Font.PLAIN, 35));
		GridBagConstraints gbc_importUsers = new GridBagConstraints();
		gbc_importUsers.anchor = GridBagConstraints.NORTHWEST;
		gbc_importUsers.gridwidth = 4;
		gbc_importUsers.insets = new Insets(15, 10, 5, 0);
		gbc_importUsers.gridx = 1;
		gbc_importUsers.gridy = 2;

		importUsersButton.setVerticalAlignment(SwingConstants.TOP);
		importUsersButton.setForeground(new Color(105, 105, 105));
		importUsersButton.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_importUsersButton = new GridBagConstraints();
		gbc_importUsersButton.insets = new Insets(0, 0, 5, 5);
		gbc_importUsersButton.gridx = 3;
		gbc_importUsersButton.gridy = 2;
		
		addUser.setVerticalAlignment(SwingConstants.TOP);
		addUser.setForeground(new Color(105, 105, 105));
		addUser.setFont(new Font("Arial", Font.PLAIN, 35));
		GridBagConstraints gbc_addUser = new GridBagConstraints();
		gbc_addUser.anchor = GridBagConstraints.NORTHWEST;
		gbc_addUser.gridwidth = 4;
		gbc_addUser.insets = new Insets(15, 10, 5, 0);
		gbc_addUser.gridx = 1;
		gbc_addUser.gridy = 4;
		
		addFirstName.setVerticalAlignment(SwingConstants.TOP);
		addFirstName.setForeground(new Color(105, 105, 105));
		addFirstName.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_addFirstName = new GridBagConstraints();
		gbc_addFirstName.anchor = GridBagConstraints.NORTHWEST;
		gbc_addFirstName.gridwidth = 4;
		gbc_addFirstName.insets = new Insets(15, 10, 5, 0);
		gbc_addFirstName.gridx = 1;
		gbc_addFirstName.gridy = 5;
		
		addFirstNameTextField.setFont(new Font("Arial", Font.PLAIN, 15));
		addFirstNameTextField.setBackground(new Color(220, 220, 220));
		addFirstNameTextField.setForeground(new Color(0, 0, 0));
		addFirstNameTextField.setBorder(new CompoundBorder(new LineBorder(new Color(105, 105, 105)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_addFirstNameTextField = new GridBagConstraints();
		gbc_addFirstNameTextField.gridwidth = 2;
		gbc_addFirstNameTextField.insets = new Insets(0, 40, 5, 5);
		gbc_addFirstNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addFirstNameTextField.gridx = 2;
		gbc_addFirstNameTextField.gridy = 6;
		
		addLastName.setVerticalAlignment(SwingConstants.TOP);
		addLastName.setForeground(new Color(105, 105, 105));
		addLastName.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_addLastName = new GridBagConstraints();
		gbc_addLastName.anchor = GridBagConstraints.NORTHWEST;
		gbc_addLastName.gridwidth = 4;
		gbc_addLastName.insets = new Insets(15, 10, 5, 0);
		gbc_addLastName.gridx = 1;
		gbc_addLastName.gridy = 7;
		
		addLastNameTextField.setFont(new Font("Arial", Font.PLAIN, 15));
		addLastNameTextField.setBackground(new Color(220, 220, 220));
		addLastNameTextField.setForeground(new Color(0, 0, 0));
		addLastNameTextField.setBorder(new CompoundBorder(new LineBorder(new Color(105, 105, 105)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_addLastNameTextField = new GridBagConstraints();
		gbc_addLastNameTextField.gridwidth = 2;
		gbc_addLastNameTextField.insets = new Insets(0, 40, 5, 5);
		gbc_addLastNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addLastNameTextField.gridx = 2;
		gbc_addLastNameTextField.gridy = 8;
		
		addID.setVerticalAlignment(SwingConstants.TOP);
		addID.setForeground(new Color(105, 105, 105));
		addID.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_addID = new GridBagConstraints();
		gbc_addID.anchor = GridBagConstraints.NORTHWEST;
		gbc_addID.gridwidth = 4;
		gbc_addID.insets = new Insets(15, 10, 5, 0);
		gbc_addID.gridx = 1;
		gbc_addID.gridy = 9;
		
		addIDTextField.setFont(new Font("Arial", Font.PLAIN, 15));
		addIDTextField.setBackground(new Color(220, 220, 220));
		addIDTextField.setForeground(new Color(0, 0, 0));
		addIDTextField.setBorder(new CompoundBorder(new LineBorder(new Color(105, 105, 105)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_addIDTextField = new GridBagConstraints();
		gbc_addIDTextField.gridwidth = 2;
		gbc_addIDTextField.insets = new Insets(0, 40, 5, 5);
		gbc_addIDTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addIDTextField.gridx = 2;
		gbc_addIDTextField.gridy = 10;
		
		addUserButton.setVerticalAlignment(SwingConstants.TOP);
		addUserButton.setForeground(new Color(105, 105, 105));
		addUserButton.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_addUserButton = new GridBagConstraints();
		gbc_addUserButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_addUserButton.gridwidth = 4;
		gbc_addUserButton.insets = new Insets(15, 10, 5, 0);
		gbc_addUserButton.gridx = 3;
		gbc_addUserButton.gridy = 11;
		
		add(header, gbc_displayName);
		add(backButton, gbc_settingsButton);
		add(importUsers, gbc_importUsers);
		add(importUsersButton, gbc_importUsersButton);
		add(addUser, gbc_addUser);
		add(addFirstName, gbc_addFirstName);
		add(addFirstNameTextField, gbc_addFirstNameTextField);
		add(addLastName, gbc_addLastName);
		add(addLastNameTextField, gbc_addLastNameTextField);
		add(addID, gbc_addID);
		add(addIDTextField, gbc_addIDTextField);
		add(addUserButton, gbc_addUserButton);
		
	}
	
	private void setUpListeners() 
	{
		
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.returnToMenu();
			}
		});
		
		importUsersButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				JPanel temp = new JPanel();
			    fileChoose.setFileFilter(new FileFilter() 
			    {
			        @Override
			        public boolean accept(File f) 
			        {	return f.getName().endsWith(".csv");	}
			        @Override
			        public String getDescription() 
			        {	return "CSV files";	}
			    });
				int valueReturned = fileChoose.showOpenDialog(temp);
				if(valueReturned == JFileChooser.APPROVE_OPTION)
				{
					base.importUsers(fileChoose.getSelectedFile());
				}
			}
		});
		
		addUserButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				addStudent();
			}
		});
	
	}
	
	private void addStudent(){
		String firstName = addFirstNameTextField.getText();
		String lastName = addLastNameTextField.getText();
		String idString = addIDTextField.getText();
		int firstLength = firstName.length();
		int lastLength = lastName.length();
		int idLength = idString.length();
		if(firstLength > 0 && lastLength > 0 && idLength > 0)
		{
		base.addStudent(firstName, lastName, idString);	
		}
		else
		{
			JPanel errorPanel = new JPanel();
			JOptionPane.showMessageDialog(errorPanel, "Please enter first name, last name, and password.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		addFirstNameTextField.setText("");
		addLastNameTextField.setText("");
		addIDTextField.setText("");
		addFirstNameTextField.requestFocus();
	}
}
