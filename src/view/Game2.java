/**
 *	@author Jadie Adams
 *
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import adapter.Controller;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.awt.Canvas;
import javax.swing.Timer;

public class Game2 extends JPanel
{
	private static final long serialVersionUID = -5262708339581599541L;
	@SuppressWarnings("unused")
	private Controller base;
	private int answer;
	private int frequency;
	private int score;
	private int gamePeriod;
	private int sec;
	private SpringLayout theLayout;
	private String question;
	private List<String> questionList;
	private JLabel timerLabel;
	private JLabel questionLabel;
	private JLabel scoreLabel;
	private Timer timer;
	private Timer displayTime;
	private ActionListener gameRestarter;
	private ActionListener timeDisplayer;

	public Game2(Controller base) 
	{
		this.base = base;
		frequency = base.getFrequency();
		answer = 0;
		score = 0;
		gamePeriod = 60; //seconds
		theLayout = new SpringLayout();
		question = "Question";
		questionList = base.getEquations();
		timerLabel = new JLabel("Time: "+(gamePeriod/60)+":"+ (gamePeriod%60));
		questionLabel = new JLabel(question);
		scoreLabel = new JLabel("Score: 0");

		setUpLayout();
		setUpTimers();
		playGame();
	}

	private void setUpLayout() 
	{
		setLayout(theLayout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(173, 216, 230));
		setBackground(new Color(245, 245, 245));

		timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timerLabel.setForeground(new Color(70, 130, 180));
		timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		theLayout.putConstraint(SpringLayout.NORTH, timerLabel, 25, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.EAST, timerLabel, -50, SpringLayout.EAST, this);

		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		scoreLabel.setForeground(new Color(70, 130, 180));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		theLayout.putConstraint(SpringLayout.SOUTH, scoreLabel, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, -50, SpringLayout.EAST, this);

		questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		questionLabel.setForeground(new Color(70, 130, 180));
		questionLabel.setFont(new Font("Arial", Font.BOLD, 30));
		theLayout.putConstraint(SpringLayout.WEST, questionLabel, 50, SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.SOUTH, questionLabel, -25, SpringLayout.SOUTH, this);

		add(timerLabel);
		add(scoreLabel);
		add(questionLabel);
	}

	private void setUpTimers()
	{
		sec = gamePeriod -1;
		timeDisplayer = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				if((sec%60) < 10)
				{
					timerLabel.setText("Time: "+(sec/60)+ ":0" + (sec%60));
				}
				else
				{
					timerLabel.setText("Time: "+(sec/60)+ ":" + (sec%60));
				}
				sec--;
				//moveFish();
			}
		};
		displayTime = new Timer(1000, timeDisplayer); //time parameter milliseconds
		displayTime.start();
		displayTime.setRepeats(true);

		gameRestarter = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				base.returnToMenu();
				System.out.println("Time's up!");
				JPanel gameOverPanel = new JPanel();
				JOptionPane.showMessageDialog(gameOverPanel, "Your score was " + score + ".", "Time's up!", JOptionPane.PLAIN_MESSAGE);
			}
		};
		timer = new Timer(gamePeriod*1000, gameRestarter); //time parameter milliseconds
		timer.setRepeats(false);
		timer.start();
	}

	private void playGame()
	{
	}

	private void getQuestion()
	{
		int random = Controller.rng.nextInt(10);
		if(frequency > 0 && random <= frequency && questionList != null)
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
		int random = Controller.rng.nextInt(questionList.size());
		System.out.println(random);
		question = questionList.get(random);
		if(question.contains("+"))
		{
			int operator = question.indexOf("+");
			int firstInteger = Integer.parseInt(question.substring(0, operator));
			int secondInteger = Integer.parseInt(question.substring(operator + 1));
			answer = firstInteger + secondInteger;
			question = firstInteger + " + " + secondInteger + " = ? ";
		}
		else
		{
			int operator = question.indexOf("-");
			int firstInteger = Integer.parseInt(question.substring(0, operator));
			int secondInteger = Integer.parseInt(question.substring(operator + 1));
			answer = firstInteger - secondInteger;
			question = firstInteger + " - " + secondInteger + " = ? ";
		}
	}

	private void generateQuestion()
	{
		int random = Controller.rng.nextInt(2);
		if(random < 1)
		{
			int firstInteger = Controller.rng.nextInt(30);
			int secondInteger = Controller.rng.nextInt(30);
			answer = firstInteger - secondInteger;
			question = firstInteger + " - " + secondInteger + " = ? ";
		}
		else
		{
			int firstInteger = Controller.rng.nextInt(30);
			int secondInteger = Controller.rng.nextInt(30);
			answer = firstInteger + secondInteger;
			question = firstInteger + " + " + secondInteger + " = ? ";
		}
	}

	private void updateScore() 
	{	
		scoreLabel.setText("Score: " + Integer.toString(score));	
	}

	

	public void wentOffScreen(FishObject fish)
	{
		if(fish.getAnswer() == answer)
		{
			System.out.println("Correct answer went off screen.");
			timer.stop();
			//clearCurrentFish();
			base.returnToMenu();
			JPanel messagePanel = new JPanel();
			JOptionPane.showMessageDialog(messagePanel, "The correct answer went off screen.\nYour score was " + score + ".", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void selected(FishObject fish)
	{
		if(fish.getAnswer() == answer)
		{
			System.out.println("Correct answer given.");
			score += 10;
			updateScore();
			//clearCurrentFish();
			playGame();
		}
		else
		{
			System.out.println("Incorrect answer.");
			//removeFish(fish);
			score--;
			//TODO maybe limit the number of times they can answer incorrectly
		}
	}
}
