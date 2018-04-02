/**
 *	@author Jadie Adams
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
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
import adapter.ViewStates;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.awt.Canvas;
import javax.swing.Timer;

public class Game2 extends JPanel implements KeyListener
{
	private static final long serialVersionUID = -5262708339581599541L;
	private Controller base;
	private SpringLayout theLayout;
	private JButton jelly;
	private Timer timeOutCount;
	private Timer jellyTimer;
	private Timer displayTime;
	private ActionListener timeOut;
	private ActionListener jellyMover;
	private ActionListener timeDisplayer;
	private ArrayList<Integer> columnLabels;
	private Image jellyImg;
	private ImageIcon jellyIcon;
	private JLabel timerLabel;
	private JLabel scoreLabel;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private Point jellyLocation;
	private List<String> questionList;
	private double currentTime;
	private int frequency;
	private int answer;
	private int gamePeriod;
	private int index;
	private int answerIndex;
	private int numberOfColumns;
	private int maxY;
	private int xSpacing;
	private int movement;
	private int score;
	private String question;
	private int questionBase;
	private int questionTypes;	//TODO
	private int speed;
	private boolean playing;
	private JButton menu;

	public Game2(Controller base) 
	{
		this.base = base;
		theLayout = new SpringLayout();
		columnLabels = new ArrayList<Integer>();
		movement = 2;
		index = 1;
		answerIndex = 0;
		numberOfColumns = 3;
		frequency = base.getFrequency();
		answer = 0;
		score = 0;
		gamePeriod = 40; //seconds
		question = "Question";
		questionList = base.getEquations();
		timerLabel = new JLabel("Time: "+(gamePeriod/60)+":"+ (gamePeriod%60));
		scoreLabel = new JLabel("Score: 0");
		label1 = new JLabel("");
		label2 = new JLabel("");
		label3 = new JLabel("");
		maxY = base.frame.getHeight();
		xSpacing = (base.frame.getWidth())/numberOfColumns;
		jellyLocation = new Point(xSpacing + 50, 50);
		try 
		{	jellyImg = ImageIO.read(this.getClass().getResourceAsStream("Jellyfish.png"));	} 
		catch (IOException ex) 
		{	System.out.println("File \"Jellyfish.png\" is missing.");	}
		jellyImg = jellyImg.getScaledInstance(xSpacing/3, xSpacing/2,  java.awt.Image.SCALE_SMOOTH );
		jellyIcon = new ImageIcon(jellyImg);
		jelly = new JButton(jellyIcon);
		questionBase = 15;
		questionTypes = 0; //both, addition, subtraction
		speed = 40;
		menu = new JButton(" Return to Menu ");
		
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		playGame();
		setUpLayout();
		setUpTimers();
		setUpListeners();
	}
	
	private void playGame()
	{
		getQuestion();
		newJelly();
		int randomPlacement = Controller.rng.nextInt(numberOfColumns);
		for(int i = 0; i < numberOfColumns; i++)
		{
			int columnAnswer = Controller.rng.nextInt(25);
			while (columnAnswer == answer)
			{	columnAnswer = Controller.rng.nextInt(25);	}
			if(i == randomPlacement)
			{	
				columnAnswer = answer;	
				answerIndex = i;
			}
			columnLabels.add(columnAnswer);
		}
		resetLabels();
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
		
		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		scoreLabel.setForeground(new Color(70, 130, 180));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		theLayout.putConstraint(SpringLayout.NORTH, scoreLabel, 25, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, -50, SpringLayout.EAST, this);
		
		int currentX = xSpacing/2;
		label1 = new JLabel(columnLabels.get(0)+"");
		label1.setForeground(new Color(70, 130, 180));
		label1.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.SOUTH, label1, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, label1, currentX, SpringLayout.WEST, this);
		add(label1);
		currentX += xSpacing;
		label2 = new JLabel(columnLabels.get(1)+"");
		label2.setForeground(new Color(70, 130, 180));
		label2.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.SOUTH, label2, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, label2, currentX, SpringLayout.WEST, this);
		add(label2);
		currentX += xSpacing;
		label3 = new JLabel(columnLabels.get(2)+"");
		label3.setForeground(new Color(70, 130, 180));
		label3.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.SOUTH, label3, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.EAST, label3, currentX, SpringLayout.WEST, this);
		add(label3);
		
    	menu.setFont(new Font("Arial", Font.PLAIN, 25));
		menu.setForeground(new Color(70, 130, 180));
		menu.setBackground(new Color(70, 130, 180));
		menu.setFocusPainted(false);
		menu.setContentAreaFilled(false);
		menu.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		theLayout.putConstraint(SpringLayout.NORTH, menu, 15, SpringLayout.SOUTH, timerLabel);
		theLayout.putConstraint(SpringLayout.WEST, menu, 10, SpringLayout.WEST, this);
		
		add(menu);
		add(timerLabel);
		add(scoreLabel);
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
		
		jellyMover = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{	
				if(playing)
				{	
					jellyLocation.y += movement;
			    	if(jellyLocation.y >= maxY)
			    	{	
			    		wentOffScreen();
			    	}
					repaint();
				}
				else
				{	jellyTimer.stop();	}
			}
		};
		jellyTimer = new Timer(speed, jellyMover);
		jellyTimer.start();
		jellyTimer.setRepeats(true);
		
		timeOut = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				timeOutCount.stop();
				playing = false;
				System.out.println("Time's up!");
				JOptionPane.showMessageDialog(base.messagePanel, "Your score was " + score + ".", "Time's up!", JOptionPane.PLAIN_MESSAGE);
				remove(jelly);
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
	
	private void wentOffScreen()
	{
		if(index == answerIndex)
		{
			System.out.println("Correct answer given.");
			JOptionPane.showMessageDialog(base.messagePanel, question.substring(0, question.indexOf("?")) + " " + answer, "Correct!", JOptionPane.INFORMATION_MESSAGE);
			score += 50;
		}
		else
		{
			System.out.println("Incorrect answer given.");
			JOptionPane.showMessageDialog(base.messagePanel, question.substring(0, question.indexOf("?")) + " " + answer, "Incorrect", JOptionPane.INFORMATION_MESSAGE);
		}
		updateScore(index == answerIndex);
		playing = false;
		playGame();
	}
	
	private void updateJellyLocation()
	{
		jellyLocation.x = (index * xSpacing) + 50;
		jelly.setLocation(jellyLocation);
	}
	
	public void keyPressed(KeyEvent e) 
	{
		if((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && index > 0) 
		{	
			index--;	
			repaint();
		}
		if((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)	&& index < 2) 
		{	
			index++;	
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{	}

	@Override
	public void keyTyped(KeyEvent e) 
	{	}

	@Override
	public void paint(Graphics g)
	{
		requestFocus();
		updateJellyLocation();
		super.paint(g);;
	}
	
	private void setUpListeners() 
	{	
		menu.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick) 
			{	base.changeState(ViewStates.studentMenu);	}
		});
	}
	
	private void updateScore(boolean correct) 
	{	
		scoreLabel.setText("Score: " + Integer.toString(score));	
	}
	
	private void newJelly(){
		jellyLocation = new Point(xSpacing + 50, 50);
		jelly.setLocation(jellyLocation);
		jelly.setFocusable(false);
		jelly.setDisabledIcon(jellyIcon);
		jelly.setEnabled(false);
		jelly.setFocusPainted(false);
    	jelly.setOpaque(false);
    	jelly.setContentAreaFilled(false);
    	jelly.setBorderPainted(false);
    	jelly.setText(question);
    	add(jelly);
	}
	
	private void resetLabels(){
		System.out.println("New labels: " + columnLabels.get(0));
		System.out.println(columnLabels.get(1));
		System.out.println(columnLabels.get(2));
		label1.setText(columnLabels.get(0)+"");
		label2.setText(columnLabels.get(1)+"");
		label3.setText(columnLabels.get(2)+"");
	}
	
}
