/**
 *	@author Jadie Adams
 *	@author Ariana Fairbanks
 */
package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import adapter.Controller;
import javax.swing.JLabel;
import java.awt.Insets;
import java.util.List;
import java.awt.Canvas;

public class Game1 extends JPanel
{
	private static final long serialVersionUID = -5262708339581599541L;
	private Controller base;
	private String question;
	private int frequency;
	private int answer;
	private int score;
	private List<String> questionList;
	private SpringLayout theLayout;
	private JLabel timer;
	private JLabel questionLabel;
	private String scoreString;
	private JLabel scoreLabel;
	private Canvas canvas;
	
	public Game1(Controller base) 
	{
		this.base = base;
		theLayout = new SpringLayout();
		frequency = base.getFrequency();
		answer = 0;
		score = 0;
		question = "Question";
		questionList = base.getEquations();
		timer = new JLabel("Time: ");
		questionLabel = new JLabel(question);
		scoreString = "Score: 0";
		scoreLabel = new JLabel(scoreString);
		canvas = new Canvas();
		
		setUpLayout();
		setUpListeners();
		playGame();
	}
	
	private void setUpLayout() 
	{
		setLayout(theLayout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(0, 0, 0));
		
		timer.setHorizontalAlignment(SwingConstants.RIGHT);
		timer.setForeground(new Color(135, 206, 250));
		timer.setFont(new Font("MV Boli", Font.PLAIN, 20));
		theLayout.putConstraint(SpringLayout.NORTH, timer, 0, SpringLayout.NORTH, this);

		scoreLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
		scoreLabel.setForeground(new Color(135, 206, 250));
		theLayout.putConstraint(SpringLayout.SOUTH, scoreLabel, 0, SpringLayout.SOUTH, this);
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		questionLabel.setForeground(new Color(135, 206, 250));
		questionLabel.setFont(new Font("MV Boli", Font.BOLD, 30));
		theLayout.putConstraint(SpringLayout.SOUTH, questionLabel, 0, SpringLayout.SOUTH, this);
		
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, 0, SpringLayout.EAST, canvas);
		theLayout.putConstraint(SpringLayout.SOUTH, timer, 0, SpringLayout.NORTH, canvas);
		theLayout.putConstraint(SpringLayout.EAST, timer, 0, SpringLayout.EAST, canvas);
		theLayout.putConstraint(SpringLayout.WEST, questionLabel, 0, SpringLayout.WEST, canvas);
		theLayout.putConstraint(SpringLayout.NORTH, scoreLabel, 0, SpringLayout.SOUTH, canvas);
		theLayout.putConstraint(SpringLayout.NORTH, questionLabel, 0, SpringLayout.SOUTH, canvas);
		theLayout.putConstraint(SpringLayout.NORTH, canvas, 50, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.WEST, canvas, 50, SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.SOUTH, canvas, -100, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.EAST, canvas, -50, SpringLayout.EAST, this);
		canvas.setBackground(new Color(173, 216, 230));

		add(timer);
		add(scoreLabel);
		add(questionLabel);
		add(canvas);
	}
	
	private void setUpListeners() 
	{
	
	}
	
	private void playGame()
	{
		//Our Game Loop
		getQuestion();
		updateScore();
	}
	
	private void getQuestion()
	{
		if(frequency > 0 && Controller.rng.nextInt(10) < frequency && questionList != null)
		{	questionFromList();	}
		else
		{	generateQuestion();	}
		while(answer < 0)
		{
			//generates a new question if the answer is negative
			generateQuestion();
		}
		questionLabel.setText(question);
	}
	
	private void questionFromList()
	{
		//Assumes only a + or - operator
		question = questionList.get(Controller.rng.nextInt(questionList.size()));
		if(question.contains("+"))
		{
			int operator = question.indexOf("+");
			int firstInteger = Integer.parseInt(question.substring(0, operator));
			int secondInteger = Integer.parseInt(question.substring(operator + 1));
			answer = firstInteger + secondInteger;
			question = firstInteger + " + " + secondInteger + " = ?";
		}
		else
		{
			int operator = question.indexOf("-");
			int firstInteger = Integer.parseInt(question.substring(0, operator));
			int secondInteger = Integer.parseInt(question.substring(operator + 1));
			answer = firstInteger - secondInteger;
			question = firstInteger + " - " + secondInteger + " = ?";
		}
	}
	
	private void generateQuestion()
	{
		int random = Controller.rng.nextInt(2);
		if(random < 1)
		{
			int firstInteger = Controller.rng.nextInt(100);
			int secondInteger = Controller.rng.nextInt(100);
			answer = firstInteger - secondInteger;
			question = firstInteger + " - " + secondInteger;
		}
		else
		{
			int firstInteger = Controller.rng.nextInt(100);
			int secondInteger = Controller.rng.nextInt(100);
			answer = firstInteger + secondInteger;
			question = firstInteger + " - " + secondInteger;
		}
	}
	
	//I changed this because we don't need a method call to change the score -A
	private void updateScore() 
	{	
		scoreLabel.setText("Score: " + Integer.toString(score));	
	}
	
}
