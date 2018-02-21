/**
 *	@author Ariana Fairbanks
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.Controller;

public class ManageUsers extends JPanel
{
	private static final long serialVersionUID = 7833894194480819845L;
	private JFileChooser fileChoose;
	private Controller base;
	private GridBagLayout layout;
	private JLabel header;
	private JButton backButton;
	private JButton importUsersButton;
	
	public ManageUsers(Controller base)
	{
		this.base = base;
		fileChoose = new JFileChooser();
		layout = new GridBagLayout();
		header = new JLabel(" View Student Records ");
		backButton = new JButton(" BACK ");
		importUsersButton = new JButton(" Users From CSV ");

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
		
		header.setVerticalAlignment(SwingConstants.TOP);
		header.setForeground(new Color(220, 220, 220));
		header.setFont(new Font("MV Boli", Font.PLAIN, 35));
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 4;
		gbc_displayName.insets = new Insets(15, 10, 5, 0);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;
		
		backButton.setFont(new Font("MV Boli", Font.PLAIN, 25));
		backButton.setForeground(new Color(192, 192, 192));
		backButton.setBackground(new Color(105, 105, 105));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(192, 192, 192), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_settingsButton.insets = new Insets(20, 20, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;		
		
		GridBagConstraints gbc_importUsersButton = new GridBagConstraints();
		gbc_importUsersButton.insets = new Insets(0, 0, 5, 5);
		gbc_importUsersButton.gridx = 3;
		gbc_importUsersButton.gridy = 2;
		
		add(header, gbc_displayName);
		add(backButton, gbc_settingsButton);
		add(importUsersButton, gbc_importUsersButton);
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
		
		importUsersButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				JPanel temp = new JPanel();
			    fileChoose.setFileFilter(new FileFilter() 
			    {
			        @Override
			        public boolean accept(File f) 
			        {	return f.getName().endsWith(".csv");	}
			        @Override
			        public String getDescription() 
			        {	return "CSV files";	}
			    });
				int valueReturned = fileChoose.showOpenDialog(temp);
				if(valueReturned == JFileChooser.APPROVE_OPTION)
				{
					base.importUsers(fileChoose.getSelectedFile());
				}
			}
		});
	
	}
	
	
}
