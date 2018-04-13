/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.*;
import adapter.Controller;
import adapter.ViewStates;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class Settings extends JPanel
{
	private static final long serialVersionUID = 1212500511996392234L;
	private Controller base;
	private GridBagLayout layout;
	private JButton changePassButton;
	private JButton logoutButton;
	private JButton backButton;
	private JButton game1instructions;
	private JLabel g1instruct;
	private JButton game2instructions;
	private JLabel g2instruct;
	private JButton game3instructions;
	private JLabel g3instruct;

	public Settings(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		changePassButton = new JButton(" Change Password ");
		logoutButton = new JButton(" Log Out ");
		backButton = new JButton(" BACK ");
		game1instructions = new JButton("<html>   Fish Game<br/> Instructions   <html>");
		g1instruct = new JLabel("<html>To play click on the fish that has the <br/> answer to the math question. <br/> "
				+ "<br/>The game ends when time runs out or the <br/> correct fish swims off screen.</html>");
		game2instructions = new JButton("<html> Jellyfish Game<br/> Instructions <html>");
		g2instruct = new JLabel("<html>To play use the side arrow keys to help the <br/> jellyfish float down to the correct answer. <br/> "
				+ "<br/>The down arrow will help the jellyfish go faster <br/> and the up arrow will slow it back down. <br/>"
				+ " <br/>The game ends when time runs out. </html>");
		game3instructions = new JButton("<html> Shark Game<br/> Instructions <html>");
		g3instruct = new JLabel("<html>To play use the arrow keys to help the <br/> shark swim to the correct answer. <br/> "
				+ "<br/>The game ends when time runs out.</html>");
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
		layout.columnWidths = new int[]
		{ 0, 0, 0 };
		layout.rowHeights = new int[]
		{ 0, 0, 0, 0, 20 };
		layout.columnWeights = new double[]
		{ 1.0, 0.0, 1.0 };
		layout.rowWeights = new double[]
		{ 1.0, 1.0, 1.0, 1.0, 1.0 };
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(135, 206, 235));
		setBackground(new Color(245, 245, 245));
		
		g1instruct.setFont(new Font("Arial", Font.PLAIN, 30));
		g1instruct.setForeground(new Color(70, 130, 180));
		g1instruct.setBackground(new Color(208, 243, 255));
		g2instruct.setFont(new Font("Arial", Font.PLAIN, 30));
		g2instruct.setForeground(new Color(70, 130, 180));
		g2instruct.setBackground(new Color(208, 243, 255));
		g3instruct.setFont(new Font("Arial", Font.PLAIN, 30));
		g3instruct.setForeground(new Color(70, 130, 180));
		g3instruct.setBackground(new Color(208, 243, 255));

		changePassButton.setFont(new Font("Arial", Font.PLAIN, 30));
		changePassButton.setForeground(new Color(70, 130, 180));
		changePassButton.setContentAreaFilled(false);
		changePassButton.setFocusPainted(false);
		changePassButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_changePassButton = new GridBagConstraints();
		gbc_changePassButton.anchor = GridBagConstraints.NORTH;
		gbc_changePassButton.insets = new Insets(20, 5, 5, 5);
		gbc_changePassButton.gridwidth = 3;
		gbc_changePassButton.gridx = 0;
		gbc_changePassButton.gridy = 2;

		logoutButton.setFont(new Font("Arial", Font.PLAIN, 30));
		logoutButton.setForeground(new Color(70, 130, 180));
		logoutButton.setBackground(new Color(240, 240, 245));
		logoutButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_logoutButton = new GridBagConstraints();
		gbc_logoutButton.anchor = GridBagConstraints.NORTH;
		gbc_logoutButton.insets = new Insets(20, 5, 5, 5);
		gbc_logoutButton.gridwidth = 3;
		gbc_logoutButton.gridx = 0;
		gbc_logoutButton.gridy = 3;

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
		
		game1instructions.setFont(new Font("Arial", Font.PLAIN, 25));
		game1instructions.setForeground(new Color(70, 130, 180));
		game1instructions.setBackground(new Color(0, 0, 0));
		game1instructions.setFocusPainted(false);
		game1instructions.setContentAreaFilled(false);
		game1instructions.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_game1instructions = new GridBagConstraints();
		gbc_game1instructions.insets = new Insets(20, 20, 5, 5);
		gbc_game1instructions.anchor = GridBagConstraints.NORTH;
		gbc_game1instructions.gridx = 0;
		gbc_game1instructions.gridy = 4;
		
		game2instructions.setFont(new Font("Arial", Font.PLAIN, 25));
		game2instructions.setForeground(new Color(70, 130, 180));
		game2instructions.setBackground(new Color(0, 0, 0));
		game2instructions.setFocusPainted(false);
		game2instructions.setContentAreaFilled(false);
		game2instructions.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_game2instructions = new GridBagConstraints();
		gbc_game2instructions.insets = new Insets(20, 20, 5, 5);
		gbc_game2instructions.anchor = GridBagConstraints.NORTH;
		gbc_game2instructions.gridx = 1;
		gbc_game2instructions.gridy = 4;
		
		game3instructions.setFont(new Font("Arial", Font.PLAIN, 25));
		game3instructions.setForeground(new Color(70, 130, 180));
		game3instructions.setBackground(new Color(0, 0, 0));
		game3instructions.setFocusPainted(false);
		game3instructions.setContentAreaFilled(false);
		game3instructions.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_game3instructions = new GridBagConstraints();
		gbc_game3instructions.insets = new Insets(20, 20, 5, 5);
		gbc_game3instructions.anchor = GridBagConstraints.NORTH;
		gbc_game3instructions.gridx = 2;
		gbc_game3instructions.gridy = 4;
		
		add(changePassButton, gbc_changePassButton);
		add(logoutButton, gbc_logoutButton);
		add(backButton, gbc_backButton);
		add(game1instructions, gbc_game1instructions);
		add(game2instructions, gbc_game2instructions);
		add(game3instructions, gbc_game3instructions);
	}

	private void setUpListeners()
	{

		changePassButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.passwordChange);
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
		
		game1instructions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				JOptionPane.showMessageDialog(base.messagePanel, g1instruct, "Game 1 Instructions",JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		game2instructions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				JOptionPane.showMessageDialog(base.messagePanel, g2instruct, "Game 2 Instructions",JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		game3instructions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				JOptionPane.showMessageDialog(base.messagePanel, g3instruct, "Game 3 Instructions",JOptionPane.PLAIN_MESSAGE);
			}
		});
		
	}

}
