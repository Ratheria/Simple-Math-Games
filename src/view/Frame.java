/**
 *	@author Ariana Fairbanks
 */
package view;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
		minSize = new Dimension(960, 600);
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
	
	@SuppressWarnings("incomplete-switch")
	public void updateState()
	{
		ViewStates state = base.getState();
		switch (state)
		{
			case login:
				panel.removeAll();
				panel = new Login(base);
				break;
			case settings:
				panel.removeAll();
				panel = new Settings(base);
				break;
			case passwordChange:
				panel.removeAll();
				panel = new PassChange(base);
				break;
			case rootMenu:
				panel.removeAll();
				panel = new RootMenu(base);
				break;
			case teacherMenu:
				panel.removeAll();
				panel = new TeacherMenu(base);
				break;
			case selectStudentRecords:
				panel.removeAll();
				panel = new SelectStudentRecord(base);
				break;
			case studentMenu:
				panel.removeAll();
				panel = new StudentMenu(base);
				break;
			case addUsers:
				panel.removeAll();
				panel = new AddUsers(base);
				break;			
			case game1:
				panel.removeAll();
				panel = new Game1(base);
				break;
			case game2:
				panel.removeAll();
				panel = new Game2(base);
				break;
			case game3:
				panel.removeAll();
				panel = new Game3(base);
		}	

		setContentPane(panel);
		panel.revalidate();
		panel.repaint();
	}
	
}
