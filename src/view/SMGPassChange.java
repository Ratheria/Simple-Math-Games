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

public class SMGPassChange extends JPanel
{
	private SMGController base;
	private SpringLayout springLayout;
	private JLabel oldPassLabel;
	private JLabel newPassLabel;
	private JLabel headLabel; 
	private JTextField nPTextField;
	private JTextField oPTextField;
	private JButton enterButton;
	private JButton backButton;
	
	public SMGPassChange(SMGController base)
	{	
		this.base = base;
		springLayout = new SpringLayout();
		oldPassLabel = new JLabel("Old Pass:");
		newPassLabel = new JLabel("New Pass:");
		headLabel = new JLabel("CHANGE PASSWORD");
		nPTextField = new JTextField();
		oPTextField = new JTextField();
		enterButton = new JButton("ENTER");
		backButton = new JButton("BACK");
		
		
		setLayout(springLayout);
		add(oldPassLabel);
		add(newPassLabel);
		add(headLabel);
		add(nPTextField);
		add(oPTextField);
		add(enterButton);
		add(backButton);
		
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpLayout()
	{
		setBorder(new LineBorder(new Color(255, 0, 0), 10));
		setForeground(new Color(0, 0, 255));
		setBackground(new Color(0, 0, 0));
		oldPassLabel.setFont(new Font("MV Boli", Font.PLAIN, 35));
		oldPassLabel.setForeground(new Color(255, 0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, newPassLabel, 15, SpringLayout.SOUTH, oldPassLabel);
		springLayout.putConstraint(SpringLayout.WEST, newPassLabel, 50, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, newPassLabel, 225, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, oldPassLabel, 0, SpringLayout.WEST, newPassLabel);
		springLayout.putConstraint(SpringLayout.EAST, oldPassLabel, 0, SpringLayout.EAST, newPassLabel);
		newPassLabel.setFont(new Font("MV Boli", Font.PLAIN, 35));
		newPassLabel.setForeground(new Color(255, 0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, oldPassLabel, 80, SpringLayout.SOUTH, headLabel);
		springLayout.putConstraint(SpringLayout.NORTH, headLabel, 80, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, headLabel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, headLabel, -10, SpringLayout.EAST, this);
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setFont(new Font("MV Boli", Font.PLAIN, 65));
		headLabel.setForeground(new Color(255, 0, 0));
		springLayout.putConstraint(SpringLayout.SOUTH, nPTextField, -3, SpringLayout.SOUTH, newPassLabel);
		springLayout.putConstraint(SpringLayout.WEST, nPTextField, 25, SpringLayout.EAST, newPassLabel);
		nPTextField.setHorizontalAlignment(SwingConstants.LEFT);
		springLayout.putConstraint(SpringLayout.NORTH, nPTextField, 0, SpringLayout.NORTH, newPassLabel);
		nPTextField.setForeground(new Color(255, 0, 0));
		springLayout.putConstraint(SpringLayout.EAST, nPTextField, -50, SpringLayout.EAST, this);
		nPTextField.setFont(new Font("MV Boli", Font.PLAIN, 20));
		nPTextField.setBackground(new Color(0, 0, 0));
		nPTextField.setColumns(5);
		nPTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED),
                BorderFactory.createEmptyBorder(4, 25, 0, 0)));
		springLayout.putConstraint(SpringLayout.SOUTH, oPTextField, -3, SpringLayout.SOUTH, oldPassLabel);
		springLayout.putConstraint(SpringLayout.NORTH, oPTextField, 0, SpringLayout.NORTH, oldPassLabel);
		springLayout.putConstraint(SpringLayout.WEST, oPTextField, 0, SpringLayout.WEST, nPTextField);
		springLayout.putConstraint(SpringLayout.EAST, oPTextField, -50, SpringLayout.EAST, this);
		oPTextField.setHorizontalAlignment(SwingConstants.LEFT);
		oPTextField.setForeground(new Color(255, 0, 0));
		oPTextField.setFont(new Font("MV Boli", Font.PLAIN, 20));
		oPTextField.setColumns(5);
		oPTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED),
				BorderFactory.createEmptyBorder(4, 25, 0, 0)));
		oPTextField.setBackground(new Color(0, 0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, enterButton, 100, SpringLayout.SOUTH, nPTextField);
		enterButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
		enterButton.setForeground(new Color(255, 0, 0));
		enterButton.setBackground(new Color(0, 0, 0));
		springLayout.putConstraint(SpringLayout.EAST, enterButton, 0, SpringLayout.EAST, nPTextField);
		enterButton.setFocusPainted(false);
		enterButton.setContentAreaFilled(false);
		enterButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED),
				BorderFactory.createEmptyBorder(5, 10, 0, 10)));
		backButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
		backButton.setForeground(new Color(255, 0, 0));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		springLayout.putConstraint(SpringLayout.NORTH, backButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, backButton, 0, SpringLayout.WEST, headLabel);
		backButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED),
				BorderFactory.createEmptyBorder(5, 10, 0, 10)));
	}
	
	private void setUpListeners()
	{
		nPTextField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				changePass();
			}
		});
		
		enterButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				changePass();
			}
		});
		
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(4);
			}
		});
	}
	
	private void changePass()
	{
		String newPass = nPTextField.getText();
		String oldPass = oPTextField.getText();
		int oLength = oldPass.length();
		int nLength = newPass.length();
		if(oLength > 0 && nLength > 0)
		{
			if(oLength > 15)
			{
				oldPass = oldPass.substring(0, 15);
			}
			if(nLength > 15)
			{
				newPass = newPass.substring(0, 15);
			}
			base.changePassword(oldPass, newPass);
		}
		else
		{
			JPanel errorPanel = new JPanel();
			JOptionPane.showMessageDialog(errorPanel, "Please enter the old password and the new password desired.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		oPTextField.setText("");
		nPTextField.setText("");
	}
}
