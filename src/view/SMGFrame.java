/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import adapter.SMGController;

public class SMGFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private SMGController base;
	private JPanel panel;
	private SMGLogIn login;
	private SMGStudentMenu studentMenu;
	

	public SMGFrame(SMGController base)
	{
		this.base = base;
		login = new SMGLogIn(base);
		studentMenu = new SMGStudentMenu(base);
		panel = login;
		setName("My Parents Don't Know What I Do");
		setTitle("My Parents Don't Know What I Do");
		setSize(950, 700);
		setVisible(true);
		setResizable(false);
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
				panel = new SMGStudentMenu(base);
				break;
		}
		setContentPane(panel);
		panel.revalidate();
		panel.repaint();
	}
}
