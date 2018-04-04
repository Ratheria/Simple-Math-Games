/**
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

public class Game3 extends JPanel implements KeyListener {
	private static final long serialVersionUID = 8585306608329719982L;
	@SuppressWarnings("unused")
	private Controller base;
	private SpringLayout theLayout;
	private JLabel shark;
	private Image sharkImg;
	private ImageIcon sharkIcon;
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
	private ActionListener screenRefresh;
	private Timer refreshTimer;
	private int[] answerList;
	private int frequency;
	private List<String> questionList;
	private JLabel answerLabel1;
	private JLabel answerLabel2;
	private JLabel answerLabel3;
	private Point answerLabel1Location;
	private Point answerLabel2Location;
	private Point answerLabel3Location;
	private JLabel scoreLabel;
	private JLabel timerLabel;
	private String question;
	private String answer;
	private int score;
	private static final int GAME_PERIOD = 60;
	private int sec;

	public Game3(Controller base) {
		this.base = base;
		frequency = base.getFrequency();
		questionList = base.getEquations();
		theLayout = new SpringLayout();
		answerList = new int[2];
		question = "";
		answer = "0";
		score = 0;
		answerLabel1 = new JLabel();
		answerLabel2 = new JLabel();
		answerLabel3 = new JLabel();
		answerLabel1Location = new Point(0, 110);
		answerLabel2Location = new Point(0, 230);
		answerLabel3Location = new Point(0, 350);
		timerLabel = new JLabel("Time: " + (GAME_PERIOD / 60) + ":" + (GAME_PERIOD % 60));
		scoreLabel = new JLabel("Score: 0");
		screenWidth = base.frame.getWidth();
		screenHeight = base.frame.getHeight();
		sharkLocation = new Point(30, 110);
		movementSpeed = 3;
		sharkWidth = (base.frame.getWidth() - 660);
		sharkHeight = (base.frame.getHeight() - 450);

		try {
			sharkImg = ImageIO.read(this.getClass().getResourceAsStream("shark.png"));
		} 
		catch (IOException ex) {
			System.out.println("File \"shark.png\" is missing.");
		}

		sharkImg = sharkImg.getScaledInstance(sharkWidth, sharkHeight, java.awt.Image.SCALE_SMOOTH);
		sharkIcon = new ImageIcon(sharkImg);
		shark = new JLabel(sharkIcon);
		up = false;
		down = false;
		right = false;
		left = false;
		playing = true;

		getQuestion();
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		setUpLayout();
		setUpTimers();
		playGame();
	}

	private void setUpLayout() {
		setLayout(theLayout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setBackground(new Color(213, 248, 255));
		setBackground(new Color(208, 243, 255));

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

		shark.setLocation(sharkLocation);
		shark.setText(question);
		shark.setHorizontalTextPosition(SwingConstants.LEFT);

		addAnswerLabels();
		add(timerLabel);
		add(scoreLabel);
		add(shark);
	}

	private void setUpTimers() {
		sec = GAME_PERIOD - 1;
		timeDisplayer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if ((sec % 60) < 10) {
					timerLabel.setText("Time: " + (sec / 60) + ":0"
							+ (sec % 60));
				} else {
					timerLabel
							.setText("Time: " + (sec / 60) + ":" + (sec % 60));
				}
				sec--;
			}
		};
		displayTime = new Timer(1000, timeDisplayer); // time parameter
														// milliseconds
		displayTime.start();
		displayTime.setRepeats(true);

		gameRestarter = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				base.returnToMenu();
				System.out.println("Time's up!");
				JPanel gameOverPanel = new JPanel();
				JOptionPane.showMessageDialog(gameOverPanel, "Your score was " + score + ".", "Time's up!", JOptionPane.PLAIN_MESSAGE);
			}
		};
		timer = new Timer(GAME_PERIOD * 1000, gameRestarter); // time parameter
																// milliseconds
		timer.setRepeats(false);
		timer.start();
	}

	private void playGame() {
		screenRefresh = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (playing) {
					moveShark();
					repaint();
				} 
				else {
					refreshTimer.stop();
				}
			}
		};
		refreshTimer = new Timer(20, screenRefresh);
		refreshTimer.start();
		refreshTimer.setRepeats(true);
	}

	private void moveShark() {
		if (up) {
			if (sharkLocation.y > 0) {
				sharkLocation.y -= movementSpeed;
				if (checkAnswer()) {
					updateScore();
					getQuestion();
					repaint();
				}
			}
			else 
				resetShark();
		}
		if (down) {
			if (sharkLocation.y < screenHeight - sharkHeight) {
				sharkLocation.y += movementSpeed;
				if (checkAnswer()) {
					updateScore();
					getQuestion();
					repaint();
				}
			}
			else
				resetShark();
		}
		if (right) {
			if (sharkLocation.x < screenWidth - sharkWidth) {
				sharkLocation.x += movementSpeed;
				if (checkAnswer()) {
					updateScore();
					getQuestion();
					repaint();
//					setUpLayout();
				}
			}
			else
				resetShark();
		}
		if (left) {
			if (sharkLocation.x > 0) {
				sharkLocation.x -= movementSpeed;
				if (checkAnswer()) {
					updateScore();
					getQuestion();
					repaint();
				}
			}
			else
				resetShark();
		}
		shark.setLocation(sharkLocation);
	}

	public void resolveKeyEvent(KeyEvent e, boolean value) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			up = value;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_S) {
			down = value;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			left = value;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			right = value;
		}
	}

	public void keyPressed(KeyEvent e) {
		resolveKeyEvent(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		resolveKeyEvent(e, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	
	
	@Override
	public void paint(Graphics g) {
		requestFocus();
		shark.setText(question);
//		sharkLocation.setLocation(30, 100);
		answerLabel1.setLocation(answerLabel1.getLocation());
		answerLabel2.setLocation(answerLabel2.getLocation());
		answerLabel3.setLocation(answerLabel3.getLocation());
		// place all labels
		super.paint(g);
	}

	
	private void generateRandomAnswers() {
		for (int i = 0; i < 2; i++) {
			int random = Controller.rng.nextInt(15);
			answerList[i] = random;
			// System.out.println(answerList[i]);
		}
	}

	
	private void addAnswerLabels() {
		generateRandomAnswers();

		// TODO randomize the way answers are added to labels
		answerLabel1.setText("= " + Integer.toString(answerList[0]));
		answerLabel2.setText("= " + Integer.toString(answerList[1]));
		answerLabel3.setText("= " + answer);

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

	
	private void getQuestion() {
		int random = Controller.rng.nextInt(10);
		if (frequency > 0 && random <= frequency) {
			questionFromList();
		} 
		else {
			generateQuestion();
		}
		while (Integer.parseInt(answer) < 0) {
			// generates a new question if the answer is negative
			generateQuestion();
		}
	}

	
	private void questionFromList() {
		// Assumes only a + or - operator
		int random = Controller.rng.nextInt(questionList.size());
		System.out.println(random);
		question = questionList.get(random);
		System.out.println(question);
		if (question.contains("+")) {
			int operator = question.indexOf("+");
			int firstInteger = Integer
					.parseInt(question.substring(0, operator));
			int secondInteger = Integer.parseInt(question
					.substring(operator + 1));
			answer = Integer.toString(firstInteger + secondInteger);
			question = firstInteger + " + " + secondInteger + " = ? ";
		} 
		else {
			int operator = question.indexOf("-");
			int firstInteger = Integer
					.parseInt(question.substring(0, operator));
			int secondInteger = Integer.parseInt(question
					.substring(operator + 1));
			answer = Integer.toString(firstInteger - secondInteger);
			question = firstInteger + " - " + secondInteger + " = ? ";
		}
	}

	// TODO also need questions about place value
	
	private void generateQuestion() {
		int random = Controller.rng.nextInt(2);
		if (random < 1) {
			int firstInteger = Controller.rng.nextInt(30);
			int secondInteger = Controller.rng.nextInt(30);
			answer = Integer.toString(firstInteger - secondInteger);
			question = firstInteger + " - " + secondInteger + " = ? ";
		} 
		else {
			int firstInteger = Controller.rng.nextInt(30);
			int secondInteger = Controller.rng.nextInt(30);
			answer = Integer.toString(firstInteger + secondInteger);
			question = firstInteger + " + " + secondInteger + " = ? ";
		}
	}

	
	private void updateScore() {
		score += 5;
		scoreLabel.setText("Score: " + Integer.toString(score));
	}

	
	private void resetShark() {
		remove(shark);
		sharkLocation.setLocation(30, 100);
		add(shark);
	}
	
	private boolean checkAnswer() {
		// if the shark is over the correct answer, it's correct
		// make a Point for each answer label
		// if shark location is equal to answer label Point location, we good
		// give range of points maybe? Both vertical and horizontal??
		
		System.out.println("Shark X: " + shark.getX());
		System.out.println("Shark Y: " + shark.getY());
//		System.out.println("Answer X:" + answerLabel3.getX());
//		System.out.println("Answer Y: " + answerLabel3.getY());
		if ((shark.getX() >= (answerLabel3.getX() - 320)) && (shark.getY() >= 350 && shark.getY() <= 450)) {
//			System.out.println("yep, equal");
			return true;
		}
		return false;
	}
}
