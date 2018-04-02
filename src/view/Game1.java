/**
 * @author Ariana Fairbanks
 * @author Jadie Adams
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import adapter.Controller;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class Game1 extends JPanel
{
	private static final long serialVersionUID = -5262708339581599541L;
	private Controller base;
	private ArrayList<FishObject> currentFish;
	private int width;
	private int height;
	private int maxFishVertical;
	private int frequency;
	private int answer;
	private int score;
	private int gamePeriod;
	private double currentTime;
	private int fishImageWidth;
	private int fishImageHeight;
	private SpringLayout theLayout;
	private String question;
	private List<String> questionList;
	private JLabel timerLabel;
	private JLabel questionLabel;
	private JLabel scoreLabel;
	private Timer timeOutCount;
	private Timer fishTimer;
	private Timer displayTime;
	private ActionListener timeOut;
	private ActionListener fishMover;
	private ActionListener timeDisplayer;
	private Image fishImg;
	private ImageIcon fishIcon;
	private int questionBase;
	private int questionTypes;	//TODO
	private int fishSpeed;
	private boolean playing;
	
	public Game1(Controller base) 
	{
		this.base = base;
		currentFish = new ArrayList<FishObject>();
		width = base.frame.getWidth();
		height = base.frame.getHeight();
		maxFishVertical = (height - 250)/60;
		frequency = base.getFrequency();
		answer = 0;
		score = 0;
		gamePeriod = 40; //seconds
		theLayout = new SpringLayout();
		question = "Question";
		questionList = base.getEquations();
		timerLabel = new JLabel("Time: "+(gamePeriod/60)+":"+ (gamePeriod%60));
		questionLabel = new JLabel(question);
		questionLabel.setBackground(new Color(245, 245, 245));
		scoreLabel = new JLabel("Score: 0");
		fishImageWidth = (width - 250)/8;
		fishImageHeight = (height - 250)/5;
		try 
		{	fishImg = ImageIO.read(this.getClass().getResourceAsStream("fish.png"));	} 
		catch (IOException ex){	System.out.println("File fish.png is missing.");	}
		fishImg = fishImg.getScaledInstance(fishImageWidth, fishImageHeight,  java.awt.Image.SCALE_SMOOTH );
		fishIcon = new ImageIcon(fishImg);
		questionBase = 15;
		questionTypes = 0; //both, addition, subtraction
		fishSpeed = 40;
		
		this.setDoubleBuffered(true);
		playGame();
		setUpLayout();
		setUpTimers();
	}

	private void playGame()
	{
		getQuestion();
		int randomPlacement = Controller.rng.nextInt(maxFishVertical);
		for(int i = 0; i < maxFishVertical; i++)
		{
			int fishAnswer = Controller.rng.nextInt(25);
			while (fishAnswer == answer)
			{	fishAnswer = Controller.rng.nextInt(25);	}
			if(i == randomPlacement)
			{	fishAnswer = answer;	}
			currentFish.add(new FishObject(fishAnswer, i, this, fishIcon));
		}
		playing = true;
	}
	
	private void setUpLayout() 
	{
		setLayout(theLayout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setBackground(new Color(213, 248, 255));
		setBackground(new Color(208, 243, 255));

		timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timerLabel.setForeground(new Color(70, 130, 180));
		timerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.NORTH, timerLabel, 25, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.EAST, timerLabel, -50, SpringLayout.EAST, this);

		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		scoreLabel.setForeground(new Color(70, 130, 180));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		theLayout.putConstraint(SpringLayout.SOUTH, scoreLabel, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, -50, SpringLayout.EAST, this);

		questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		questionLabel.setForeground(new Color(70, 130, 180));
		questionLabel.setFont(new Font("Arial", Font.BOLD, 30));
		theLayout.putConstraint(SpringLayout.WEST, questionLabel, 50, SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.NORTH, questionLabel, 25, SpringLayout.NORTH, this);

		add(timerLabel);
		add(scoreLabel);
		add(questionLabel);
		addFish();
	}
	
	private void setUpTimers()
	{
		currentTime = gamePeriod - 1;
		timeDisplayer = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				if(playing)
				{
					if((currentTime % 60) < 10)
					{	timerLabel.setText("Time: " + (int)(currentTime/60)+ ":0" + (int)(currentTime%60));	}
					else
					{	timerLabel.setText("Time: " + (int)(currentTime/60)+ ":" + (int)(currentTime%60));	}
					currentTime--;
				}
				else
				{	
					timerLabel.setText("Time: 0:00");
					displayTime.stop();
				}
			}
		};
		displayTime = new Timer(1000, timeDisplayer); //time parameter milliseconds
		displayTime.start();
		displayTime.setRepeats(true);
		
		fishMover = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{	
				if(playing)
				{	moveFish();	}
				else
				{	fishTimer.stop();	}
			}
		};
		fishTimer = new Timer(fishSpeed, fishMover);
		fishTimer.start();
		fishTimer.setRepeats(true);
		
		timeOut = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				timeOutCount.stop();
				playing = false;
				System.out.println("Time's up!");
				JOptionPane.showMessageDialog(base.messagePanel, "Your score was " + score + ".", "Time's up!", JOptionPane.PLAIN_MESSAGE);
				clearCurrentFish();
				base.returnToMenu();
			}
		};
		timeOutCount = new Timer(gamePeriod * 1000, timeOut);
		timeOutCount.setRepeats(false);
		timeOutCount.start();
	}

	private void getQuestion()
	{
		int random = Controller.rng.nextInt(10);
		if(frequency > 0 && random <= frequency && questionList != null)
		{	questionFromList();	}
		else
		{	generateQuestion();	}
		while(answer < 0)
		{	generateQuestion();	}
		questionLabel.setText(question);
	}

	private void questionFromList()
	{
		int random = Controller.rng.nextInt(questionList.size());
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
		switch(questionTypes)
		{
			case 0:
				int random = Controller.rng.nextInt(2);
				if(random < 1)
				{	generateAddition();	}
				else
				{	generateSubtraction();	}
				break;
			case 1:
				generateAddition();
				break;
			case 2:
				generateSubtraction();
				break;
		}
	}

	private void generateAddition()
	{
		int firstInteger = Controller.rng.nextInt(questionBase);
		int secondInteger = Controller.rng.nextInt(questionBase);
		answer = firstInteger - secondInteger;
		question = firstInteger + " - " + secondInteger + " = ? ";
	}
	
	private void generateSubtraction()
	{
		int firstInteger = Controller.rng.nextInt(questionBase);
		int secondInteger = Controller.rng.nextInt(questionBase);
		answer = firstInteger + secondInteger;
		question = firstInteger + " + " + secondInteger + " = ? ";
	}
	
	private void updateScore(boolean correct) 
	{	
		//TODO
		scoreLabel.setText("Score: " + Integer.toString(score));	
	}

	private void removeFish(FishObject fish)
	{
		currentFish.remove(fish);
		this.remove(fish);
	}

	private void clearCurrentFish()
	{
		for(FishObject fish : currentFish)
		{	this.remove(fish);	}
		currentFish = new ArrayList<FishObject>();
		repaint();
	}
	
	private void moveFish()
	{	
		for(FishObject fish : currentFish)
		{	
			fish.updateFishLocation();	
			repaint(fish.getBounds());
		}
	}
	
	private void addFish()
	{
		for(FishObject fish : currentFish)
		{
	  		fish.setFocusPainted(false);
			fish.setFont(new Font("Ariel", Font.PLAIN, 20));
			fish.setForeground(Color.WHITE);
	    	add(fish);
		}
	}

	private void refreshFishLocation()
	{
		for(FishObject fish : currentFish)
		{
	    	fish.setLocation(fish.getXValue(), fish.getYValue());
		}
	}
	
	public void fishWentOffScreen(FishObject fish)
	{
		if(fish.getAnswer() == answer)
		{
			System.out.println("Correct answer went off screen.");
			playing = false;
			timeOutCount.stop();
			JOptionPane.showMessageDialog(base.messagePanel, "The correct answer went off screen.\nYour score was " + score + ".", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			clearCurrentFish();
			base.returnToMenu();
		}
	}

	public void fishWasSelected(FishObject fish)
	{
		if(fish.getAnswer() == answer)
		{
			System.out.println("Correct answer given.");
			score += 50;
			updateScore(true);
			clearCurrentFish();
			playGame();
		}
		else
		{
			System.out.println("Incorrect answer given.");
			removeFish(fish);
			score -= 5;
			updateScore(false);
		}
	}
	
	@Override 
	public void paint(Graphics g)
	{
		refreshFishLocation();
		super.paint(g);
	}
	
}
