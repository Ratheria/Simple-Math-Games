package view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import adapter.SMGController;

public class SMGStudentMenu extends JPanel 
{
	private SMGController base;
	private SpringLayout springLayout;
	
	public SMGStudentMenu(SMGController base)
	{
		this.base = base;
		springLayout = new SpringLayout();
		
		setLayout(springLayout);
		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout() 
	{
		setBorder(new LineBorder(new Color(0, 0, 255), 10));
		setForeground(new Color(0, 0, 255));
		setBackground(new Color(0, 0, 0));
	}
	
	private void setUpListeners() 
	{
		
	}
}
