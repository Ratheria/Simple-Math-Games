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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class StudentMenu extends JPanel
{
	private static final long serialVersionUID = -9125821110680618384L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel displayName;
	private JButton settingsButton;
	private JComboBox<String> comboBox;
	private JButton btnGame;
	private JButton btnGame3;
	private JButton btnGame2;
	private JButton viewStats;
	private JButton gameRecords;
	private JButton sessionRecords;

	public StudentMenu(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		displayName = new JLabel(" ");
		settingsButton = new JButton("    ");
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnGame = new JButton(" Play Fish Game ");
		btnGame2 = new JButton(" Play Jellyfish Game ");
		btnGame3 = new JButton(" Play Shark Game ");
		viewStats = new JButton(" Stats ");
		gameRecords = new JButton(" Game Records ");
		sessionRecords = new JButton(" Session Records ");

		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout()
	{
		layout.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		layout.rowWeights = new double[]
		{ 0.0, 5.0, 0.0, 1.0, 0.0, 0.0, 0.0, 5.0, 0.0, 1.0 };
		layout.columnWidths = new int[]
		{ 20, 0, 0, 0, 0 };
		layout.columnWeights = new double[]
		{ 0.0, 0.0, 5.0, 1.0, 0.0 };
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
		gbc_displayName.gridwidth = 4;
		gbc_displayName.insets = new Insets(15, 10, 5, 0);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;

		/*
		 * logOut.setFont(new Font("Arial", Font.PLAIN, 30));
		 * logOut.setForeground(new Color(135, 206, 250));
		 * logOut.setBackground(new Color(0, 0, 0));
		 * logOut.setFocusPainted(false); logOut.setBorder(new LineBorder(new
		 * Color(70, 130, 180), 2, true)); GridBagConstraints gbc_logOut = new
		 * GridBagConstraints(); gbc_logOut.anchor = GridBagConstraints.EAST;
		 * gbc_logOut.insets = new Insets(0, 0, 20, 20); gbc_logOut.gridx = 3;
		 * gbc_logOut.gridy = 3;
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

		comboBox.setEditable(false);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {" Addition and Subtraction ", " Addition ", " Subtraction "}));
		comboBox.setSelectedIndex(base.questionTypes);
		comboBox.setForeground(new Color(70, 130, 180));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 5;
		gbc_comboBox.insets = new Insets(0, 0, 20, 0);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		
		btnGame.setFont(new Font("Arial", Font.PLAIN, 27));
		btnGame.setForeground(new Color(70, 130, 180));
		btnGame.setBackground(new Color(70, 130, 180));
		btnGame.setFocusPainted(false);
		btnGame.setContentAreaFilled(false);
		btnGame.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_btnGame = new GridBagConstraints();
		gbc_btnGame.gridwidth = 5;
		gbc_btnGame.insets = new Insets(5, 0, 20, 0);
		gbc_btnGame.gridx = 0;
		gbc_btnGame.gridy = 4;

		btnGame2.setFont(new Font("Arial", Font.PLAIN, 27));
		btnGame2.setForeground(new Color(70, 130, 180));
		btnGame2.setBackground(new Color(70, 130, 180));
		btnGame2.setFocusPainted(false);
		btnGame2.setContentAreaFilled(false);
		btnGame2.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_btnGame2 = new GridBagConstraints();
		gbc_btnGame2.gridwidth = 5;
		gbc_btnGame2.insets = new Insets(5, 0, 20, 0);
		gbc_btnGame2.gridx = 0;
		gbc_btnGame2.gridy = 5;

		btnGame3.setFont(new Font("Arial", Font.PLAIN, 27));
		btnGame3.setForeground(new Color(70, 130, 180));
		btnGame3.setBackground(new Color(70, 130, 180));
		btnGame3.setFocusPainted(false);
		btnGame3.setContentAreaFilled(false);
		btnGame3.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_btnGame3 = new GridBagConstraints();
		gbc_btnGame3.gridwidth = 5;
		gbc_btnGame3.insets = new Insets(5, 0, 15, 0);
		gbc_btnGame3.gridx = 0;
		gbc_btnGame3.gridy = 6;

		viewStats.setFont(new Font("Arial", Font.PLAIN, 25));
		viewStats.setForeground(new Color(70, 130, 180));
		viewStats.setBackground(new Color(70, 130, 180));
		viewStats.setFocusPainted(false);
		viewStats.setContentAreaFilled(false);
		viewStats.setHorizontalTextPosition(SwingConstants.CENTER);
		viewStats.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_viewStats = new GridBagConstraints();
		gbc_viewStats.gridwidth = 3;
		gbc_viewStats.fill = GridBagConstraints.HORIZONTAL;
		gbc_viewStats.insets = new Insets(10, 60, 20, 60);
		gbc_viewStats.gridx = 0;
		gbc_viewStats.gridy = 8;

		gameRecords.setFont(new Font("Arial", Font.PLAIN, 25));
		gameRecords.setForeground(new Color(70, 130, 180));
		gameRecords.setBackground(new Color(70, 130, 180));
		gameRecords.setFocusPainted(false);
		gameRecords.setContentAreaFilled(false);
		gameRecords.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_gameRecords = new GridBagConstraints();
		gbc_gameRecords.fill = GridBagConstraints.HORIZONTAL;
		gbc_gameRecords.insets = new Insets(10, 0, 20, 5);
		gbc_gameRecords.gridx = 3;
		gbc_gameRecords.gridy = 8;
		
		sessionRecords.setFont(new Font("Arial", Font.PLAIN, 25));
		sessionRecords.setForeground(new Color(70, 130, 180));
		sessionRecords.setBackground(new Color(70, 130, 180));
		sessionRecords.setFocusPainted(false);
		sessionRecords.setContentAreaFilled(false);
		sessionRecords.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_sessionRecords = new GridBagConstraints();
		gbc_sessionRecords.insets = new Insets(10, 60, 20, 60);
		gbc_sessionRecords.gridx = 4;
		gbc_sessionRecords.gridy = 8;
		
		add(displayName, gbc_displayName);
		add(settingsButton, gbc_settingsButton);
		add(comboBox, gbc_comboBox);
		add(btnGame, gbc_btnGame);
		add(btnGame2, gbc_btnGame2);
		add(btnGame3, gbc_btnGame3);
		add(viewStats, gbc_viewStats);
		add(gameRecords, gbc_gameRecords);
		add(sessionRecords, gbc_sessionRecords);
	}

	private void setUpListeners()
	{
		comboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				base.questionTypes = comboBox.getSelectedIndex();
			}
		});
		
		settingsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.SETTINGS);
			}
		});

		btnGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.GAME1);
			}
		});

		btnGame2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.GAME2);
			}
		});

		btnGame3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.GAME3);
			}
		});

		viewStats.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(0, -1);
			}
		});
		
		gameRecords.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(0, 0);
			}
		});
		
		sessionRecords.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(0, 1);
			}
		});
		
	}

}
