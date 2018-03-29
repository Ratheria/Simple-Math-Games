/**
 *	@author Walker Flocker
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
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

public class Game3 extends JPanel
{
	private static final long serialVersionUID = 8585306608329719982L;
	private Controller base;
	private SharkObject shark;
	private int frequency;
	private int score;
	private String answer;
	private int gamePeriod;
	private int sec;
	private int sharkImageWidth;
	private int sharkImageHeight;
	private SpringLayout theLayout;
	private String question;
	private int[] answerList;
	private List<String> questionList;
	private JLabel timerLabel;
	private JLabel questionLabel;
	private JLabel scoreLabel;
	private Timer timer;
	private Timer displayTime;
	private ActionListener gameRestarter;
	private ActionListener timeDisplayer;
	private Image sharkImg;
	private ImageIcon sharkIcon;
	private JLabel answerLabel1;
	private JLabel answerLabel2;
	private JLabel answerLabel3;
	private JLabel sharkLabel;

	public Game3(Controller base) 
	{
		this.base = base;
		frequency = base.getFrequency();
		answer = "0";
		gamePeriod = 40; //seconds
		theLayout = new SpringLayout();
		question = "Question";
		questionList = base.getEquations();
		answerList = new int[2];
		timerLabel = new JLabel("Time: "+(gamePeriod/60)+":"+ (gamePeriod % 60));
		questionLabel = new JLabel(question);
		scoreLabel = new JLabel("Score: 0");
		answerLabel1 = new JLabel();
		answerLabel2 = new JLabel();
		answerLabel3 = new JLabel();
		
		// setting up shark icon
		sharkImageWidth= (base.frame.getWidth() - 660);
		sharkImageHeight = (base.frame.getHeight() - 450);
		try 
		{	
			sharkImg = ImageIO.read(new File("shark.png"));
		} 
		catch (IOException ex) 
		{	
			System.out.println("File \"shark.png\" is missing.");
		}
		sharkImg = sharkImg.getScaledInstance(sharkImageWidth, sharkImageHeight, java.awt.Image.SCALE_SMOOTH);  //resizes shark image
		sharkIcon = new ImageIcon(sharkImg);
		sharkLabel = new JLabel(sharkIcon);

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
//		questionLabel.setForeground(new Color(70, 130, 180));
		questionLabel.setForeground(Color.BLACK);
		questionLabel.setFont(new Font("Arial", Font.BOLD, 30));
		questionLabel.setMinimumSize(new Dimension(130, 100));
		questionLabel.setPreferredSize(new Dimension(240, 120));
		questionLabel.setMaximumSize(new Dimension(250, 130));
//		theLayout.putConstraint(SpringLayout.WEST, questionLabel, 50, SpringLayout.WEST, this);
//		theLayout.putConstraint(SpringLayout.SOUTH, questionLabel, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, questionLabel, 45, SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.NORTH, questionLabel, 110, SpringLayout.NORTH, this);
				
		sharkLabel.setHorizontalAlignment(SwingConstants.LEFT);
		theLayout.putConstraint(SpringLayout.WEST, sharkLabel, 10, SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.NORTH, sharkLabel, 90, SpringLayout.NORTH, this);
		
		add(timerLabel);
		add(scoreLabel);
		add(questionLabel);
		add(sharkLabel);
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
		/** TODO
		 * Shark appears on left side of screen
		 * Answers appear on right side of screen
		 * When user uses arrow keys to move shark, repaint screen with shark in new location
		 * Use a grid???
		 * When shark has reached an answer, how do we check for correctness?
		 * What to do for incorrect answer? Re-spawn shark on the left side of screen
		 */

		getQuestion();
		addAnswerLabels();
		shark = new SharkObject(question, answer, this, sharkIcon);
		addShark();
		repaint();
	}
	
	private void addAnswerLabels() {
		generateRandomAnswers();
		
		// TODO randomize the way answers are added to labels
		// TODO add labels to screen
		answerLabel1.setText("= " + Integer.toString(answerList[0]));
		answerLabel2.setText("= " + Integer.toString(answerList[1]));
		answerLabel3.setText("= " + answer);
		
		/**
		// make answerLabel an array, add 3 JLabels to it
		// generate random number and take answer from anserLabel
		int[] temp = new int[3];
		
		for(int i = 0; i < 2; i++) {
			int random = Controller.rng.nextInt(3);
			temp[i] = answerLabel [random];
		}
		*/
		
		/**
		questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		questionLabel.setForeground(new Color(70, 130, 180));
		questionLabel.setFont(new Font("Arial", Font.BOLD, 30));
		theLayout.putConstraint(SpringLayout.WEST, questionLabel, 50, SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.SOUTH, questionLabel, -25, SpringLayout.SOUTH, this);
		*/
		
		answerLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		answerLabel1.setForeground(new Color(70, 130, 180));
		answerLabel1.setFont(new Font("Arial", Font.BOLD, 30));
		theLayout.putConstraint(SpringLayout.EAST, answerLabel1, 0, SpringLayout.EAST, this);
		theLayout.putConstraint(SpringLayout.NORTH, answerLabel1, 110, SpringLayout.NORTH, this);
		
		answerLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		answerLabel2.setForeground(new Color(70, 130, 180));
		answerLabel2.setFont(new Font("Arial", Font.BOLD, 30));
		theLayout.putConstraint(SpringLayout.EAST, answerLabel2, 0, SpringLayout.EAST, this);
		theLayout.putConstraint(SpringLayout.NORTH, answerLabel2, 120, SpringLayout.SOUTH, answerLabel1);
		
		answerLabel3.setHorizontalAlignment(SwingConstants.LEFT);
		answerLabel3.setForeground(new Color(70, 130, 180));
		answerLabel3.setFont(new Font("Arial", Font.BOLD, 30));
		theLayout.putConstraint(SpringLayout.EAST, answerLabel3, 0, SpringLayout.EAST, this);
		theLayout.putConstraint(SpringLayout.NORTH, answerLabel3, 120, SpringLayout.SOUTH, answerLabel2);
		
		add(answerLabel1);
		add(answerLabel2);
		add(answerLabel3);
	}

	// TODO Need to display question on the shark
	private void getQuestion() {
		int random = Controller.rng.nextInt(10);
		if (frequency > 0 && random <= frequency) 
		{
			questionFromList();
		} 
		else 
		{
			generateQuestion();
		}
		while (Integer.parseInt(answer) < 0) 
		{
			// generates a new question if the answer is negative
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
			answer = Integer.toString(firstInteger + secondInteger);
			question = firstInteger + " + " + secondInteger + " = ? ";
		}
		else
		{
			int operator = question.indexOf("-");
			int firstInteger = Integer.parseInt(question.substring(0, operator));
			int secondInteger = Integer.parseInt(question.substring(operator + 1));
			answer = Integer.toString(firstInteger - secondInteger);
			question = firstInteger + " - " + secondInteger + " = ? ";
		}
	}

	private void generateRandomAnswers()
	{
		for(int i = 0; i < 2; i++)
		{
			int random = Controller.rng.nextInt(15);
			answerList[i] = random;
			// System.out.println(answerList[i]);
		}
	}
	
	//TODO also need questions about place value
	private void generateQuestion()
	{
		int random = Controller.rng.nextInt(2);
		if (random < 1) 
		{
			int firstInteger = Controller.rng.nextInt(30);
			int secondInteger = Controller.rng.nextInt(30);
			answer = Integer.toString(firstInteger - secondInteger);
			question = firstInteger + " - " + secondInteger + " = ? ";
		} 
		else 
		{
			int firstInteger = Controller.rng.nextInt(30);
			int secondInteger = Controller.rng.nextInt(30);
			answer = Integer.toString(firstInteger + secondInteger);
			question = firstInteger + " + " + secondInteger + " = ? ";
		}
	}

	private void updateScore() 
	{	
		scoreLabel.setText("Score: " + Integer.toString(score));	
	}

	private void clearShark()
	{
		this.remove(shark);
	}
	
	private void moveShark()
	{
		removeAll();
		setUpLayout();
		addShark();
		revalidate();
		repaint();
	}
	
	private void addShark()
	{
		shark.setFocusPainted(false);
		shark.setContentAreaFilled(false);
		shark.setFont(new Font("Ariel", Font.PLAIN, 20));
		shark.setForeground(Color.WHITE);
		shark.updateLocation();
		shark.setLocation(shark.getXValue(), shark.getYValue());
		add(shark);
	}

	//TODO Modify this to make sure the shark cannot move off the screen
	// reset shark if shark moves off screen
	public void wentOffScreen(SharkObject shark)
	{
		clearShark();
		addShark();
	}
	
	//TODO will be where checking shark location is on the correct answer
	// will have to check if shark went off screen in the same row as the correct answer
	public void selected(SharkObject shark)
	{
		if(shark.getAnswer().equals(answer))
		{
			System.out.println("Correct answer given.");
			score += 10;
			updateScore();
			clearShark();
			playGame();
		}
		else
		{
			System.out.println("Incorrect answer.");
			this.remove(shark);
			repaint();
			score--;
			//TODO maybe limit the number of times they can answer incorrectly
		}
	}
}
