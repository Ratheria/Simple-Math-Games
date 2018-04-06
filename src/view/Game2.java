/**
 *	@author Jadie Adams
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import adapter.Controller;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class Game2 extends JPanel implements KeyListener
{
	private static final long serialVersionUID = -5262708339581599541L;
	private Controller base;
	private SpringLayout theLayout;
	private JButton jelly;
	private Timer jellyTimer;
	private Timer displayTime;
	private ActionListener jellyMover;
	private ActionListener timeDisplayer;
	private ArrayList<JLabel> columnLabels;
	private Image jellyImg;
	private ImageIcon jellyIcon;
	private JLabel timerLabel;
	private JLabel scoreLabel;
	private JLabel feedbackLabel;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JButton menu;
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
	private int jellyWidth;
	private int jellyHeight;
	private int xSpacing;
	private int movement;
	private String question;
	private int score;
	private int questionBase;
	private int questionTypes; // TODO
	private int speed;
	
	private int numQuestionsAsked;
	private int questionsAnsweredCorrectly; // TODO
	private int guesses;
	
	private boolean playing;
	private boolean reset;

	// TODO Grid bag layout conversion?

	public Game2(Controller base)
	{
		this.base = base;
		theLayout = new SpringLayout();
		columnLabels = new ArrayList<JLabel>();
		index = 1;
		answerIndex = 0;
		numberOfColumns = 3;
		frequency = base.getFrequency();
		answer = 0;
		score = 0;
		gamePeriod = 40; // seconds
		question = "Question";
		questionList = base.getEquations();
		timerLabel = new JLabel("Time: " + (gamePeriod / 60) + ":" + (gamePeriod % 60));
		scoreLabel = new JLabel("Score: 0");
		feedbackLabel = new JLabel("");
		menu = new JButton(" Exit Game ");
		maxY = base.frame.getHeight();
		xSpacing = (base.frame.getWidth()) / numberOfColumns;
		jellyWidth = xSpacing / 3;
		jellyHeight = xSpacing / 2;
		jellyLocation = new Point();
		try
		{
			jellyImg = ImageIO.read(this.getClass().getResourceAsStream("Jellyfish.png"));
		}
		catch (IOException ex)
		{
			System.out.println("File \"Jellyfish.png\" is missing.");
		}
		jellyImg = jellyImg.getScaledInstance(jellyWidth, jellyHeight, java.awt.Image.SCALE_SMOOTH);
		jellyIcon = new ImageIcon(jellyImg);
		questionBase = 15;
		questionTypes = 0; // both, addition, subtraction
		speed = 40;
		numQuestionsAsked = 0;
		questionsAnsweredCorrectly = 0;

		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		setUpLayout();
		playGame();
		setUpTimers();
		setUpListeners();
	}

	private void playGame()
	{
		index = 1;
		movement = 2;
		jellyLocation.y = 50;
		getQuestion();
		numQuestionsAsked++;
		jelly = new JButton(question, jellyIcon);
		jelly.setVerticalTextPosition(SwingConstants.TOP);
		jelly.setFont(new Font("Tahoma", Font.BOLD, 18));
		jelly.setForeground(new Color(25, 25, 112));
		int randomPlacement = Controller.rng.nextInt(numberOfColumns);
		columnLabels = null;
		columnLabels = new ArrayList<JLabel>();
		for (int i = 0; i < numberOfColumns; i++)
		{
			int columnAnswer = Controller.rng.nextInt(25);
			while (columnAnswer == answer)
			{
				columnAnswer = Controller.rng.nextInt(25);
			}
			if (i == randomPlacement)
			{
				columnAnswer = answer;
				answerIndex = i;
			}
			columnLabels.add(new JLabel(columnAnswer + ""));
		}
		setUpVar();
		playing = true;
		reset = false;
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
		theLayout.putConstraint(SpringLayout.NORTH, timerLabel, 10, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.WEST, timerLabel, 10, SpringLayout.WEST, this);

		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		scoreLabel.setForeground(new Color(70, 130, 180));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		theLayout.putConstraint(SpringLayout.NORTH, scoreLabel, 0, SpringLayout.NORTH, menu);
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, -20, SpringLayout.WEST, menu);
		
		feedbackLabel.setHorizontalAlignment(SwingConstants.LEFT);
		feedbackLabel.setForeground(new Color(70, 130, 180));
		feedbackLabel.setBackground(new Color(245, 245, 245));
		feedbackLabel.setFont(new Font("Arial", Font.BOLD, 35));
		theLayout.putConstraint(SpringLayout.WEST, feedbackLabel, (base.frame.getWidth()/3), SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.NORTH, feedbackLabel, (base.frame.getHeight()/3), SpringLayout.NORTH, this);

		menu.setFont(new Font("Arial", Font.PLAIN, 25));
		menu.setForeground(new Color(70, 130, 180));
		menu.setBackground(new Color(70, 130, 180));
		menu.setFocusPainted(false);
		menu.setContentAreaFilled(false);
		menu.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		theLayout.putConstraint(SpringLayout.NORTH, menu, 10, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.EAST, menu, -10, SpringLayout.EAST, this);

		add(timerLabel);
		add(scoreLabel);
		add(feedbackLabel);
		add(menu);
	}

	private void setUpListeners()
	{
		menu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				playing = false;
				stopTimers();
				removeVar();
				JOptionPane.showMessageDialog(base.messagePanel, "Your score was " + score + ".", "", JOptionPane.PLAIN_MESSAGE);
				base.returnToMenu();
			}
		});
	}

	private void setUpVar()
	{
		int currentX = (xSpacing / 3) + 30;
		label1 = columnLabels.get(0);
		label1.setForeground(new Color(70, 130, 180));
		label1.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.SOUTH, label1, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, label1, currentX, SpringLayout.WEST, this);
		add(label1);
		currentX += xSpacing;
		label2 = columnLabels.get(1);
		label2.setForeground(new Color(70, 130, 180));
		label2.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.SOUTH, label2, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, label2, currentX, SpringLayout.WEST, this);
		add(label2);
		currentX += xSpacing;
		label3 = columnLabels.get(2);
		label3.setForeground(new Color(70, 130, 180));
		label3.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.SOUTH, label3, -25, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, label3, currentX, SpringLayout.WEST, this);
		add(label3);

		jelly.setLocation(jellyLocation);
		jelly.setFocusable(false);
		jelly.setFocusPainted(false);
		jelly.setOpaque(false);
		jelly.setContentAreaFilled(false);
		jelly.setBorderPainted(false);
		jelly.setMargin(new Insets(0, 0, 0, 0));
		jelly.setHorizontalTextPosition(JButton.CENTER);

		add(jelly);
	}

	private void setUpTimers()
	{
		currentTime = gamePeriod - 1;
		timeDisplayer = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (playing)
				{
					if ((currentTime % 60) < 10)
					{
						timerLabel.setText("Time: " + (int) (currentTime / 60) + ":0" + (int) (currentTime % 60));
					}
					else
					{
						timerLabel.setText("Time: " + (int) (currentTime / 60) + ":" + (int) (currentTime % 60));
					}
					currentTime--;
				}
				else if (reset)
				{
					removeVar();
					playGame();
				}
				else if (!playing){
					reset = true;
				}
				if (currentTime == 0)
				{
					displayTime.stop();
					playing = false;
					score -= 5;
					System.out.println("Time's up!");
					JOptionPane.showMessageDialog(base.messagePanel, "Your score was " + score + ".", "Time's up!", JOptionPane.PLAIN_MESSAGE);
					remove(jelly);
					base.returnToMenu();
				}
				
			}
		};
		displayTime = new Timer(1000, timeDisplayer);
		displayTime.start();
		displayTime.setRepeats(true);

		jellyMover = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if (playing)
				{
					jellyLocation.y += movement;
					if (jellyLocation.y >= maxY)
					{
						wentOffScreen();
					}
					repaint();
				}
			}
		};
		jellyTimer = new Timer(speed, jellyMover);
		jellyTimer.start();
		jellyTimer.setRepeats(true);

	}

	private void updateScore(boolean correct)
	{
		// TODO
		scoreLabel.setText("Score: " + score);
	}

	private void getQuestion()
	{
		int random = Controller.rng.nextInt(10);
		if (frequency > 0 && random <= frequency && questionList != null)
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
		feedbackLabel.setText("");
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
		switch (questionTypes)
		{
			case 0:
				int random = Controller.rng.nextInt(2);
				if (random < 1)
				{
					generateAddition();
				}
				else
				{
					generateSubtraction();
				}
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
		playing = false;
		if (index == answerIndex)
		{
			System.out.println("Correct answer given.");
			questionsAnsweredCorrectly++;
			feedbackLabel.setText("Correct!  " + question.substring(0, question.indexOf("?")) + answer);
			repaint();
			score += 50;
		}
		else
		{
			System.out.println("Incorrect answer given.");
			feedbackLabel.setText("Nice try!  " + question.substring(0, question.indexOf("?")) + answer);
			removeVar();
			repaint();
			if (score > 0)
			{
				score -= 5;
			}
		}
		updateScore(index == answerIndex);
	}

	private void removeVar()
	{
		remove(label1);
		remove(label2);
		remove(label3);
		remove(jelly);
		repaint();
	}

	private void updateJellyLocation()
	{
		jellyLocation.x = (index * xSpacing) + jellyWidth;
		jelly.setLocation(jellyLocation);
	}

	public void keyPressed(KeyEvent e)
	{
		if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && index > 0)
		{
			index--;
			repaint();
		}
		if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && index < 2)
		{
			index++;
			repaint();
		}
		if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W))
		{
			movement = 2;
		}
		if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S))
		{
			movement = 8;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{}

	@Override
	public void keyTyped(KeyEvent e)
	{}

	@Override
	public void paint(Graphics g)
	{
		requestFocus();
		updateJellyLocation();
		super.paint(g);;
	}
	
	private void stopTimers()
	{
		displayTime.stop();
		jellyTimer.stop();
	}

}
