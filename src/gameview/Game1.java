/**
 * @author Ariana Fairbanks
 * @author Jadie Adams
 */

package gameview;

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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class Game1 extends JPanel implements Game
{
	private static final long serialVersionUID = -5262708339581599541L;
	private Controller base;
	private Game thisGame;
	private ArrayList<FishObject> currentFish;
	private int width;
	private int height;
	private int maxFishVertical;
	private int frequency;
	private int answer;
	private int score;
	private int gamePeriod;
	private int currentTime;
	private int fishImageWidth;
	private int fishImageHeight;
	private int poleWidth;
	private int poleHeight;
	private int pause;
	private SpringLayout theLayout;
	private String question;
	private List<String> questionList;
	private JLabel timerLabel;
	private JLabel questionLabel;
	private JLabel scoreLabel;
	private JButton menu;
	private JButton help;
	private Timer fishTimer;
	private Timer displayTime;
	private ActionListener fishMover;
	private ActionListener timeDisplayer;
	private Image fishImg;
	private ImageIcon fishIcon;
	private Image catchImg;
	private ImageIcon catchIcon;
	private Image backgroundImg;
	private JLabel feedbackLabel;
	private JLabel background;
	private int questionBase;
	private int questionTypes; //TODO
	private int fishSpeed;
	private int questionsAnswered;
	private int questionsCorrect;
	private int guesses;
	private boolean playing;
	private boolean reset;
	private boolean miss;

	public Game1(Controller base)
	{
		this.base = base;
		thisGame = this;
		currentFish = new ArrayList<FishObject>();
		width = base.frame.getWidth();
		height = base.frame.getHeight();
		maxFishVertical = (height - 250) / 60;
		frequency = base.getFrequency();
		answer = 0;
		score = 0;
		gamePeriod = 40; // seconds
		theLayout = new SpringLayout();
		question = "Question";
		questionList = base.getEquations();
		timerLabel = new JLabel("Time: " + (gamePeriod / 60) + ":" + (gamePeriod % 60));
		questionLabel = new JLabel(question);
		questionLabel.setBackground(new Color(245, 245, 245));
		scoreLabel = new JLabel("Score: 0");
		menu = new JButton(" Exit ");
		help = new JButton(" Help ");
		
		questionBase = 15;
		questionTypes = base.questionTypes; // both, addition, subtraction
		
		fishSpeed = 40;
		questionsAnswered = 0;
		questionsCorrect = 0;
		guesses = 0;
		pause = 0;
		feedbackLabel = new JLabel("");
		
		fishImageWidth = (width - 250) / 8;
		fishImageHeight = (height - 250) / 5;
		poleWidth = (base.frame.getWidth() / 3);
		poleHeight = (base.frame.getHeight() / 2);
		try
		{
			fishImg = ImageIO.read(this.getClass().getResourceAsStream("fish.png"));
			backgroundImg = ImageIO.read(this.getClass().getResourceAsStream("background.jpg"));
			catchImg = ImageIO.read(this.getClass().getResourceAsStream("fishingpoleHooked.png"));
		}
		catch (IOException e){e.printStackTrace();}
		fishImg = fishImg.getScaledInstance(fishImageWidth, fishImageHeight, java.awt.Image.SCALE_SMOOTH);
		fishIcon = new ImageIcon(fishImg);
		backgroundImg = backgroundImg.getScaledInstance(base.frame.getWidth(), base.frame.getHeight(), java.awt.Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(backgroundImg));
		catchImg = catchImg.getScaledInstance(poleWidth, poleHeight, java.awt.Image.SCALE_SMOOTH);
		catchIcon = new ImageIcon(catchImg);
		
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setUpLayout();
		playGame();
		setUpListeners();
		setUpTimers();
		instructionsHandling();
	}
	
	private void setUpLayout()
	{
		setLayout(theLayout);

		timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timerLabel.setForeground(new Color(70, 130, 180));
		timerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		theLayout.putConstraint(SpringLayout.NORTH, timerLabel, 10, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.WEST, timerLabel, 10, SpringLayout.WEST, this);

		scoreLabel.setFont(new Font("Arial", Font.PLAIN, 35));
		scoreLabel.setForeground(new Color(70, 130, 180));
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		theLayout.putConstraint(SpringLayout.NORTH, scoreLabel, 0, SpringLayout.NORTH, menu);
		theLayout.putConstraint(SpringLayout.EAST, scoreLabel, -20, SpringLayout.WEST, help);

		questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		questionLabel.setForeground(new Color(70, 130, 180));
		questionLabel.setFont(new Font("Arial", Font.BOLD, 45));
		theLayout.putConstraint(SpringLayout.WEST, questionLabel, 350, SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.SOUTH, questionLabel, -25, SpringLayout.SOUTH, this);
		
		theLayout.putConstraint(SpringLayout.WEST, feedbackLabel, (base.frame.getWidth()/3), SpringLayout.WEST, this);
		theLayout.putConstraint(SpringLayout.NORTH, feedbackLabel, (base.frame.getHeight()/4), SpringLayout.NORTH, this);

		menu.setFont(new Font("Arial", Font.PLAIN, 30));
		menu.setForeground(new Color(70, 130, 180));
		menu.setBackground(new Color(70, 130, 180));
		menu.setFocusPainted(false);
		menu.setContentAreaFilled(false);
		menu.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		theLayout.putConstraint(SpringLayout.NORTH, menu, 10, SpringLayout.NORTH, this);
		theLayout.putConstraint(SpringLayout.EAST, menu, -10, SpringLayout.EAST, this);
		
		help.setFont(new Font("Arial", Font.PLAIN, 30));
		help.setForeground(new Color(70, 130, 180));
		help.setBackground(new Color(70, 130, 180));
		help.setFocusPainted(false);
		help.setContentAreaFilled(false);
		help.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		theLayout.putConstraint(SpringLayout.NORTH, help, 0, SpringLayout.NORTH, menu);
		theLayout.putConstraint(SpringLayout.EAST, help, -20, SpringLayout.WEST, menu);
		
		add(timerLabel);
		add(scoreLabel);
		add(questionLabel);
		add(menu);
		add(help);
		add(feedbackLabel);
	}

	private void playGame()
	{
		feedbackLabel.setIcon(null);
		getQuestion();
		questionLabel.setText(question);
		int randomPlacement = Controller.rng.nextInt(maxFishVertical);
		ArrayList<Integer> answerOptions = new ArrayList<Integer>();
		for (int i = 0; i < maxFishVertical; i++)
		{
			int fishAnswer = Controller.rng.nextInt(questionBase);
			while (fishAnswer == answer || answerOptions.contains(fishAnswer))
			{
				fishAnswer = Controller.rng.nextInt(questionBase);
			}
			if (i == randomPlacement)
			{
				fishAnswer = answer;
			}
			answerOptions.add(fishAnswer);
			currentFish.add(new FishObject(fishAnswer, i, this, fishIcon));
		}
		remove(background);
		addFish();
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
				int dialogResult = JOptionPane.showConfirmDialog(null, "Your score is " + score + ". Would you like to exit the game?", "Exit game?", JOptionPane.OK_CANCEL_OPTION);
				if(dialogResult == JOptionPane.OK_OPTION)
				{
					if(questionsAnswered > 0)
					{
						base.addGameRecord(1, questionsAnswered, questionsCorrect, guesses, gamePeriod - currentTime, score);
					}
					base.returnToMenu();
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
				new InstructionPanel(base, 1, base.getInstructionPreference("game1Instructions"), thisGame);
			}
		});
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
					playing = true;
					clearCurrentFish();
					//remove(background);
					playGame();
				}
				else if (!playing)
				{
					reset = true;
				}
				if (currentTime == 0)
				{
					playing = false;
					timerLabel.setText("Time: 0:00");
					stopTimers();
					if(questionsAnswered > 0)
					{
						base.addGameRecord(1, questionsAnswered, questionsCorrect, guesses, gamePeriod, score);
					}
					System.out.println("Time's up!");
					JOptionPane.showMessageDialog(base.messagePanel, "Your score was " + score + ".", "Time's up!", JOptionPane.PLAIN_MESSAGE);
					clearCurrentFish();
					base.returnToMenu();
				}
			}
		};
		displayTime = new Timer(1000, timeDisplayer);
		displayTime.setRepeats(true);

		fishMover = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(playing)
				{
					moveFish();
					if(miss)
					{
						if (pause >= 15)
						{
							miss = false;
							pause = 0;
							moveFish();
							questionLabel.setText(question);
						}
						pause++;
					}
				}
			}
		};
		fishTimer = new Timer(fishSpeed, fishMover);
		fishTimer.setRepeats(true);

		startTimers();
	}
	
	private void instructionsHandling()
	{
		boolean showInstructions = base.getInstructionPreference("game1Instructions");
		if(showInstructions)
		{
			new InstructionPanel(base, 1, showInstructions, this);
		}
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

	private void updateScore(boolean correct)
	{
		if (correct)
		{
			questionsCorrect++;
			score += 50;
			scoreLabel.setText("Score: " + Integer.toString(score));
			questionLabel.setText(question.substring(0, question.indexOf("?")) + " " + answer + "  Correct!");
			feedbackLabel.setIcon(catchIcon);
			repaint();
			playing = false;
		}
		else
		{
			guesses++;
			if (score > 0)
			{
				score -= 5;
				scoreLabel.setText("Score: " + Integer.toString(score));
			}
			questionLabel.setText(question + " Try again!");
			miss = true;
		}
	}

	private void removeFish(FishObject fish)
	{
		currentFish.remove(fish);
		this.remove(fish);
	}

	private void clearCurrentFish()
	{
		for (FishObject fish : currentFish)
		{
			this.remove(fish);
		}
		currentFish = new ArrayList<FishObject>();
		repaint();
		questionsAnswered++;
	}

	private void moveFish()
	{
		for (FishObject fish : currentFish)
		{
			fish.updateFishLocation();
			repaint(fish.getBounds());
		}
	}

	private void addFish()
	{
		for (FishObject fish : currentFish)
		{
			fish.setFocusPainted(false);
			fish.setFont(new Font("Ariel", Font.PLAIN, 20));
			fish.setForeground(Color.WHITE);
			add(fish);
		}
	}

	private void refreshFishLocation()
	{
		for (FishObject fish : currentFish)
		{
			fish.setLocation(fish.getXValue(), fish.getYValue());
		}
	}

	public void startTimers()
	{
		questionLabel.setVisible(true);
		displayTime.start();
		fishTimer.start();
	}

	public void stopTimers()
	{
		questionLabel.setVisible(false);
		displayTime.stop();
		fishTimer.stop();
	}
	
	public void fishWentOffScreen(FishObject fish)
	{
		if (fish.getAnswer() == answer)
		{
			System.out.println("Correct answer went off screen.");
			stopTimers();
			playing = false;
			if(questionsAnswered > 0)
			{
				base.addGameRecord(1, questionsAnswered, questionsCorrect, guesses, gamePeriod - currentTime, score);
			}
			JOptionPane.showMessageDialog(base.messagePanel, "The correct answer went off screen.\nYour score was " + score + ".", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			clearCurrentFish();
			base.returnToMenu();
		}
	}

	public void fishWasSelected(FishObject fish)
	{
		if (fish.getAnswer() == answer)
		{
			System.out.println("Correct answer given.");
			updateScore(true);
			clearCurrentFish();
		}
		else
		{
			System.out.println("Incorrect answer given.");
			removeFish(fish);
			updateScore(false);
		}
	}

	@Override
	public void paint(Graphics g)
	{
		g.drawImage(backgroundImg,0,0,this);
		refreshFishLocation();
		super.paint(g);
	}
	
}
