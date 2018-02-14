/**
 *	@author Ariana Fairbanks
 */
package view;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import adapter.Controller;
import adapter.ViewStates;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class RootMenu extends JPanel 
{
	private static final long serialVersionUID = -5873415761431343161L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel displayName;
//	private JButton logOut;
	private JButton settingsButton;
	private JLabel unlockAccountLabel;
	private JTextField unlockAccountTextField;
	private JButton unlockAccountButton;
	private JLabel resetPasswordLabel;
	private JTextField resetPasswordTextField;
	private JButton resetPasswordButton;
	private JButton manageUsersButton;
	
	public RootMenu(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		displayName = new JLabel(" ");
//		logOut = new JButton(" Log Out ");
		settingsButton = new JButton("    ");
		unlockAccountLabel = new JLabel("Unlock Account");
		unlockAccountTextField = new JTextField();
		unlockAccountButton = new JButton(" UNLOCK ");
		resetPasswordLabel = new JLabel("Reset Password");
		resetPasswordTextField = new JTextField();
		resetPasswordButton = new JButton(" RESET ");
		manageUsersButton = new JButton(" Manage Users ");

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
		setBackground(new Color(0, 0, 0));
		
		displayName.setVerticalAlignment(SwingConstants.TOP);
		displayName.setForeground(new Color(220, 220, 220));
		displayName.setFont(new Font("MV Boli", Font.PLAIN, 35));
		displayName.setText(base.getFullName());
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 4;
		gbc_displayName.insets = new Insets(15, 10, 5, 0);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;
		
/*		
  		logOut.setFont(new Font("MV Boli", Font.PLAIN, 30));
		logOut.setForeground(new Color(220, 220, 220));
		logOut.setBackground(new Color(0, 0, 0));
		logOut.setFocusPainted(false);
		logOut.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_logOut = new GridBagConstraints();
		gbc_logOut.gridwidth = 2;
		gbc_logOut.anchor = GridBagConstraints.EAST;
		gbc_logOut.insets = new Insets(0, 0, 20, 20);
		gbc_logOut.gridx = 3;
		gbc_logOut.gridy = 5;
*/
		
		settingsButton.setFont(new Font("MV Boli", Font.PLAIN, 25));
		settingsButton.setForeground(new Color(192, 192, 192));
		settingsButton.setBackground(new Color(105, 105, 105));
		settingsButton.setFocusPainted(false);
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_settingsButton.insets = new Insets(20, 20, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;
		
		unlockAccountLabel.setForeground(new Color(192, 192, 192));
		unlockAccountLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
		GridBagConstraints gbc_unlockAccountLabel = new GridBagConstraints();
		gbc_unlockAccountLabel.anchor = GridBagConstraints.WEST;
		gbc_unlockAccountLabel.gridwidth = 2;
		gbc_unlockAccountLabel.insets = new Insets(5, 20, 5, 5);
		gbc_unlockAccountLabel.gridx = 1;
		gbc_unlockAccountLabel.gridy = 2;
		
		unlockAccountTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
		unlockAccountTextField.setBackground(new Color(192, 192, 192));
		unlockAccountTextField.setForeground(new Color(0, 0, 0));
		unlockAccountTextField.setToolTipText("Username");
		unlockAccountTextField.setBorder(new CompoundBorder(new LineBorder(new Color(105, 105, 105)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_unlockAccountTextField = new GridBagConstraints();
		gbc_unlockAccountTextField.gridwidth = 2;
		gbc_unlockAccountTextField.insets = new Insets(0, 40, 5, 5);
		gbc_unlockAccountTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_unlockAccountTextField.gridx = 2;
		gbc_unlockAccountTextField.gridy = 3;
		
		unlockAccountButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
		unlockAccountButton.setForeground(new Color(211, 211, 211));
		unlockAccountButton.setBackground(new Color(0, 0, 0));
		unlockAccountButton.setFocusPainted(false);
		unlockAccountButton.setContentAreaFilled(false);
		unlockAccountButton.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_unlockAccountButton = new GridBagConstraints();
		gbc_unlockAccountButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_unlockAccountButton.insets = new Insets(0, 10, 5, 40);
		gbc_unlockAccountButton.gridx = 4;
		gbc_unlockAccountButton.gridy = 3;

		resetPasswordLabel.setForeground(new Color(192, 192, 192));
		resetPasswordLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
		GridBagConstraints gbc_resetPasswordLabel = new GridBagConstraints();
		gbc_resetPasswordLabel.anchor = GridBagConstraints.WEST;
		gbc_resetPasswordLabel.gridwidth = 2;
		gbc_resetPasswordLabel.insets = new Insets(5, 20, 5, 5);
		gbc_resetPasswordLabel.gridx = 1;
		gbc_resetPasswordLabel.gridy = 4;
		
		resetPasswordTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
		resetPasswordTextField.setBackground(new Color(192, 192, 192));
		resetPasswordTextField.setForeground(new Color(0, 0, 0));
		resetPasswordTextField.setToolTipText("Username");
		resetPasswordTextField.setBorder(new CompoundBorder(new LineBorder(new Color(105, 105, 105)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_resetPasswordTextField = new GridBagConstraints();
		gbc_resetPasswordTextField.gridwidth = 2;
		gbc_resetPasswordTextField.insets = new Insets(0, 40, 5, 5);
		gbc_resetPasswordTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_resetPasswordTextField.gridx = 2;
		gbc_resetPasswordTextField.gridy = 5;
		
		resetPasswordButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
		resetPasswordButton.setForeground(new Color(211, 211, 211));
		resetPasswordButton.setBackground(new Color(0, 0, 0));
		resetPasswordButton.setFocusPainted(false);
		resetPasswordButton.setContentAreaFilled(false);
		resetPasswordButton.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_resetPasswordButton = new GridBagConstraints();
		gbc_resetPasswordButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_resetPasswordButton.insets = new Insets(0, 10, 5, 40);
		gbc_resetPasswordButton.gridx = 4;
		gbc_resetPasswordButton.gridy = 5;
		
  		manageUsersButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
  		manageUsersButton.setForeground(new Color(220, 220, 220));
  		manageUsersButton.setBackground(new Color(0, 0, 0));
  		manageUsersButton.setFocusPainted(false);
		manageUsersButton.setContentAreaFilled(false);
  		manageUsersButton.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_manageUsersButton = new GridBagConstraints();
		gbc_manageUsersButton.gridwidth = 2;
		gbc_manageUsersButton.anchor = GridBagConstraints.EAST;
		gbc_manageUsersButton.insets = new Insets(0, 0, 20, 20);
		gbc_manageUsersButton.gridx = 3;
		gbc_manageUsersButton.gridy = 7;
		
		add(displayName, gbc_displayName);
//		add(logOut, gbc_logOut);
		add(settingsButton, gbc_settingsButton);
		add(unlockAccountLabel, gbc_unlockAccountLabel);
		add(unlockAccountTextField, gbc_unlockAccountTextField);
		add(unlockAccountButton, gbc_unlockAccountButton);
		add(resetPasswordLabel, gbc_resetPasswordLabel);
		add(resetPasswordTextField, gbc_resetPasswordTextField);
		add(resetPasswordButton, gbc_resetPasswordButton);
		add(manageUsersButton, gbc_manageUsersButton);
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
		
		unlockAccountButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				String userName = unlockAccountTextField.getText();
				int length = userName.length();
				if(length > 1)
				{
					if(length > 15)
					{
						userName = userName.substring(0, 15);
					}
					base.unlockAccount(userName);
				}
				unlockAccountTextField.setText("");
			}
		});
		
		resetPasswordButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				String userName = resetPasswordTextField.getText();
				int length = userName.length();
				if(length > 1)
				{
					if(length > 15)
					{
						userName = userName.substring(0, 15);
					}
					base.resetPassword(userName);
				}
				resetPasswordTextField.setText("");
			}
		});
		
		manageUsersButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				String userName = resetPasswordTextField.getText();
				int length = userName.length();
				if(length > 1)
				{
					if(length > 15)
					{
						userName = userName.substring(0, 15);
					}
					base.resetPassword(userName);
				}
				resetPasswordTextField.setText("");
			}
		});
	}
}
