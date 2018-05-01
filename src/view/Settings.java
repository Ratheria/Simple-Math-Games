/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.*;
import adapter.Controller;
import adapter.ViewStates;
import gameview.InstructionPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Settings extends JPanel
{
	private static final long serialVersionUID = 1212500511996392234L;
	private Controller base;
	private GridBagLayout layout;
	private JButton changePassButton;
	private JButton logoutButton;
	private JButton backButton;
	private JButton fishInstructions;
	private JButton jellyInstructions;
	private JButton sharkInstructions;

	public Settings(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		backButton = new JButton(" BACK ");
		fishInstructions = new JButton(" Fish Game Instructions ");
		jellyInstructions = new JButton(" Jellyfish Game Instructions ");
		sharkInstructions = new JButton(" Shark Game Instructions ");
		changePassButton = new JButton(" Change Password ");
		logoutButton = new JButton(" Log Out ");
		setUpLayout();
		setUpListeners();
		setUpPanel();
	}

	private void setUpPanel()
	{
		if (base.getPerms() == 3)
		{
			changePassButton.setEnabled(false);
			changePassButton.setVisible(false);
		}
	}

	private void setUpLayout()
	{
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(135, 206, 235));
		setBackground(new Color(245, 245, 245));
		layout.columnWidths = new int[]{ 0, 0 };
		layout.rowHeights = new int[]{ 0, 0, 0, 0, 0, 0, 0, 20 };
		layout.columnWeights = new double[]{ 0.0, 1.0 };
		layout.rowWeights = new double[]{ 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0 };

		backButton.setFont(new Font("Arial", Font.PLAIN, 25));
		backButton.setForeground(new Color(70, 130, 180));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
		
		fishInstructions.setFont(new Font("Arial", Font.PLAIN, 28));
		fishInstructions.setForeground(new Color(70, 130, 180));
		fishInstructions.setBackground(new Color(0, 0, 0));
		fishInstructions.setFocusPainted(false);
		fishInstructions.setContentAreaFilled(false);
		fishInstructions.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_fishInstructions = new GridBagConstraints();
		gbc_fishInstructions.gridwidth = 2;
		gbc_fishInstructions.insets = new Insets(20, 10, 10, 0);
		gbc_fishInstructions.anchor = GridBagConstraints.NORTH;
		gbc_fishInstructions.gridx = 0;
		gbc_fishInstructions.gridy = 2;
		
		jellyInstructions.setFont(new Font("Arial", Font.PLAIN, 28));
		jellyInstructions.setForeground(new Color(70, 130, 180));
		jellyInstructions.setBackground(new Color(0, 0, 0));
		jellyInstructions.setFocusPainted(false);
		jellyInstructions.setContentAreaFilled(false);
		jellyInstructions.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_jellyInstructions = new GridBagConstraints();
		gbc_jellyInstructions.gridwidth = 2;
		gbc_jellyInstructions.insets = new Insets(20, 0, 10, 0);
		gbc_jellyInstructions.anchor = GridBagConstraints.NORTH;
		gbc_jellyInstructions.gridx = 0;
		gbc_jellyInstructions.gridy = 3;
		
		sharkInstructions.setFont(new Font("Arial", Font.PLAIN, 28));
		sharkInstructions.setForeground(new Color(70, 130, 180));
		sharkInstructions.setBackground(new Color(0, 0, 0));
		sharkInstructions.setFocusPainted(false);
		sharkInstructions.setContentAreaFilled(false);
		sharkInstructions.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_sharkInstructions = new GridBagConstraints();
		gbc_sharkInstructions.gridwidth = 2;
		gbc_sharkInstructions.insets = new Insets(20, 5, 10, 10);
		gbc_sharkInstructions.anchor = GridBagConstraints.NORTH;
		gbc_sharkInstructions.gridx = 0;
		gbc_sharkInstructions.gridy = 4;
		
		changePassButton.setFont(new Font("Arial", Font.PLAIN, 30));
		changePassButton.setForeground(new Color(70, 130, 180));
		changePassButton.setContentAreaFilled(false);
		changePassButton.setFocusPainted(false);
		changePassButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_changePassButton = new GridBagConstraints();
		gbc_changePassButton.anchor = GridBagConstraints.SOUTH;
		gbc_changePassButton.insets = new Insets(15, 5, 5, 0);
		gbc_changePassButton.gridwidth = 2;
		gbc_changePassButton.gridx = 0;
		gbc_changePassButton.gridy = 5;

		logoutButton.setFont(new Font("Arial", Font.PLAIN, 30));
		logoutButton.setForeground(new Color(70, 130, 180));
		logoutButton.setBackground(new Color(240, 240, 245));
		logoutButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_logoutButton = new GridBagConstraints();
		gbc_logoutButton.anchor = GridBagConstraints.NORTH;
		gbc_logoutButton.insets = new Insets(15, 5, 5, 0);
		gbc_logoutButton.gridwidth = 2;
		gbc_logoutButton.gridx = 0;
		gbc_logoutButton.gridy = 6;
		
		add(fishInstructions, gbc_fishInstructions);
		add(jellyInstructions, gbc_jellyInstructions);
		add(sharkInstructions, gbc_sharkInstructions);
		add(backButton, gbc_backButton);
		add(changePassButton, gbc_changePassButton);
		add(logoutButton, gbc_logoutButton);
	}
	
	private void setUpListeners()
	{
		changePassButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{	
				base.changeState(ViewStates.PASSWORDCHANGE);	
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
		
		fishInstructions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				new InstructionPanel(base, 1, base.getInstructionPreference("game1Instructions"), null);
			}
		});
		
		jellyInstructions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				new InstructionPanel(base, 2, base.getInstructionPreference("game2Instructions"), null);
			}
		});
		
		sharkInstructions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				new InstructionPanel(base, 3, base.getInstructionPreference("game3Instructions"), null);
			}
		});
	}

}
