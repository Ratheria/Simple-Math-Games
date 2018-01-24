/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Container;

import javax.swing.JFrame;
import adapter.SMGController;

public class SMGFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private SMGController base;
	private Object panel;

	public SMGFrame(SMGController base)
	{
		this.base = base;
		updateState();
		setName("My Parents Don't Know What I Do");
		setTitle("My Parents Don't Know What I Do");
		setSize(850, 650);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateState()
	{
		int state = base.getState();
		panel = new SMGLogIn(base);
		switch (state)
		{
			case 0:
				panel = new SMGLogIn(base);
				break;
		}
		setContentPane((Container) panel);
	}
}
