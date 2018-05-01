/**
 *	@author Ariana Fairbanks
 */
package view;

import javax.swing.*;
import adapter.Controller;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class Login extends JPanel
{
	private static final long serialVersionUID = 5053139107195686875L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel loginLabel;
	private JTextField uTextField;
	private JPasswordField pTextField;
	private JButton enterButton;

	//TODO reset loginAttempts on every login???
	public Login(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		loginLabel = new JLabel("LOGIN");
		uTextField = new JTextField();
		pTextField = new JPasswordField();
		enterButton = new JButton("ENTER");

		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout()
	{
		layout.columnWidths = new int[]
		{ 0, 0 };
		layout.rowHeights = new int[]
		{ 0, 36, 0, 0, 0, 47 };
		layout.columnWeights = new double[]
		{ 0.0, 1.0 };
		layout.rowWeights = new double[]
		{ 10.0, 0.0, 1.0, 0.0, 8.0, 4.0 };
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(135, 206, 235));
		setBackground(new Color(245, 245, 245));

		usernameLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		usernameLabel.setForeground(new Color(70, 130, 180));
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.anchor = GridBagConstraints.WEST;
		gbc_usernameLabel.insets = new Insets(5, 75, 5, 5);
		gbc_usernameLabel.gridx = 0;
		gbc_usernameLabel.gridy = 1;

		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		passwordLabel.setForeground(new Color(70, 130, 180));
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.anchor = GridBagConstraints.WEST;
		gbc_passwordLabel.insets = new Insets(5, 75, 5, 5);
		gbc_passwordLabel.gridx = 0;
		gbc_passwordLabel.gridy = 3;

		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setFont(new Font("Arial", Font.PLAIN, 85));
		loginLabel.setForeground(new Color(70, 130, 180));
		GridBagConstraints gbc_loginLabel = new GridBagConstraints();
		gbc_loginLabel.gridwidth = 3;
		gbc_loginLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginLabel.insets = new Insets(40, 5, 5, 0);
		gbc_loginLabel.gridx = 0;
		gbc_loginLabel.gridy = 0;

		uTextField.setHorizontalAlignment(SwingConstants.LEADING);
		uTextField.setForeground(new Color(0, 0, 128));
		uTextField.setFont(new Font("Arial", Font.PLAIN, 25));
		uTextField.setBackground(new Color(240, 240, 245));
		uTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 0, 128)),
				BorderFactory.createEmptyBorder(0, 25, 0, 0)));
		GridBagConstraints gbc_uTextField = new GridBagConstraints();
		gbc_uTextField.gridwidth = 2;
		gbc_uTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_uTextField.insets = new Insets(5, 5, 5, 75);
		gbc_uTextField.gridx = 1;
		gbc_uTextField.gridy = 1;

		pTextField.setHorizontalAlignment(SwingConstants.LEFT);
		pTextField.setForeground(new Color(0, 0, 128));
		pTextField.setFont(new Font("Arial", Font.PLAIN, 25));
		pTextField.setBackground(new Color(240, 240, 245));
		pTextField.setColumns(5);
		pTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 0, 128)),
				BorderFactory.createEmptyBorder(0, 25, 0, 0)));
		GridBagConstraints gbc_pTextField = new GridBagConstraints();
		gbc_pTextField.gridwidth = 2;
		gbc_pTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_pTextField.insets = new Insets(5, 5, 5, 75);
		gbc_pTextField.gridx = 1;
		gbc_pTextField.gridy = 3;

		enterButton.setFont(new Font("Arial", Font.PLAIN, 28));
		enterButton.setForeground(new Color(70, 130, 180));
		enterButton.setBackground(new Color(0, 0, 0));
		enterButton.setFocusPainted(false);
		enterButton.setContentAreaFilled(false);
		enterButton.setBorder(new CompoundBorder(new LineBorder(new Color(30, 144, 255)), new EmptyBorder(2, 10, 0, 10)));
		GridBagConstraints gbc_enterButton = new GridBagConstraints();
		gbc_enterButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_enterButton.insets = new Insets(5, 5, 40, 40);
		gbc_enterButton.gridx = 2;
		gbc_enterButton.gridy = 5;

		add(usernameLabel, gbc_usernameLabel);
		add(passwordLabel, gbc_passwordLabel);
		add(loginLabel, gbc_loginLabel);
		add(uTextField, gbc_uTextField);
		add(pTextField, gbc_pTextField);
		add(enterButton, gbc_enterButton);
	}

	private void setUpListeners()
	{
		pTextField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				login();
			}
		});

		enterButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				login();
			}
		});
	}

	private void login()
	{
		String userName = uTextField.getText();
		String pass = String.valueOf(pTextField.getPassword());
		int uLength = userName.length();
		int pLength = pass.length();
		if (uLength > 0 && pLength > 0)
		{
			if (uLength > 15)
			{
				userName = userName.substring(0, 15);
			}
			if (pLength > 15)
			{
				pass = pass.substring(0, 15);
			}
			base.checkLogin(userName, pass);
			// get ID here???
		}
		else
		{
			JPanel errorPanel = new JPanel();
			JOptionPane.showMessageDialog(errorPanel, "Please enter a username and password.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		uTextField.setText("");
		pTextField.setText("");
		uTextField.requestFocus();
	}
}
