package view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import adapter.SMGController;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SMGRootMenu extends JPanel 
{
	private SMGController base;
	private SpringLayout springLayout;
	private JLabel displayName;
	private JButton logOut;
	private JButton settingsButton;
	
	public SMGRootMenu(SMGController base)
	{
		this.base = base;
		springLayout = new SpringLayout();
		displayName = new JLabel(" ");
		logOut = new JButton("Log Out");
		settingsButton = new JButton("  ");
		
		setLayout(springLayout);
		add(displayName);
		add(logOut);
		add(settingsButton);

		setUpPanel();
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpPanel()
	{
		displayName.setText(base.getFullName());
	}

	private void setUpLayout() 
	{
		setBorder(new LineBorder(new Color(255, 255, 255), 10));
		setForeground(new Color(255, 255, 255));
		setBackground(new Color(0, 0, 0));
		springLayout.putConstraint(SpringLayout.NORTH, displayName, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, displayName, 20, SpringLayout.WEST, this);
		displayName.setForeground(new Color(255, 255, 255));
		displayName.setFont(new Font("Sylfaen", Font.PLAIN, 30));
		
		logOut.setFont(new Font("MV Boli", Font.PLAIN, 30));
		logOut.setForeground(new Color(255, 255, 255));
		logOut.setBackground(new Color(0, 0, 0));
		springLayout.putConstraint(SpringLayout.SOUTH, logOut, -20, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, logOut, -20, SpringLayout.EAST, this);
		logOut.setFocusPainted(false);
		logOut.setContentAreaFilled(false);
		logOut.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE),
				BorderFactory.createEmptyBorder(5, 10, 0, 10)));
		
		springLayout.putConstraint(SpringLayout.SOUTH, settingsButton, 0, SpringLayout.SOUTH, displayName);
		settingsButton.setForeground(new Color(255, 255, 255));
		settingsButton.setBackground(new Color(255, 255, 255));
		springLayout.putConstraint(SpringLayout.NORTH, displayName, 0, SpringLayout.NORTH, settingsButton);
		springLayout.putConstraint(SpringLayout.WEST, displayName, 20, SpringLayout.EAST, settingsButton);
		springLayout.putConstraint(SpringLayout.NORTH, settingsButton, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, settingsButton, 20, SpringLayout.WEST, this);
	}
	
	private void setUpListeners() 
	{
		logOut.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.logout();
			}
		});
		
		settingsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{
				base.changeState(4);
				//settings
			}
		});
	}
}
