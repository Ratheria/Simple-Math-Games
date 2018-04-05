/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import adapter.Controller;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Game3 extends JPanel implements KeyListener
{
	private static final long serialVersionUID = 8585306608329719982L;
	private Controller base;
	private SpringLayout theLayout;
	private JLabel shark;
	private Image sharkImg;
	private ImageIcon sharkIcon;
	private Point defaultSharkLocation;
	private Point sharkLocation;
	private Timer timer;
	private Timer displayTime;
	private ActionListener gameRestarter;
	private ActionListener timeDisplayer;
	private int screenWidth;
	private int screenHeight;
	private int movementSpeed;
	private int sharkHeight;
	private int sharkWidth;
	private boolean up;
	private boolean down;
	private boolean right;
	private boolean left;
	private boolean playing;
	private boolean guessed1;
	private boolean guessed2;
	private boolean guessed3;
	private ActionListener screenRefresh;
	private Timer refreshTimer;
	private int frequency;
	private List<String> questionList;
	private JLabel answerLabel1;
	private JLabel answerLabel2;
	private JLabel answerLabel3;
	private JLabel scoreLabel;
	private JLabel timerLabel;
	private String question;
	private int answer;
	private int randomPlacement;
	private int score;
	private static final int GAME_PERIOD = 60;
	private int sec;

	public Game3(Controller base)
	{
		this.base = base;
		frequency = base.getFrequency();
		questionList = base.getEquations();
		theLayout = new SpringLayout();
		question = "";
		answer = 0;
		score = 0;
		timerLabel = new JLabel("Time: " + (GAME_PERIOD / 60) + ":" + (GAME_PERIOD % 60));
		scoreLabel = new JLabel("Score: 0");
		screenWidth = base.frame.getWidth();
		screenHeight = base.frame.getHeight();
		defaultSharkLocation = new Point(30, 110);
		sharkLocation = new Point();
		answerLabel1 = new JLabel();
		answerLabel2 = new JLabel();
		answerLabel3 = new JLabel();
		movementSpeed = 3;
		sharkWidth = (base.frame.getWidth() / 5);
		sharkHeight = (base.frame.getHeight() / 5);

		try
		{
			sharkImg = ImageIO.read(this.getClass().getResourceAsStream("shark.png"));
		}
		catch (IOException ex)
		{
			System.out.println("File \"shark.png\" is missing.");
		}

		sharkImg = sharkImg.getScaledInstance(sharkWidth, sharkHeight, java.awt.Image.SCALE_SMOOTH);
		sharkIcon = new ImageIcon(sharkImg);
		shark = new JLabel(sharkIcon);
		up = false;
		down = false;
		right = false;
		left = false;
		guessed1 = false;
		guessed2 = false;
		guessed3 = false;
		playing = true;

		addKeyListener(this);
		setFocusable(true);
		requestFocus();
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

		answerLabel1.setForeground(new Color(70, 130, 180));
		answerLabel1.setFont(new Font("Arial", Font.BOLD, 30));
		answerLabel2.setForeground(new Color(70, 130, 180));
		answerLabel2.setFont(new Font("Arial", Font.BOLD, 30));
		answerLabel3.setForeground(new Color(70, 130, 180));
		answerLabel3.setFont(new Font("Arial", Font.BOLD, 30));

		answerLabel1.setLocation(screenWidth - 150, 110);
		answerLabel2.setLocation(screenWidth - 150, 230);
		answerLabel3.setLocation(screenWidth - 150, 350);
		answerLabel1.setBounds(answerLabel1.getX(), answerLabel1.getY(), 50, 25);
		answerLabel2.setBounds(answerLabel2.getX(), answerLabel2.getY(), 50, 25);
		answerLabel3.setBounds(answerLabel3.getX(), answerLabel3.getY(), 50, 25);
		
		add(answerLabel1);
		add(answerLabel2);
		add(answerLabel3);
		add(timerLabel);
		add(scoreLabel);
	}

	private void setUpTimers()
	{
		sec = GAME_PERIOD - 1;
		timeDisplayer = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if ((sec % 60) < 10)
				{
					timerLabel.setText("Time: " + (sec / 60) + ":0" + (sec % 60));
				}
				else
				{
					timerLabel.setText("Time: " + (sec / 60) + ":" + (sec % 60));
				}
				sec--;
			}
		};
		displayTime = new Timer(1000, timeDisplayer);
		displayTime.start();
		displayTime.setRepeats(true);

		gameRestarter = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				timer.stop();
				System.out.println("Time's up!");
				JPanel gameOverPanel = new JPanel();
				JOptionPane.showMessageDialog(gameOverPanel, "Your score was " + score + ".", "Time's up!", JOptionPane.PLAIN_MESSAGE);
				base.returnToMenu();
			}
		};
		timer = new Timer(GAME_PERIOD * 1000, gameRestarter);
		timer.setRepeats(false);
		timer.start();

		screenRefresh = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if (playing)
				{
					moveShark();
					repaint();
				}
				else
				{
					refreshTimer.stop();
				}
			}
		};
		refreshTimer = new Timer(20, screenRefresh);
		refreshTimer.start();
		refreshTimer.setRepeats(true);
	}

	private void playGame()
	{
		getQuestion();
		// TODO
		shark = new JLabel(sharkIcon);
		sharkLocation.x = defaultSharkLocation.x;
		sharkLocation.y = defaultSharkLocation.y;
		shark.setText(question);
		add(shark);
		repaint();
		guessed1 = false;
		guessed2 = false;
		guessed3 = false;
		randomPlacement = Controller.rng.nextInt(3);
		for (int i = 0; i < 3; i++)
		{
			int randomAnswer = Controller.rng.nextInt(25);
			while (randomAnswer == answer)
			{
				randomAnswer = Controller.rng.nextInt(25);
			}
			if (i == randomPlacement)
			{
				randomAnswer = answer;
			}
			switch (i)
			{
				case 0:
					answerLabel1.setText("= " + randomAnswer);
					break;
				case 1:
					answerLabel2.setText("= " + randomAnswer);
					break;
				case 2:
					answerLabel3.setText("= " + randomAnswer);
					break;
			}
		}
	}

	private void moveShark()
	{
		if (up)
		{
			if (sharkLocation.y > 0)
			{
				sharkLocation.y -= movementSpeed;
			}
		}
		if (down)
		{
			if (sharkLocation.y < screenHeight - sharkHeight)
			{
				sharkLocation.y += movementSpeed;
			}
		}
		if (right)
		{
			if (sharkLocation.x < screenWidth - sharkWidth)
			{
				sharkLocation.x += movementSpeed;
			}
		}
		if (left)
		{
			if (sharkLocation.x > 0)
			{
				sharkLocation.x -= movementSpeed;
			}
		}
		checkAnswer();
	}

	public void resolveKeyEvent(KeyEvent e, boolean value)
	{
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
		{
			up = value;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
		{
			down = value;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
		{
			left = value;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
		{
			right = value;
		}
	}

	public void keyPressed(KeyEvent e)
	{
		resolveKeyEvent(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		resolveKeyEvent(e, false);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{}

	private void getQuestion()
	{
		int random = Controller.rng.nextInt(10);
		if (frequency > 0 && random <= frequency)
		{
			questionFromList();
		}
		else
		{
			generateQuestion();
		}
		while (answer < 0)
		{
			generateQuestion();
		}
	}

	private void questionFromList()
	{
		int random = Controller.rng.nextInt(questionList.size());
		question = questionList.get(random);
		if (question.contains("+"))
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
		if (random < 1)
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

	private void updateScore(boolean correct)
	{
		if (correct)
		{
			score += 50;
		}
		else if (score > 0)
		{
			score -= 5;
		}
		scoreLabel.setText("Score: " + Integer.toString(score));
	}

	private void checkAnswer()
	{
		int intersect = -1;
		Rectangle sharkBounds = shark.getBounds();
		if (sharkBounds.intersects(answerLabel1.getBounds()) && !guessed1)
		{
			intersect = 0;
			guessed1 = true;
			answerLabel1.setText("");
			// TODO
		}
		else if (sharkBounds.intersects(answerLabel2.getBounds()) && !guessed2)
		{
			intersect = 1;
			guessed2 = true;
			answerLabel2.setText("");
		}
		else if (sharkBounds.intersects(answerLabel3.getBounds()) && !guessed3)
		{
			intersect = 2;
			guessed3 = true;
			answerLabel3.setText("");
		}

		if (intersect == randomPlacement)
		{
			updateScore(true);
			remove(shark);
			playGame();
		}
		else if (intersect != -1)
		{
			updateScore(false);
		}
	}

	@Override
	public void paint(Graphics g)
	{
		requestFocus();
		answerLabel1.setLocation(screenWidth - 150, 110);
		answerLabel2.setLocation(screenWidth - 150, 230);
		answerLabel3.setLocation(screenWidth - 150, 350);
		shark.setLocation(sharkLocation);
		super.paint(g);
	}

}
