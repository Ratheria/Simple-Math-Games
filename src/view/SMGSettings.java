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

public class SMGSettings extends JPanel
{
	private SMGController base;
	private SpringLayout springLayout;
	private JLabel headLabel; 
	private JButton changePassButton;
	private JButton backButton;
	
	public SMGSettings(SMGController base)
	{	
		this.base = base;
		springLayout = new SpringLayout();
		headLabel = new JLabel("SETTINGS");
		changePassButton = new JButton("Change Password");
		backButton = new JButton("BACK");
		
		setLayout(springLayout);
		add(headLabel);
		add(changePassButton);
		add(backButton);
		
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpLayout()
	{
		setBorder(new LineBorder(new Color(255, 0, 0), 10));
		setForeground(new Color(0, 0, 255));
		setBackground(new Color(0, 0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, headLabel, 80, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, headLabel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, headLabel, -10, SpringLayout.EAST, this);
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setFont(new Font("MV Boli", Font.PLAIN, 65));
		headLabel.setForeground(new Color(255, 0, 0));
		changePassButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
		springLayout.putConstraint(SpringLayout.SOUTH, changePassButton, -20, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, changePassButton, -20, SpringLayout.EAST, this);
		changePassButton.setForeground(new Color(255, 0, 0));
		changePassButton.setBackground(new Color(0, 0, 0));
		changePassButton.setFocusPainted(false);
		changePassButton.setContentAreaFilled(false);
		changePassButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED),
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
		
		changePassButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(5);
			}
		});
		
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.returnToMenu();
			}
		});
	}
	
}
