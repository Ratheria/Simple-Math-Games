/**
 *	@author Jadie Adams
 *	@author Ariana Fairbanks
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

public class Game1 extends JPanel
{
	private static final long serialVersionUID = -5262708339581599541L;
	@SuppressWarnings("unused")
	private Controller base;
	private Runnable doAddFish;
	private FishObject thisFish;
	private List<FishObject> currentFish = new ArrayList<FishObject>();
	private boolean playing;
	private int maxFishVertical;
	private int frequency;
	private int questionsToAsk;
	private int answer;
	private int score;
	private SpringLayout theLayout;
	private String question;
	private List<String> questionList;
	private JLabel timerLabel;
	private JLabel questionLabel;
	private JLabel scoreLabel;
	private Timer timer;
	private int gamePeriod = 90; //in seconds
	private ActionListener gameRestarter;
	private Timer displayTime;
	private ActionListener timeDisplayer;
	private int sec;
	private Image fishImg;
	private ImageIcon fishIcon;
	private int fishImageWidth;
	private int fishImageHeight;

	public Game1(Controller base) 
	{
		this.base = base;
		playing = true;
		thisFish = null;
		doAddFish = null;
		maxFishVertical = (base.frame.getHeight() - 250)/50;
		frequency = base.getFrequency();
		questionsToAsk = 10;
		answer = 0;
		score = 0;
		theLayout = new SpringLayout();
		question = "Question";
		questionList = base.getEquations();
		timerLabel = new JLabel("Time: "+(gamePeriod/60)+":"+ (gamePeriod%60));
		questionLabel = new JLabel(question);
		scoreLabel = new JLabel("Score: 0");

		//setting up fish icon for answer buttons
		fishImageWidth= (base.frame.getWidth() - 250)/10;
		fishImageHeight = (base.frame.getHeight() - 250)/10;
		try {                
			fishImg = ImageIO.read(new File("fish.png"));
		} catch (IOException ex) {
			System.out.println("File fish.png is missing.");
		}
		fishImg = fishImg.getScaledInstance( fishImageWidth, fishImageHeight,  java.awt.Image.SCALE_SMOOTH ) ;  //resizes fish image
		fishIcon = new ImageIcon(fishImg);

		setUpLayout();
		setUpTimers();
		playGame();
	}

	private void setUpLayout() 
	{
		setLayout(theLayout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(173, 216, 230));
		setBackground(new Color(0, 0, 0));

		timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timerLabel.setForeground(new Color(135, 206, 250));
		timerLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
		theLayout.putConstraint(SpringLayout.NORTH, timerLabel, 25, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.EAST, timerLabel, -50, SpringLayout.EAST, this);

		scoreLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
		scoreLabel.setForeground(new Color(135, 206, 250));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		theLayout.putConstraint(SpringLayout.SOUTH, scoreLabel, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, -50, SpringLayout.EAST, this);

		questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		questionLabel.setForeground(new Color(135, 206, 250));
		questionLabel.setFont(new Font("MV Boli", Font.BOLD, 30));
		theLayout.putConstraint(SpringLayout.WEST, questionLabel, 50, SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.SOUTH, questionLabel, -25, SpringLayout.SOUTH, this);

		add(timerLabel);
		add(scoreLabel);
		add(questionLabel);
	}

	private void setUpTimers(){
		sec = gamePeriod -1;
		timeDisplayer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if((sec%60) < 10)
				{
					timerLabel.setText("Time: "+(sec/60)+ ":0" + (sec%60));
				}
				else
				{
					timerLabel.setText("Time: "+(sec/60)+ ":" + (sec%60));
				}
				sec--;
			}
		};
		displayTime = new Timer(1000, timeDisplayer); //time parameter milliseconds
		displayTime.start();
		displayTime.setRepeats(true);

		gameRestarter = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				base.returnToMenu();
				System.out.println("Times up!");
				JPanel gameOverPanel = new JPanel();
				JOptionPane.showMessageDialog(gameOverPanel, "Your score was " + score + ".", "Times up!", JOptionPane.PLAIN_MESSAGE);
			}
		};
		timer = new Timer(gamePeriod*1000, gameRestarter); //time parameter milliseconds
		timer.setRepeats(false);
		timer.start();
	}

	private void addFish()
	{
		add(thisFish);
	}

	private void playGame()
	{
		//Our 'Game Loop' So Far

		getQuestion();
		int randomPlacement = Controller.rng.nextInt(maxFishVertical);
		for(int i = 0; i < maxFishVertical; i++)
		{
			int fishAnswer = Controller.rng.nextInt(100);
			while (fishAnswer == answer)
			{
				fishAnswer = Controller.rng.nextInt(100);
			}
			if(i == randomPlacement)
			{
				fishAnswer = answer;
			}
			currentFish.add(new FishObject(fishAnswer, i, this.getWidth(), this, fishIcon));
		}
		for(FishObject fish : currentFish)
		{
			add(fish);
		}
		repaint();

		/*
    	doAddFish = new Runnable() 
	    {
            public void run() 
            {
                addFish();
            }
        };
        SwingUtilities.invokeLater(doAddFish);
		 */
	}

	private void getQuestion()
	{
		int random = Controller.rng.nextInt(10);
		if(random <= frequency && questionList != null)
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

	//I changed this because score is a class variable and we don't need a method to change it. -A
	private void updateScore() 
	{	
		scoreLabel.setText("Score: " + Integer.toString(score));	
	}

	private void removeFish(FishObject fish)
	{
		currentFish.remove(fish);
		this.remove(fish);
		repaint();
	}

	private void clearCurrentFish()
	{
		for(FishObject fish : currentFish)
		{
			this.remove(fish);
		}
		currentFish = new ArrayList<FishObject>();
	}

	public void wentOffScreen(FishObject fish)
	{
		if(fish.getAnswer() == answer)
		{
			System.out.println("Correct answer went off screen.");
			clearCurrentFish();
		}
	}

	public void selected(FishObject fish)
	{
		if(fish.getAnswer() == answer)
		{
			System.out.println("Correct answer given.");
			score++;
			updateScore();
			clearCurrentFish();
			playGame();
		}
		else
		{
			System.out.println("Incorrect answer.");
			removeFish(fish);
			//TODO maybe limit the number of times they can answer incorrectly
		}
	}
}
