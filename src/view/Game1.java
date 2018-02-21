/**
 *	@author Jadie Adams
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import adapter.Controller;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Game1 extends JPanel
{
	private static final long serialVersionUID = -5262708339581599541L;
	private Controller base;
	private GridBagLayout gridBagLayout;
	private JLabel timer;
	private JLabel question;
	
	//use addScore(num) to increase score. Don't just increase score, the JLabel needs to update.
	private int score = 0;
	String scoreString = "Score: 0";
	JLabel labelScore = new JLabel(scoreString);
	
	public void updateScoreString() {
		scoreString = ("Score: " + Integer.toString(score));
	}
	
	public Game1(Controller base) 
	{
		this.base = base;
		gridBagLayout = new GridBagLayout();
		timer = new JLabel("Timer");
		question = new JLabel("Question");
		
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpLayout() 
	{
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gridBagLayout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(0, 0, 0));
		
		timer.setVerticalAlignment(SwingConstants.TOP);
		timer.setHorizontalAlignment(SwingConstants.LEFT);
		timer.setForeground(new Color(135, 206, 250));
		timer.setFont(new Font("MV Boli", Font.PLAIN, 35));
		GridBagConstraints gbc_timer = new GridBagConstraints();
		gbc_timer.insets = new Insets(0, 0, 5, 0);
		gbc_timer.gridx = 2;
		gbc_timer.gridy = 0;
		add(timer, gbc_timer);
		
		updateScoreString();
		labelScore.setFont(new Font("MV Boli", Font.PLAIN, 35));
		labelScore.setForeground(new Color(135, 206, 250));
		GridBagConstraints gbc_labelScore = new GridBagConstraints();
		gbc_labelScore.insets = new Insets(0, 0, 0, 5);
		gbc_labelScore.gridx = 0;
		gbc_labelScore.gridy = 2;
		add(labelScore, gbc_labelScore);
		
		question.setVerticalAlignment(SwingConstants.BOTTOM);
		question.setHorizontalAlignment(SwingConstants.CENTER);
		question.setForeground(new Color(135, 206, 250));
		question.setFont(new Font("MV Boli", Font.PLAIN, 35));
		GridBagConstraints gbc_question = new GridBagConstraints();
		gbc_question.insets = new Insets(0, 0, 0, 5);
		gbc_question.gridx = 1;
		gbc_question.gridy = 2;
		add(question, gbc_question);
	
	}
	// Make sure you add to score using this, don't just increase score, label needs to update.
	private void addScore(int num){
		score += num;
		updateScoreString();
		labelScore.setText(scoreString);
	}
	
	private void setUpListeners() 
	{
	
	}
}
