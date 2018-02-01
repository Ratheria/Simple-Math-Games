/**
 *	@author Ariana Fairbanks
 */
package view;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import adapter.SMGController;

public class SMGFrame extends JFrame
{
	private static final long serialVersionUID = -2248105492340561524L;
	private SMGController base;
	private JPanel panel;
	private SMGLogIn login;
	private Dimension minSize;
	

	public SMGFrame(SMGController base)
	{
		this.base = base;
		login = new SMGLogIn(base);
		panel = login;
		minSize = new Dimension(700, 500);
		setName("My Parents Don't Know What I Do");
		setTitle("My Parents Don't Know What I Do");
		setSize(900, 650);
		setMinimumSize(minSize);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
	}
	
	public void updateState()
	{
		int state = base.getState();
		switch (state)
		{
			case 0:
				panel.removeAll();
				panel = new SMGLogIn(base);
				break;
			case 1:
				panel.removeAll();
				panel = new SMGRootMenu(base);
				break;
			case 2:
				panel.removeAll();
				panel = new SMGTeacherMenu(base);
				break;
			case 3:
				panel.removeAll();
				panel = new SMGStudentMenu(base);
				break;
			case 4:
				panel.removeAll();
				panel = new SMGSettings(base);
				break;
			case 5:
				panel.removeAll();
				panel = new SMGPassChange(base);
				break;
		}
		setContentPane(panel);
		panel.revalidate();
		panel.repaint();
	}
}
