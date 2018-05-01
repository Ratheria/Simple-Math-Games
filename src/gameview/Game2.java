/**
 *	@author Jadie Adams
 *	@author Ariana Fairbanks
 */

package gameview;

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

public class Game2 extends JPanel implements KeyListener, Game
{
	private static final long serialVersionUID = -5262708339581599541L;
	private Controller base;
	private Game thisGame;
	private SpringLayout theLayout;
	private JButton jelly;
	private Timer jellyTimer;
	private Timer displayTime;
	private ActionListener jellyMover;
	private ActionListener timeDisplayer;
	private ArrayList<JLabel> columnLabels;
	private Image jellyImg;
	private ImageIcon jellyIcon;
	private Image chestImg;
	private ImageIcon chestIcon;
	private Image fullChestImg;
	private ImageIcon fullChestIcon;
	private Image emptyChestImg;
	private ImageIcon emptyChestIcon;
	private JLabel timerLabel;
	private JLabel scoreLabel;
	private JLabel feedbackLabel;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JButton menu;
	private JButton help;
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
	private int chestWidth;
	private int chestHeight;
	private int xSpacing;
	private int movement;
	private String question;
	private int score;
	private int questionBase;
	private int questionTypes; // TODO
	private int speed;
	private Image backgroundImg;
	private JLabel background;
	private int questionsAnswered;
	private int questionsCorrect; // TODO
	private int guesses;
	private boolean playing;
	private boolean reset;
	private boolean focus;

	public Game2(Controller base)
	{
		this.base = base;
		thisGame = this;
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
		menu = new JButton(" Exit ");
		help = new JButton(" Help ");
		maxY = base.frame.getHeight();
		xSpacing = (base.frame.getWidth()) / numberOfColumns;
		jellyLocation = new Point();		
		questionBase = 15;
		questionTypes = base.questionTypes; // both, addition, subtraction
		speed = 40;
		questionsAnswered = 0;
		questionsCorrect = 0;
		focus = true;

		try
		{
			backgroundImg = ImageIO.read(this.getClass().getResourceAsStream("background.jpg"));
			jellyImg = ImageIO.read(this.getClass().getResourceAsStream("Jellyfish.png"));
			chestImg = ImageIO.read(this.getClass().getResourceAsStream("chest_closed.png"));
			fullChestImg = ImageIO.read(this.getClass().getResourceAsStream("chest_full.png"));
			emptyChestImg = ImageIO.read(this.getClass().getResourceAsStream("chest_empty.png"));
		}
		catch (IOException ex){}
		backgroundImg = backgroundImg.getScaledInstance(base.frame.getWidth(), base.frame.getHeight(), java.awt.Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(backgroundImg));
		jellyWidth = (int)(xSpacing / 2.5);
		jellyHeight = (int) (xSpacing / 1.75);
		jellyImg = jellyImg.getScaledInstance(jellyWidth, jellyHeight, java.awt.Image.SCALE_SMOOTH);
		jellyIcon = new ImageIcon(jellyImg);
		chestWidth = jellyWidth*2;
		chestHeight = (int) (jellyWidth * 1.5);
		chestImg = chestImg.getScaledInstance(chestWidth, chestHeight, java.awt.Image.SCALE_SMOOTH);
		chestIcon = new ImageIcon(chestImg);
		fullChestImg = fullChestImg.getScaledInstance(chestWidth, chestHeight, java.awt.Image.SCALE_SMOOTH);
		fullChestIcon = new ImageIcon(fullChestImg);
		emptyChestImg = emptyChestImg.getScaledInstance(chestWidth, chestHeight, java.awt.Image.SCALE_SMOOTH);
		emptyChestIcon = new ImageIcon(emptyChestImg);
		
		addKeyListener(this);
		setFocusable(true);
		focus();
		setUpLayout();
		playGame();
		setUpTimers();
		setUpListeners();
		instructionsHandling();
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

		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		scoreLabel.setForeground(new Color(70, 130, 180));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		theLayout.putConstraint(SpringLayout.NORTH, scoreLabel, 0, SpringLayout.NORTH, menu);
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, -20, SpringLayout.WEST, help);
		
		feedbackLabel.setHorizontalAlignment(SwingConstants.LEFT);
		feedbackLabel.setForeground(new Color(20, 20, 217));
		feedbackLabel.setFont(new Font("Arial", Font.BOLD, 35));
		feedbackLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		feedbackLabel.setVerticalTextPosition(SwingConstants.TOP);
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
		
		help.setFont(new Font("Arial", Font.PLAIN, 25));
		help.setForeground(new Color(70, 130, 180));
		help.setBackground(new Color(70, 130, 180));
		help.setFocusPainted(false);
		help.setContentAreaFilled(false);
		help.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		theLayout.putConstraint(SpringLayout.NORTH, help, 0, SpringLayout.NORTH, menu);
		theLayout.putConstraint(SpringLayout.EAST, help, -20, SpringLayout.WEST, menu);
		
		add(timerLabel);
		add(scoreLabel);
		add(feedbackLabel);
		add(menu);
		add(help);
	}
	
	private void playGame()
	{
		index = 1;
		movement = 2;
		jellyLocation.y = 20;
		getQuestion();
		jelly = new JButton(question, jellyIcon);
		jelly.setVerticalTextPosition(SwingConstants.TOP);
		jelly.setFont(new Font("Tahoma", Font.BOLD, 25));
		jelly.setForeground(new Color(20, 20, 217));
		int randomPlacement = Controller.rng.nextInt(numberOfColumns);
		columnLabels = null;
		columnLabels = new ArrayList<JLabel>();
		ArrayList<Integer> answerOptions = new ArrayList<Integer>();
		for (int i = 0; i < numberOfColumns; i++)
		{
			int columnAnswer = Controller.rng.nextInt(questionBase);
			while (columnAnswer == answer || answerOptions.contains(columnAnswer))
			{
				columnAnswer = Controller.rng.nextInt(questionBase);
			}
			if (i == randomPlacement)
			{
				columnAnswer = answer;
				answerIndex = i;
			}
			answerOptions.add(columnAnswer);
			columnLabels.add(new JLabel(" " + columnAnswer, chestIcon, JLabel.CENTER));
		}
		setUpVar();
		add(background);
		playing = true;
		reset = false;
	}

	private void setUpListeners()
	{
		menu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				stopTimers();
				playing = false;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Your score is " + score + ".  Would you like to exit the game?","Exit game?",JOptionPane.OK_CANCEL_OPTION);
				if(dialogResult == JOptionPane.OK_OPTION)
				{
					if(questionsAnswered > 0)
					{
						base.addGameRecord(2, questionsAnswered, questionsCorrect, guesses, (int) (gamePeriod - currentTime), score);
					}
					base.returnToMenu();
					removeVar();
				}
				else
				{
					startTimers();
					playing = true;
				}
			}
		});
		help.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				new InstructionPanel(base, 2, base.getInstructionPreference("game2Instructions"), thisGame);
			}
		});
	}

	private void setUpVar()
	{		
		int currentX = (xSpacing / 3) - 50; //was  +30
		label1 = columnLabels.get(0);
		label1.setForeground(new Color(255, 255, 255));
		label1.setFont(new Font("Arial", Font.PLAIN, 35));
		label1.setVerticalTextPosition(JLabel.CENTER);
		label1.setHorizontalTextPosition(JLabel.CENTER);
		theLayout.putConstraint(SpringLayout.SOUTH, label1, 0, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, label1, currentX, SpringLayout.WEST, this);
		add(label1);
		currentX += xSpacing;
		label2 = columnLabels.get(1);
		label2.setForeground(new Color(255, 255, 255));
		label2.setFont(new Font("Arial", Font.PLAIN, 35));
		label2.setVerticalTextPosition(JLabel.CENTER);
		label2.setHorizontalTextPosition(JLabel.CENTER);
		theLayout.putConstraint(SpringLayout.SOUTH, label2, 0, SpringLayout.SOUTH, this);
		theLayout.putConstraint(SpringLayout.WEST, label2, currentX, SpringLayout.WEST, this);
		add(label2);
		currentX += xSpacing;
		label3 = columnLabels.get(2);
		label3.setForeground(new Color(255, 255, 255));
		label3.setFont(new Font("Arial", Font.PLAIN, 35));
		label3.setVerticalTextPosition(JLabel.CENTER);
		label3.setHorizontalTextPosition(JLabel.CENTER);
		theLayout.putConstraint(SpringLayout.SOUTH, label3, 0, SpringLayout.SOUTH, this);
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
				else if (!playing)
				{
					reset = true;
				}
				if (currentTime == 0)
				{
					stopTimers();
					playing = false;
					System.out.println("Time's up!");
					JOptionPane.showMessageDialog(base.messagePanel, "Your score was " + score + ".", "Time's up!", JOptionPane.PLAIN_MESSAGE);
					if(questionsAnswered > 0)
					{
						base.addGameRecord(2, questionsAnswered, questionsCorrect, guesses, gamePeriod, score);
					}
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
					if (jellyLocation.y >= maxY - chestWidth)
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
	
	private void instructionsHandling()
	{
		boolean showInstructions = base.getInstructionPreference("game2Instructions");
		if(showInstructions)
		{
			new InstructionPanel(base, 2, showInstructions, this);
		}
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
		feedbackLabel.setText("");
		feedbackLabel.setIcon(null);
		while (question.contains("+") && questionTypes == 2)
		{
			generateSubtraction();
		}
		while( question.contains("-") && questionTypes == 1)
		{
			generateAddition();
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
		switch (questionTypes)
		{
			case 0:
				int random = Controller.rng.nextInt(2);
				if (random < 1)
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
		answer = firstInteger + secondInteger;
		question = firstInteger + " + " + secondInteger + " = ? ";
	}

	private void generateSubtraction()
	{
		int firstInteger = Controller.rng.nextInt(questionBase);
		int secondInteger = Controller.rng.nextInt(questionBase);
		answer = firstInteger - secondInteger;
		question = firstInteger + " - " + secondInteger + " = ? ";
	}

	private void wentOffScreen()
	{
		questionsAnswered++;
		playing = false;
		if (index == answerIndex)
		{
			System.out.println("Correct answer given.");
			questionsCorrect++;
			feedbackLabel.setText("Correct!  " + question.substring(0, question.indexOf("?")) + answer);
			feedbackLabel.setIcon(fullChestIcon);
			removeVar();
			repaint();
			score += 50;
		}
		else
		{
			System.out.println("Incorrect answer given.");
			feedbackLabel.setText("Oops,  " + question.substring(0, question.indexOf("?")) + answer);
			feedbackLabel.setIcon(emptyChestIcon);
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

	private void focus()
	{
		if(focus)
		{	
			requestFocus();	
		}
	}
	
	public void stopTimers()
	{
		jelly.setVisible(false);
		focus = false;
		displayTime.stop();
		jellyTimer.stop();
	}
	public void startTimers()
	{
		jelly.setVisible(true);
		focus = true;
		displayTime.start();
		jellyTimer.start();
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
	public void keyReleased(KeyEvent e){}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void paint(Graphics g)
	{
		focus();
		updateJellyLocation();
		g.drawImage(backgroundImg,0,0,this);
		super.paint(g);;
	}
}
