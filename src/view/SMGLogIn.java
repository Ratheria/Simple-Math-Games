/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.*;
import adapter.SMGController;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SMGLogIn extends JPanel
{
	private SMGController base;
	private SpringLayout springLayout;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel loginLabel; 
	private JTextField pTextField;
	private JTextField uTextField;
	private JButton enterButton;
	
	public SMGLogIn(SMGController base)
	{	
		this.base = base;
		springLayout = new SpringLayout();
		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");
		loginLabel = new JLabel("LOGIN");
		pTextField = new JTextField();
		uTextField = new JTextField();
		enterButton = new JButton("ENTER");
		
		setLayout(springLayout);
		add(usernameLabel);
		add(passwordLabel);
		add(loginLabel);
		add(pTextField);
		add(uTextField);
		add(enterButton);
		
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpLayout()
	{
		setBorder(new LineBorder(new Color(0, 0, 255), 10));
		setForeground(new Color(0, 0, 255));
		setBackground(new Color(0, 0, 0));
		usernameLabel.setFont(new Font("MV Boli", Font.PLAIN, 35));
		usernameLabel.setForeground(new Color(0, 0, 255));
		springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 15, SpringLayout.SOUTH, usernameLabel);
		springLayout.putConstraint(SpringLayout.WEST, passwordLabel, 50, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, passwordLabel, 225, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, usernameLabel, 0, SpringLayout.WEST, passwordLabel);
		springLayout.putConstraint(SpringLayout.EAST, usernameLabel, 0, SpringLayout.EAST, passwordLabel);
		passwordLabel.setFont(new Font("MV Boli", Font.PLAIN, 35));
		passwordLabel.setForeground(new Color(0, 0, 255));
		springLayout.putConstraint(SpringLayout.NORTH, usernameLabel, 80, SpringLayout.SOUTH, loginLabel);
		springLayout.putConstraint(SpringLayout.NORTH, loginLabel, 80, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, loginLabel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, loginLabel, -10, SpringLayout.EAST, this);
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setFont(new Font("MV Boli", Font.PLAIN, 65));
		loginLabel.setForeground(new Color(0, 0, 255));
		springLayout.putConstraint(SpringLayout.SOUTH, pTextField, -3, SpringLayout.SOUTH, passwordLabel);
		springLayout.putConstraint(SpringLayout.WEST, pTextField, 25, SpringLayout.EAST, passwordLabel);
		pTextField.setHorizontalAlignment(SwingConstants.LEFT);
		springLayout.putConstraint(SpringLayout.NORTH, pTextField, 0, SpringLayout.NORTH, passwordLabel);
		pTextField.setForeground(new Color(0, 0, 255));
		springLayout.putConstraint(SpringLayout.EAST, pTextField, -50, SpringLayout.EAST, this);
		pTextField.setFont(new Font("MV Boli", Font.PLAIN, 20));
		pTextField.setBackground(new Color(0, 0, 0));
		pTextField.setColumns(5);
		pTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE),
                BorderFactory.createEmptyBorder(4, 25, 0, 0)));
		springLayout.putConstraint(SpringLayout.SOUTH, uTextField, -3, SpringLayout.SOUTH, usernameLabel);
		springLayout.putConstraint(SpringLayout.NORTH, uTextField, 0, SpringLayout.NORTH, usernameLabel);
		springLayout.putConstraint(SpringLayout.WEST, uTextField, 0, SpringLayout.WEST, pTextField);
		springLayout.putConstraint(SpringLayout.EAST, uTextField, -50, SpringLayout.EAST, this);
		uTextField.setHorizontalAlignment(SwingConstants.LEFT);
		uTextField.setForeground(new Color(0, 0, 255));
		uTextField.setFont(new Font("MV Boli", Font.PLAIN, 20));
		uTextField.setColumns(5);
		uTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE),
				BorderFactory.createEmptyBorder(4, 25, 0, 0)));
		uTextField.setBackground(new Color(0, 0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, enterButton, 100, SpringLayout.SOUTH, pTextField);
		enterButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
		enterButton.setForeground(new Color(0, 0, 255));
		enterButton.setBackground(new Color(0, 0, 0));
		springLayout.putConstraint(SpringLayout.EAST, enterButton, 0, SpringLayout.EAST, pTextField);
		enterButton.setFocusPainted(false);
		enterButton.setContentAreaFilled(false);
		enterButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE),
				BorderFactory.createEmptyBorder(5, 10, 0, 10)));
	}
	
	private void setUpListeners()
	{
		enterButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				String userName = uTextField.getText();
				String pass = pTextField.getText();
				int uLength = userName.length();
				int pLength = pass.length();
				if(uLength > 0 && pLength > 0)
				{
					if(uLength > 15)
					{
						userName = userName.substring(0, 15);
					}
					if(pLength > 15)
					{
						pass = pass.substring(0, 15);
					}
					base.checkLogin(userName, pass);
				}
				else
				{
					JPanel errorPanel = new JPanel();
					JOptionPane.showMessageDialog(errorPanel, "Please enter a username and password.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				uTextField.setText("");
				pTextField.setText("");
			}
		});
	}
}
