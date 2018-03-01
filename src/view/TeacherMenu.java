/**
 *	@author Ariana Fairbanks
 */
package view;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import adapter.Controller;
import adapter.ViewStates;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;

public class TeacherMenu extends JPanel implements ChangeListener
{
	private static final long serialVersionUID = -1163688838297846114L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel displayName;
	private JButton settingsButton;
	private JButton viewRecordsButton;
	private JLabel addEquationsLabel;
	private JTextField addEquationsTextField;
	private JButton addEquationsButton;
	private JSlider frequencySlider;
	private JLabel frequencyLabel;
	private int baseFrequency;
	
	public TeacherMenu(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		settingsButton = new JButton("    ");
		displayName = new JLabel(" ");
		frequencyLabel = new JLabel("Custom Equation Frequency");
		frequencySlider = new JSlider();
		viewRecordsButton = new JButton(" View Records ");
		addEquationsLabel = new JLabel("Add Custom Equation");
		addEquationsTextField = new JTextField();
		addEquationsButton = new JButton(" Add Equation ");
		baseFrequency = base.getFrequency();

		setUpPane();
		setUpLayout();
		setUpListeners();
	}

	private void setUpPane()
	{
		frequencySlider.addChangeListener(this);
	}
	
	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.4, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(0, 0, 0));

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
		
		displayName.setVerticalAlignment(SwingConstants.BOTTOM);
		displayName.setForeground(new Color(135, 206, 250));
		displayName.setFont(new Font("MV Boli", Font.PLAIN, 35));
		displayName.setText(base.getFullName());
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 4;
		gbc_displayName.insets = new Insets(15, 10, 5, 5);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;
		
		frequencyLabel.setForeground(new Color(135, 206, 250));
		frequencyLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
		GridBagConstraints gbc_frequencyLabel = new GridBagConstraints();
		gbc_frequencyLabel.gridwidth = 6;
		gbc_frequencyLabel.insets = new Insets(0, 0, 5, 5);
		gbc_frequencyLabel.gridx = 0;
		gbc_frequencyLabel.gridy = 1;
		
		frequencySlider.setValue(baseFrequency);
		frequencySlider.setToolTipText("Frequency");
		frequencySlider.setPaintLabels(true);
		frequencySlider.setMaximum(10);
		frequencySlider.setMajorTickSpacing(1);
		frequencySlider.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frequencySlider.setSnapToTicks(true);
		frequencySlider.setForeground(new Color(176, 224, 230));
		frequencySlider.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.gridwidth = 6;
		gbc_slider.insets = new Insets(5, 25, 5, 25);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 2;
		
		addEquationsLabel.setForeground(new Color(135, 206, 250));
		addEquationsLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
		GridBagConstraints gbc_addEquationsLabel = new GridBagConstraints();
		gbc_addEquationsLabel.anchor = GridBagConstraints.WEST;
		gbc_addEquationsLabel.gridwidth = 3;
		gbc_addEquationsLabel.insets = new Insets(5, 20, 5, 5);
		gbc_addEquationsLabel.gridx = 1;
		gbc_addEquationsLabel.gridy = 4;
		
		addEquationsTextField.setFont(new Font("MV Boli", Font.PLAIN, 15));
		addEquationsTextField.setForeground(new Color(135, 206, 250));
		addEquationsTextField.setBackground(new Color(0, 0, 0));
		addEquationsTextField.setToolTipText("Username");
		addEquationsTextField.setBorder(new CompoundBorder(new LineBorder(new Color(135, 206, 250)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_addEquationsTextField = new GridBagConstraints();
		gbc_addEquationsTextField.gridwidth = 3;
		gbc_addEquationsTextField.insets = new Insets(0, 40, 5, 5);
		gbc_addEquationsTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addEquationsTextField.gridx = 2;
		gbc_addEquationsTextField.gridy = 5;
		
		addEquationsButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
		addEquationsButton.setForeground(new Color(135, 206, 250));
		addEquationsButton.setBackground(new Color(70, 130, 180));
		addEquationsButton.setFocusPainted(false);
		addEquationsButton.setContentAreaFilled(false);
		addEquationsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_addEquationsButton = new GridBagConstraints();
		gbc_addEquationsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addEquationsButton.insets = new Insets(0, 10, 5, 40);
		gbc_addEquationsButton.gridx = 5;
		gbc_addEquationsButton.gridy = 5;
		
  		viewRecordsButton.setFont(new Font("MV Boli", Font.PLAIN, 30));
		viewRecordsButton.setForeground(new Color(135, 206, 250));
		viewRecordsButton.setBackground(new Color(70, 130, 180));
  		viewRecordsButton.setFocusPainted(false);
  		viewRecordsButton.setContentAreaFilled(false);
  		viewRecordsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_viewRecordsButton = new GridBagConstraints();
		gbc_viewRecordsButton.gridwidth = 2;
		gbc_viewRecordsButton.anchor = GridBagConstraints.EAST;
		gbc_viewRecordsButton.insets = new Insets(0, 0, 20, 20);
		gbc_viewRecordsButton.gridx = 4;
		gbc_viewRecordsButton.gridy = 9;
		
		add(settingsButton, gbc_settingsButton);
		add(displayName, gbc_displayName);
		add(frequencyLabel, gbc_frequencyLabel);
		add(frequencySlider, gbc_slider);
		add(addEquationsLabel, gbc_addEquationsLabel);
		add(addEquationsTextField, gbc_addEquationsTextField);
		add(addEquationsButton, gbc_addEquationsButton);
		add(viewRecordsButton, gbc_viewRecordsButton);
	}
	
	private void setUpListeners() 
	{
		settingsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	base.changeState(ViewStates.settings);	}
		});
		
		addEquationsButton.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent onClick)
			{	
				String newEquation = addEquationsTextField.getText();
				if(newEquation != null)
				{
					base.addEquation(newEquation);
				}
				addEquationsTextField.setText("");
			}
		});
		
		viewRecordsButton.addActionListener(new ActionListener()
		{ 
			public void actionPerformed(ActionEvent onClick)
			{	base.changeState(ViewStates.viewRecords);	}
		});
	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		//in case there are more things listening for change later
		if(e.getSource().equals(frequencySlider))
		{
			sliderChanged();
		}
	}
	
	private void sliderChanged()
	{
		baseFrequency = base.getFrequency();
		if(!frequencySlider.getValueIsAdjusting())
		{
			int currentValue = frequencySlider.getValue();
			if(baseFrequency != currentValue)
			{
				base.changeCustomEquations(base.getEquationString(), currentValue, base.getNumberOfEquations());
				baseFrequency = base.getFrequency();
				frequencySlider.setValue(baseFrequency);
			}
		}
	}
}