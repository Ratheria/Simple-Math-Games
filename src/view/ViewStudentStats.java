/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import adapter.Controller;

public class ViewStudentStats extends JPanel
{
	private static final long serialVersionUID = 2146437373343203144L;
	private Controller base;
	private GridBagLayout layout;
	private JButton backButton;
	private JTextArea textArea;
	private int studentID;

	public ViewStudentStats(Controller base, int studentID)
	{
		this.base = base;
		this.studentID = studentID;
		layout = new GridBagLayout();
		backButton = new JButton(" BACK ");
		textArea = new JTextArea();
		
		setUp();
	}
	
	private void setUp()
	{
		try
		{
			ResultSet res = base.getStats(studentID);
			String statsString = "";
			res.next();
			statsString = statsString
						+ "OVERALL\n"
						+ "    Total Score                    : " + res.getInt("scoreSum") + "\n"
						+ "    Total Games Played      : " + res.getInt("gameSum") + "\n"
						+ "    Total Correct Answers   : " + res.getInt("correctSum") + "\n\n";
			
			ResultSet classHighscore = base.getClassHighscore(1);
			ResultSet highscore = base.getHighscore(1);
			String classHigh = "";
			String high = "";
			int classHighScore = 0;
			int highScore = 0;
			if(classHighscore.next())
			{
				classHigh = classHighscore.getString("firstName");
				if(classHigh == null)
				{
					classHigh = "";
				}
				else 
				{
					classHighScore = classHighscore.getInt("score");
					classHigh = "   -   " + classHigh + " " + classHighscore.getString("lastName");
				}
			}
			if(highscore.next())
			{
				high = highscore.getString("firstName");
				if(high == null)
				{
					high = "";
				}
				else 
				{
					highScore = highscore.getInt("score");
					high = "   -   " + high + " " + highscore.getString("lastName");
				}	
			}
			statsString = statsString
						+ "FISH GAME HIGH SCORES\n"
						+ "    Personal\t: " + base.getPersonalHighscore(studentID, 1) + "\n"
						+ "    Class\t: " + classHighScore + classHigh + "\n"
						+ "    School\t: " + highScore + high + "\n\n";
			
			classHighscore = base.getClassHighscore(2);
			highscore = base.getHighscore(2);
			classHigh = "";
			high = "";
			classHighScore = 0;
			highScore = 0;
			if(classHighscore.next())
			{
				classHigh = classHighscore.getString("firstName");
				if(classHigh == null)
				{
					classHigh = "";
				}
				else 
				{
					classHighScore = classHighscore.getInt("score");
					classHigh = "   -   " + classHigh + " " + classHighscore.getString("lastName");
				}
			}
			if(highscore.next())
			{
				high = highscore.getString("firstName");
				if(high == null)
				{
					high = "";
				}
				else 
				{
					highScore = highscore.getInt("score");
					high = "   -   " + high + " " + highscore.getString("lastName");
				}	
			}
			statsString = statsString
						+ "JELLYFISH GAME HIGH SCORES\n"
						+ "    Personal\t: " + base.getPersonalHighscore(studentID, 2) + "\n"
						+ "    Class\t: " + classHighScore + classHigh + "\n"
						+ "    School\t: " + highScore + high + "\n\n";
			
			classHighscore = base.getClassHighscore(3);
			highscore = base.getHighscore(3);
			classHigh = "";
			high = "";
			classHighScore = 0;
			highScore = 0;
			if(classHighscore.next())
			{
				classHigh = classHighscore.getString("firstName");
				if(classHigh == null)
				{
					classHigh = "";
				}
				else 
				{
					classHighScore = classHighscore.getInt("score");
					classHigh = "   -   " + classHigh + " " + classHighscore.getString("lastName");
				}
			}
			if(highscore.next())
			{
				high = highscore.getString("firstName");
				if(high == null)
				{
					high = "";
				}
				else 
				{
					highScore = highscore.getInt("score");
					high = "   -   " + high + " " + highscore.getString("lastName");
				}	
			}
			statsString = statsString
						+ "SHARK GAME HIGH SCORES\n"
						+ "    Personal\t: " + base.getPersonalHighscore(studentID, 3) + "\n"
						+ "    Class\t: " + classHighScore + classHigh + "\n"
						+ "    School\t: " + highScore + high + "\n\n";
			
			textArea.setText(statsString);
		}
		catch (SQLException e)
		{	
			System.out.println("Something went wrong when getting stats.");
			e.printStackTrace();
		}
		
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpLayout()
	{
		layout.rowHeights = new int[]{0, 0};
		layout.rowWeights = new double[]{0.0, 1.0};
		layout.columnWidths = new int[]{0, 0};
		layout.columnWeights = new double[]{0.0, 1.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(245, 245, 245));
		
		backButton.setFont(new Font("Arial", Font.PLAIN, 22));
		backButton.setForeground(new Color(70, 130, 180));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
		
		textArea.setFont(new Font("Arial", Font.PLAIN, 18));
		textArea.setBackground(new Color(245, 245, 245));
		textArea.setForeground(new Color(70, 130, 180));
		textArea.setFocusable(false);
		textArea.setEditable(false);
		textArea.setTabSize(4);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.HORIZONTAL;
		gbc_textArea.gridwidth = 2;
		gbc_textArea.anchor = GridBagConstraints.NORTH;
		gbc_textArea.insets = new Insets(20, 100, 20, 100);
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 1;
		
		add(backButton, gbc_backButton);
		add(textArea, gbc_textArea);
	}
	
	private void setUpListeners()
	{
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick)
			{	
				base.returnToLastState();
			}
		});
	}
}
