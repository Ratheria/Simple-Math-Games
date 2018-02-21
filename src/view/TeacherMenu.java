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

public class TeacherMenu extends JPanel 
{
	private static final long serialVersionUID = -1163688838297846114L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel displayName;
	private JButton settingsButton;
	private JButton viewRecordsButton;
	
	public TeacherMenu(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		displayName = new JLabel(" ");
		settingsButton = new JButton("    ");
		viewRecordsButton = new JButton("    ");

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
		setBackground(new Color(0, 0, 0));
		
		displayName.setVerticalAlignment(SwingConstants.BOTTOM);
		displayName.setForeground(new Color(135, 206, 250));
		displayName.setFont(new Font("MV Boli", Font.PLAIN, 35));
		displayName.setText(base.getFullName());
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 3;
		gbc_displayName.insets = new Insets(15, 10, 5, 5);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;
		
		settingsButton.setFont(new Font("MV Boli", Font.PLAIN, 25));
		settingsButton.setForeground(new Color(135, 206, 250));
		settingsButton.setBackground(new Color(70, 130, 180));
		settingsButton.setFocusPainted(false);
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_settingsButton.insets = new Insets(20, 25, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;
		
		viewRecordsButton.setFont(new Font("MV Boli", Font.PLAIN, 25));
		viewRecordsButton.setForeground(new Color(135, 06, 50));
		viewRecordsButton.setBackground(new Color(70, 130, 180));
		viewRecordsButton.setFocusPainted(false);
		viewRecordsButton.setContentAreaFilled(false);
		viewRecordsButton.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_viewRecordsButton = new GridBagConstraints();
		gbc_viewRecordsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_viewRecordsButton.insets = new Insets(20, 25, 5, 5);
		gbc_viewRecordsButton.gridx = 0;
		gbc_viewRecordsButton.gridy = 0;
		
		add(displayName, gbc_displayName);
		//add(settingsButton, gbc_settingsButton);
		add(viewRecordsButton, gbc_viewRecordsButton);
	}
	
	private void setUpListeners() 
	{
		settingsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	base.changeState(ViewStates.settings);	}
		});
		
		viewRecordsButton.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent onClick)
			{	base.changeState(ViewStates.viewRecords);	}
		});
	}
}