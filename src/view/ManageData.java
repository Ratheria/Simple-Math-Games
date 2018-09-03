/**
 *	@author Ariana Fairbanks
 * Jadie Adams
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import adapter.Controller;

public class ManageData extends JPanel
{
	private static final long serialVersionUID = 4214156551837153632L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel header;
	private JButton backButton;
	private JComboBox<String> userOptions;
	private JButton manageDataButton;
	private int value;

	public ManageData(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		header = new JLabel("Manage Data");
		backButton = new JButton(" BACK ");
		userOptions = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[]
		{ " Remove All Students", " Remove All Teachers", " Remove Students and Teachers", " Remove All Users", 
		  " Remove Game Records and Sessions", " Remove Game High Scores", "Remove All Game Data", 
		  " Remove All Data"}));
		manageDataButton = new JButton(" REMOVE SPECIFIED DATA ");
		value = 0;

		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout()
	{
		layout.rowHeights = new int[]
		{ 0, 0, 0, 0, 0 };
		layout.rowWeights = new double[]
		{ 0.0, 2.0, 0.0, 1.0, 1.0 };
		layout.columnWidths = new int[]
		{ 0, 0, 0, 0, 0 };
		layout.columnWeights = new double[]
		{ 0.0, 0.0, 1.0, 1.0, 0.0 };
		setLayout(layout);
		setBorder(new LineBorder(new Color(128, 128, 128), 10));
		setForeground(new Color(105, 105, 105));
		setBackground(new Color(245, 245, 245));
		header.setForeground(new Color(105, 105, 105));
		header.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 4;
		gbc_displayName.insets = new Insets(15, 10, 5, 0);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;

		backButton.setFont(new Font("Arial", Font.PLAIN, 25));
		backButton.setForeground(new Color(105, 105, 105));
		backButton.setBackground(new Color(105, 105, 105));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(105, 105, 105), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_settingsButton.insets = new Insets(15, 20, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;

		userOptions.setForeground(new Color(105, 105, 105));
		userOptions.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_userOptions = new GridBagConstraints();
		gbc_userOptions.anchor = GridBagConstraints.WEST;
		gbc_userOptions.gridwidth = 2;
		gbc_userOptions.insets = new Insets(10, 30, 10, 5);
		gbc_userOptions.gridx = 0;
		gbc_userOptions.gridy = 2;

		manageDataButton.setVerticalAlignment(SwingConstants.TOP);
		manageDataButton.setForeground(new Color(105, 105, 105));
		manageDataButton.setBackground(new Color(105, 105, 105));
		manageDataButton.setFocusPainted(false);
		manageDataButton.setContentAreaFilled(false);
		manageDataButton.setBorder(new LineBorder(new Color(105, 105, 105), 2));
		manageDataButton.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_addUserButton = new GridBagConstraints();
		gbc_addUserButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_addUserButton.gridwidth = 2;
		gbc_addUserButton.insets = new Insets(0, 0, 20, 20);
		gbc_addUserButton.gridx = 3;
		gbc_addUserButton.gridy = 5;

		add(header, gbc_displayName);
		add(backButton, gbc_settingsButton);
		add(userOptions, gbc_userOptions);
		add(manageDataButton, gbc_addUserButton);
	}

	private void setUpListeners()
	{
		backButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.returnToMenu();
			}
		});

		userOptions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (value != userOptions.getSelectedIndex())
				{
					value = userOptions.getSelectedIndex();
				}
			}
		});

		manageDataButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				//TODO
				//{ " Remove All Students", 
				//" Remove All Teachers", 
				//" Remove Students and Teachers", 
				//" Remove All Users", 
				//" Remove Game Records and Sessions", 
				//" Remove Game High Scores", 
				//"Remove All Game Data", 
				//" Remove All Data"}));
				
				switch(value)
				{
					case 0:
						base.deleteUserRange(3);
						break;
					case 1:
						base.deleteUserRange(2);
						break;
				}
			}
		});

	}

}
