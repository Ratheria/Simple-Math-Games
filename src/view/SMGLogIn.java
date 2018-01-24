/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.*;
import adapter.SMGController;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class SMGLogIn extends JPanel
{

	SMGController base;
	private JTextField pTextField;
	private JTextField uTextField;
	private SpringLayout springLayout;
	private JLabel usernameLabel;
	
	public SMGLogIn(SMGController base)
	{	
		this.base = base;
		setBorder(new LineBorder(new Color(0, 0, 255), 10));
		setForeground(new Color(0, 0, 255));
		setBackground(new Color(0, 0, 0));
		springLayout = new SpringLayout();
		setLayout(springLayout);
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Andy", Font.PLAIN, 35));
		usernameLabel.setForeground(new Color(0, 0, 255));
		add(usernameLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 15, SpringLayout.SOUTH, usernameLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblPassword, 50, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblPassword, 200, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, usernameLabel, 0, SpringLayout.WEST, lblPassword);
		springLayout.putConstraint(SpringLayout.EAST, usernameLabel, 0, SpringLayout.EAST, lblPassword);
		lblPassword.setFont(new Font("Andy", Font.PLAIN, 35));
		lblPassword.setForeground(new Color(0, 0, 255));
		add(lblPassword);
		
		JLabel lblLogin = new JLabel("LOGIN");
		springLayout.putConstraint(SpringLayout.NORTH, usernameLabel, 80, SpringLayout.SOUTH, lblLogin);
		springLayout.putConstraint(SpringLayout.NORTH, lblLogin, 80, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblLogin, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblLogin, -10, SpringLayout.EAST, this);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Andy", Font.PLAIN, 65));
		lblLogin.setForeground(new Color(0, 0, 255));
		add(lblLogin);
		
		pTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, pTextField, 25, SpringLayout.EAST, lblPassword);
		pTextField.setHorizontalAlignment(SwingConstants.LEFT);
		springLayout.putConstraint(SpringLayout.NORTH, pTextField, 0, SpringLayout.NORTH, lblPassword);
		springLayout.putConstraint(SpringLayout.SOUTH, pTextField, -3, SpringLayout.SOUTH, lblPassword);
		pTextField.setForeground(new Color(0, 0, 255));
		springLayout.putConstraint(SpringLayout.EAST, pTextField, -50, SpringLayout.EAST, this);
		pTextField.setFont(new Font("Andy", Font.PLAIN, 18));
		pTextField.setBackground(new Color(0, 0, 0));
		pTextField.setColumns(5);
		pTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE),
                BorderFactory.createEmptyBorder(4, 25, 0, 0)));
		add(pTextField);
		
		uTextField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, uTextField, 0, SpringLayout.NORTH, usernameLabel);
		springLayout.putConstraint(SpringLayout.WEST, uTextField, 0, SpringLayout.WEST, pTextField);
		springLayout.putConstraint(SpringLayout.SOUTH, uTextField, -3, SpringLayout.SOUTH, usernameLabel);
		springLayout.putConstraint(SpringLayout.EAST, uTextField, -50, SpringLayout.EAST, this);
		uTextField.setHorizontalAlignment(SwingConstants.LEFT);
		uTextField.setForeground(new Color(0, 0, 255));
		uTextField.setFont(new Font("Andy", Font.PLAIN, 18));
		uTextField.setColumns(5);
		uTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE),				BorderFactory.createEmptyBorder(4, 25, 0, 0)));
		uTextField.setBackground(new Color(0, 0, 0));
		add(uTextField);
		
		JButton btnEnter = new JButton("ENTER");
		springLayout.putConstraint(SpringLayout.NORTH, btnEnter, 100, SpringLayout.SOUTH, pTextField);
		btnEnter.setFont(new Font("Andy", Font.PLAIN, 30));
		btnEnter.setForeground(new Color(0, 0, 255));
		btnEnter.setBackground(new Color(0, 0, 0));
		springLayout.putConstraint(SpringLayout.EAST, btnEnter, 0, SpringLayout.EAST, pTextField);
		btnEnter.setFocusPainted(false);
		btnEnter.setContentAreaFilled(false);
		btnEnter.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE),
				BorderFactory.createEmptyBorder(5, 10, 0, 10)));
		add(btnEnter);
		
	}
	
	private void loginSetUp()
	{
		
	}
}
