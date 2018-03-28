/**
 *	@author Ariana Fairbanks
 */
package view;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import adapter.Controller;
import adapter.ViewStates;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentMenu extends JPanel 
{
	private static final long serialVersionUID = -9125821110680618384L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel displayName;
	private JButton settingsButton;
	private JButton btnGame;
	private JButton btnGame3;
	private JButton btnGame2;
	
	public StudentMenu(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		displayName = new JLabel(" ");
		settingsButton = new JButton("    ");
		btnGame = new JButton(" Game1 ");
		btnGame3 = new JButton(" Game3");
		btnGame = new JButton(" Game 1 ");
		btnGame2 = new JButton(" Game 2 ");

		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		layout.columnWidths = new int[]{20, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(245, 245, 245));
		
		displayName.setVerticalAlignment(SwingConstants.BOTTOM);
		displayName.setForeground(new Color(70, 130, 180));
		displayName.setFont(new Font("Arial", Font.PLAIN, 35));
		displayName.setText(base.getFullName());
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 3;
		gbc_displayName.insets = new Insets(15, 10, 5, 0);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;
		
/*
		logOut.setFont(new Font("Arial", Font.PLAIN, 30));
		logOut.setForeground(new Color(135, 206, 250));
		logOut.setBackground(new Color(0, 0, 0));
		logOut.setFocusPainted(false);
		logOut.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_logOut = new GridBagConstraints();
		gbc_logOut.anchor = GridBagConstraints.EAST;
		gbc_logOut.insets = new Insets(0, 0, 20, 20);
		gbc_logOut.gridx = 3;
		gbc_logOut.gridy = 3;
*/
		
		settingsButton.setFont(new Font("Arial", Font.PLAIN, 25));
		settingsButton.setForeground(new Color(135, 206, 250));
		settingsButton.setBackground(new Color(240, 240, 245));
		settingsButton.setFocusPainted(false);
		settingsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_settingsButton.insets = new Insets(20, 25, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;
		
		btnGame.setFont(new Font("Arial", Font.PLAIN, 25));
		btnGame.setForeground(new Color(70, 130, 180));
		btnGame.setBackground(new Color(70, 130, 180));
		btnGame.setFocusPainted(false);
		btnGame.setContentAreaFilled(false);
		btnGame.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_btnGame = new GridBagConstraints();
		gbc_btnGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnGame.gridx = 1;
		gbc_btnGame.gridy = 2;
		
		btnGame3.setFont(new Font("Arial", Font.PLAIN, 25));
		btnGame3.setForeground(new Color(70, 130, 180));
		btnGame3.setBackground(new Color(70, 130, 180));
		btnGame3.setFocusPainted(false);
		btnGame3.setContentAreaFilled(false);
		btnGame3.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_btnGame3 = new GridBagConstraints();
		gbc_btnGame3.insets = new Insets(0, 0, 5, 5);
		gbc_btnGame3.gridx = 2;
		gbc_btnGame3.gridy = 3;
		
		btnGame2.setFont(new Font("Arial", Font.PLAIN, 25));
		btnGame2.setForeground(new Color(70, 130, 180));
		btnGame2.setBackground(new Color(70, 130, 180));
		btnGame2.setFocusPainted(false);
		btnGame2.setContentAreaFilled(false);
		btnGame2.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_btnGame2 = new GridBagConstraints();
		gbc_btnGame2.insets = new Insets(0, 0, 5, 5);
		gbc_btnGame2.gridx = 2;
		gbc_btnGame2.gridy = 2;
		
		add(displayName, gbc_displayName);
		add(settingsButton, gbc_settingsButton);
		add(btnGame, gbc_btnGame);
		add(btnGame3, gbc_btnGame3);
		add(btnGame2, gbc_btnGame2);
//		add(logOut, gbc_logOut);

	}
	
	private void setUpListeners() 
	{	
		settingsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(ViewStates.settings);
			}
		});
		
		btnGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(ViewStates.game1);
			}
		});
		
		btnGame2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(ViewStates.game2);
			}
		});
		
		btnGame3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(ViewStates.game3);
			}
		});
	}
}

