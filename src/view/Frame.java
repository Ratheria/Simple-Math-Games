/**
 *	@author Ariana Fairbanks
 */
package view;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import adapter.Controller;
import adapter.ViewStates;
import view_games.Game1;
import view_games.Game2;
import view_games.Game3;
import view_menus.RootMenu;
import view_menus.StudentMenu;
import view_menus.TeacherMenu;

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
		minSize = new Dimension(900, 600);
		setName("My Parents Don't Know What I Do");
		setTitle("My Parents Don't Know What I Do");
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setUndecorated(true);
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
			case LOGIN:
				panel.removeAll();
				panel = new Login(base);
				break;
			case SETTINGS:
				panel.removeAll();
				panel = new Settings(base);
				break;
			case PASSWORDCHANGE:
				panel.removeAll();
				panel = new PassChange(base);
				break;
			case ROOTMENU:
				panel.removeAll();
				panel = new RootMenu(base);
				break;
			case MANAGEDATA:
				panel.removeAll();
				panel = new ManageData(base);
				break;
			case ADDUSERS:
				panel.removeAll();
				panel = new AddUsers(base);
				break;
			case TEACHERMENU:
				panel.removeAll();
				panel = new TeacherMenu(base);
				break;
			case SELECTSTUDENTRECORDS:
				panel.removeAll();
				panel = new SelectStudentRecord(base);
				break;
			case STUDENTMENU:
				panel.removeAll();
				panel = new StudentMenu(base);
				break;
			case GAME1:
				panel.removeAll();
				panel = new Game1(base);
				break;
			case GAME2:
				panel.removeAll();
				panel = new Game2(base);
				break;
			case GAME3:
				panel.removeAll();
				panel = new Game3(base);
				break;
			default:
				System.out.println("Something went wrong when changing views.");
				break;
		}
		setContentPane(panel);
		panel.revalidate();
		panel.repaint();
	}
	
	public void updateState(int studentID, int value)
	{
		panel.removeAll();
		panel = (value == -1) ? new ShowStudentStats(base, studentID) : new ShowRecords(base, studentID, value);
		setContentPane(panel);
		panel.revalidate();
		panel.repaint();
	}
}
