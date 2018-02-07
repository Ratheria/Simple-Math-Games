/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.*;
import adapter.SMGController;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SMGSettings extends JPanel
{
	private static final long serialVersionUID = 1212500511996392234L;
	private SMGController base;
	private GridBagLayout layout;
	private JLabel headLabel; 
	private JButton changePassButton;
	private JButton logoutButton;
	private JButton backButton;
	
	public SMGSettings(SMGController base)
	{	
		this.base = base;
		layout = new GridBagLayout();
		headLabel = new JLabel(" SETTINGS ");
		changePassButton = new JButton("Change Password");
		logoutButton = new JButton(" Log Out ");
		backButton = new JButton("BACK");
		
		setUpLayout();
		setUpListeners();
		setUpPanel();
	}
	
	private void setUpPanel()
	{
		if(base.getPerms() == 3)
		{
			changePassButton.setEnabled(false);
			changePassButton.setVisible(false);
		}
	}
	
	private void setUpLayout()
	{
		layout.columnWidths = new int[]{0, 0, 0};
		layout.rowHeights = new int[]{0, 0, 0, 0, 20};
		layout.columnWeights = new double[]{1.0, 0.0, 1.0};
		layout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(135, 206, 235));
		setBackground(new Color(0, 0, 0));
		
		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setFont(new Font("MV Boli", Font.PLAIN, 80));
		headLabel.setForeground(new Color(135, 206, 250));
		GridBagConstraints gbc_headLabel = new GridBagConstraints();
		gbc_headLabel.anchor = GridBagConstraints.NORTH;
		gbc_headLabel.gridwidth = 3;
		gbc_headLabel.insets = new Insets(20, 5, 5, 5);
		gbc_headLabel.gridx = 0;
		gbc_headLabel.gridy = 1;

		changePassButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
		changePassButton.setForeground(new Color(135, 206, 250));
		changePassButton.setBackground(new Color(0, 0, 0));
		changePassButton.setFocusPainted(false);
		changePassButton.setContentAreaFilled(false);
		changePassButton.setBorder(new CompoundBorder(new LineBorder(new Color(30, 144, 255)), new EmptyBorder(5, 10, 0, 10)));
		GridBagConstraints gbc_changePassButton = new GridBagConstraints();
		gbc_changePassButton.anchor = GridBagConstraints.NORTH;
		gbc_changePassButton.insets = new Insets(20, 5, 5, 5);
		gbc_changePassButton.gridwidth = 3;
		gbc_changePassButton.gridx = 0;
		gbc_changePassButton.gridy = 2;
		
		logoutButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
		logoutButton.setForeground(new Color(135, 206, 250));
		logoutButton.setBackground(new Color(0, 0, 0));
		logoutButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_logoutButton = new GridBagConstraints();
		gbc_logoutButton.anchor = GridBagConstraints.NORTH;
		gbc_logoutButton.insets = new Insets(20, 5, 5, 5);
		gbc_logoutButton.gridwidth = 3;
		gbc_logoutButton.gridx = 0;
		gbc_logoutButton.gridy = 3;
		
		backButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
		backButton.setForeground(new Color(135, 206, 250));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new CompoundBorder(new LineBorder(new Color(30, 144, 255)), new EmptyBorder(5, 10, 0, 10)));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
				
		add(headLabel, gbc_headLabel);
		add(changePassButton, gbc_changePassButton);
		add(logoutButton, gbc_logoutButton);
		add(backButton, gbc_backButton);
	}
	
	private void setUpListeners()
	{
		
		changePassButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(5);
				//passwordChange
			}
		});
		
		logoutButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.logout();
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
