/**
 *	@author Ariana Fairbanks
 */
package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import adapter.Controller;
import adapter.ViewStates;

public class Frame extends JFrame
{
	public static Dimension DIMENSIONS;
	private static final long serialVersionUID = -2248105492340561524L;
	private Controller base;
	private JPanel panel;
	private Login login;
	private Dimension minSize;
	

	public Frame(Controller base)
	{
		this.base = base;
		login = new Login(base);
		panel = login;
		minSize = new Dimension(950, 600);
		setName("My Parents Don't Know What I Do");
		setTitle("My Parents Don't Know What I Do");
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setSize(minSize);
		setMinimumSize(minSize);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		DIMENSIONS = getSize();
	}
	
	public void updateState()
	{
		ViewStates state = base.getState();
		switch (state)
		{
			case login:
				panel.removeAll();
				panel = new Login(base);
				break;
			case rootMenu:
				panel.removeAll();
				panel = new RootMenu(base);
				break;
			case teacherMenu:
				panel.removeAll();
				panel = new TeacherMenu(base);
				break;
			case studentMenu:
				panel.removeAll();
				panel = new StudentMenu(base);
				break;
			case settings:
				panel.removeAll();
				panel = new Settings(base);
				break;
			case passwordChange:
				panel.removeAll();
				panel = new PassChange(base);
				break;
			case manageUsers:
				panel.removeAll();
				panel = new ManageUsers(base);
				break;			
			case game1:
				panel.removeAll();
				panel = new Game1(base);
				break;
			case viewRecords:
				panel.removeAll();
				panel = new ViewRecords(base);
				break;
		}	

		setContentPane(panel);
		panel.revalidate();
		panel.repaint();
	}
	
}
