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

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PassChange extends JPanel
{
	private static final long serialVersionUID = -105254438804479292L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel oldPassLabel;
	private JLabel newPassLabel;
	private JLabel headLabel;
	private JTextField oPTextField;
	private JTextField nPTextField;
	private JButton enterButton;
	private JButton backButton;

	public PassChange(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		oldPassLabel = new JLabel("Old Pass: ");
		newPassLabel = new JLabel("New Pass: ");
		headLabel = new JLabel("CHANGE PASSWORD");
		oPTextField = new JTextField();
		nPTextField = new JTextField();
		enterButton = new JButton("ENTER");
		backButton = new JButton("BACK");

		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout()
	{
		layout.columnWidths = new int[]
		{ 0, 0 };
		layout.rowHeights = new int[]
		{ 0, 0, 36, 0, 47 };
		layout.columnWeights = new double[]
		{ 0.0, 1.0 };
		layout.rowWeights = new double[]
		{ 0.0, 1.0, 0.0, 0.0, 1.0 };
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(135, 206, 235));
		setBackground(new Color(245, 245, 245));

		oldPassLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		oldPassLabel.setForeground(new Color(70, 130, 180));
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.anchor = GridBagConstraints.WEST;
		gbc_usernameLabel.insets = new Insets(5, 40, 5, 5);
		gbc_usernameLabel.gridx = 0;
		gbc_usernameLabel.gridy = 2;

		newPassLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		newPassLabel.setForeground(new Color(70, 130, 180));
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.anchor = GridBagConstraints.WEST;
		gbc_passwordLabel.insets = new Insets(15, 40, 5, 5);
		gbc_passwordLabel.gridx = 0;
		gbc_passwordLabel.gridy = 3;

		headLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		headLabel.setForeground(new Color(135, 206, 250));
		GridBagConstraints gbc_loginLabel = new GridBagConstraints();
		gbc_loginLabel.gridwidth = 3;
		gbc_loginLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginLabel.insets = new Insets(30, 5, 5, 5);
		gbc_loginLabel.gridx = 0;
		gbc_loginLabel.gridy = 1;

		oPTextField.setHorizontalAlignment(SwingConstants.LEADING);
		oPTextField.setForeground(new Color(70, 130, 180));
		oPTextField.setFont(new Font("Arial", Font.PLAIN, 25));
		oPTextField.setBackground(new Color(240, 240, 245));
		oPTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 0, 128)),
				BorderFactory.createEmptyBorder(0, 25, 0, 0)));
		GridBagConstraints gbc_uTextField = new GridBagConstraints();
		gbc_uTextField.gridwidth = 2;
		gbc_uTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_uTextField.insets = new Insets(5, 5, 5, 40);
		gbc_uTextField.gridx = 1;
		gbc_uTextField.gridy = 2;

		nPTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nPTextField.setForeground(new Color(70, 130, 180));
		nPTextField.setFont(new Font("Arial", Font.PLAIN, 25));
		nPTextField.setBackground(new Color(240, 240, 245));
		nPTextField.setColumns(5);
		nPTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 0, 128)),
				BorderFactory.createEmptyBorder(0, 25, 0, 0)));
		GridBagConstraints gbc_pTextField = new GridBagConstraints();
		gbc_pTextField.gridwidth = 2;
		gbc_pTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_pTextField.insets = new Insets(5, 5, 5, 40);
		gbc_pTextField.gridx = 1;
		gbc_pTextField.gridy = 3;

		enterButton.setFont(new Font("Arial", Font.PLAIN, 30));
		enterButton.setForeground(new Color(135, 206, 250));
		enterButton.setBackground(new Color(0, 0, 0));
		enterButton.setFocusPainted(false);
		enterButton.setContentAreaFilled(false);
		enterButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 128)), new EmptyBorder(5, 10, 0, 10)));
		GridBagConstraints gbc_enterButton = new GridBagConstraints();
		gbc_enterButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_enterButton.insets = new Insets(5, 5, 40, 40);
		gbc_enterButton.gridx = 2;
		gbc_enterButton.gridy = 4;

		backButton.setFont(new Font("Arial", Font.PLAIN, 30));
		backButton.setForeground(new Color(70, 130, 180));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 128)), new EmptyBorder(5, 10, 0, 10)));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
		add(backButton, gbc_backButton);

		add(oldPassLabel, gbc_usernameLabel);
		add(newPassLabel, gbc_passwordLabel);
		add(headLabel, gbc_loginLabel);
		add(oPTextField, gbc_uTextField);
		add(nPTextField, gbc_pTextField);
		add(enterButton, gbc_enterButton);
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
				base.changeState(ViewStates.SETTINGS);
			}
		});
	}

	private void changePass()
	{
		String newPass = nPTextField.getText();
		String oldPass = oPTextField.getText();
		int oLength = oldPass.length();
		int nLength = newPass.length();
		if (oLength > 0 && nLength > 0)
		{
			if (oLength > 15)
			{
				oldPass = oldPass.substring(0, 15);
			}
			if (nLength > 15)
			{
				newPass = newPass.substring(0, 15);
			}
			base.changePassword(oldPass, newPass);
		}
		else
		{
			JPanel errorPanel = new JPanel();
			JOptionPane.showMessageDialog(errorPanel, "Please enter the old password and the new password desired.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		oPTextField.setText("");
		nPTextField.setText("");
	}
}
