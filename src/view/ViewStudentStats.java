/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.JPanel;

import adapter.Controller;

public class ViewStudentStats extends JPanel
{
	private static final long serialVersionUID = 2146437373343203144L;
	private Controller base;
	private int studentID;
	
	public ViewStudentStats(Controller base, int studentID)
	{
		this.base = base;
		this.studentID = studentID;
	}

	//TODO
	//total score(s), user high scores, overall high scores, games played
	//stats for last 10 games? first and last? determine by date?
	
	
}
