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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class Game2 extends JPanel implements KeyListener
{
	private static final long serialVersionUID = -5262708339581599541L;
	@SuppressWarnings("unused")
	private Controller base;
	private JellyfishObject currentJelly;
	private int answer;
	private int frequency;
	private int score;
	private int gamePeriod;
	private int sec;
	private SpringLayout theLayout;
	private String question;
	private List<String> questionList;
	private JLabel timerLabel;
	private JLabel scoreLabel;
	private Timer timer;
	private Timer displayTime;
	private ActionListener gameRestarter;
	private ActionListener timeDisplayer;
	private Image JellyImg;
	private ImageIcon JellyIcon;
	private int JellyImageWidth;
	private int JellyImageHeight;
	private List<Integer> answers = new ArrayList<Integer>();
	private JLabel answer1Label;
	private JLabel answer2Label;
	private JLabel answer3Label;
	private boolean left;
	private boolean right;	

	public Game2(Controller base) 
	{
		this.base = base;
		
		this.addKeyListener(this);
	    this.setFocusable(true);
	    this.requestFocus();
	    
		frequency = base.getFrequency();
		answer = 0;
		score = 0;
		gamePeriod = 60; //seconds
		theLayout = new SpringLayout();
		question = "Question";
		questionList = base.getEquations();
		timerLabel = new JLabel("Time: "+(gamePeriod/60)+":"+ (gamePeriod%60));
		scoreLabel = new JLabel("Score: 0");
		answer1Label = new JLabel("");
		answer2Label = new JLabel("");
		answer3Label = new JLabel("");
		left = false;
		right = false;
		
		//setting up jellyfish icon for 
		JellyImageWidth= (base.frame.getWidth() - 250)/4;
		JellyImageHeight = (base.frame.getHeight() - 250)/2;
		try 
		{	JellyImg = ImageIO.read(new File("Jellyfish.png"));	} 
		catch (IOException ex) 
		{	System.out.println("File fish.png is missing.");	}
		JellyImg =JellyImg.getScaledInstance( JellyImageWidth, JellyImageHeight,  java.awt.Image.SCALE_SMOOTH ) ;  //resizes fish image
		JellyIcon = new ImageIcon(JellyImg);

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
		scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		theLayout.putConstraint(SpringLayout.NORTH, scoreLabel, 25, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.WEST, scoreLabel, 50, SpringLayout.WEST, this);
		
		answer1Label.setFont(new Font("Arial", Font.PLAIN, 30));
		answer1Label.setForeground(new Color(70, 130, 180));
		answer1Label.setHorizontalAlignment(SwingConstants.LEFT);
		theLayout.putConstraint(SpringLayout.SOUTH, answer1Label, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, answer1Label, 150, SpringLayout.WEST, this);
		
		answer2Label.setFont(new Font("Arial", Font.PLAIN, 30));
		answer2Label.setForeground(new Color(70, 130, 180));
		answer2Label.setHorizontalAlignment(SwingConstants.LEFT);
		theLayout.putConstraint(SpringLayout.SOUTH, answer2Label, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, answer2Label, 200, SpringLayout.EAST, answer1Label);
		
		answer3Label.setFont(new Font("Arial", Font.PLAIN, 30));
		answer3Label.setForeground(new Color(70, 130, 180));
		answer3Label.setHorizontalAlignment(SwingConstants.LEFT);
		theLayout.putConstraint(SpringLayout.SOUTH, answer3Label, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, answer3Label, 200, SpringLayout.EAST, answer2Label);
		
		add(answer1Label);
		add(answer2Label);
		add(answer3Label);
		add(timerLabel);
		add(scoreLabel);
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
				moveJelly();
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
		getQuestion();
		currentJelly = new JellyfishObject(question, answer, this, JellyIcon);
		int randomPlacement = Controller.rng.nextInt(3);
		for(int i = 0; i < 3; i++)
		{
			int jellyAnswer = Controller.rng.nextInt(100);
			while (jellyAnswer == answer)
			{	jellyAnswer = Controller.rng.nextInt(100);	}
			if(i == randomPlacement)
			{	jellyAnswer = answer;	}
			answers.add(jellyAnswer);
		}
		answer1Label = new JLabel(" " + answers.get(0));
		answer2Label = new JLabel(" " + answers.get(1));
		answer3Label = new JLabel(" " + answers.get(2));
		addJelly();
		repaint();
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
		//questionLabel.setText(question);
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

	private void addJelly(){
		currentJelly.setFocusPainted(false);
		currentJelly.updateLocation();
    	currentJelly.setLocation(currentJelly.getXValue(), currentJelly.getYValue());
		add(currentJelly);
	}


	public void moveJelly(){
		removeAll();
		setUpLayout();
		addJelly();
		revalidate();
		repaint();
	}

	public void wentOffScreen(JellyfishObject jelly)
	{
		if(jelly.getAnswer() == answer)
		{
			System.out.println("Jellyfish went off screen.");
			JPanel messagePanel = new JPanel();
			JOptionPane.showMessageDialog(messagePanel, "The correct answer was " + answer, "Good try!", JOptionPane.INFORMATION_MESSAGE);
			playGame();		
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
	}
	public void keyTyped(KeyEvent arg0) {
		System.out.println("key recognized");

	}
	
	public boolean getRight(){
		return right;
	}
	public boolean getLeft(){
		return left;	}
}
