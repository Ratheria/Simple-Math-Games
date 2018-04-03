/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import adapter.Controller;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Game3 extends JPanel implements KeyListener
{
	private static final long serialVersionUID = 8585306608329719982L;
	@SuppressWarnings("unused")
	private Controller base;
	private SpringLayout theLayout;
	private JLabel shark;
	private Image sharkImg;
	private ImageIcon sharkIcon;
	private Point sharkLocation;
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

	public Game3(Controller base) 
	{
		this.base = base;
		theLayout = new SpringLayout();
		screenWidth = base.frame.getWidth();
		screenHeight = base.frame.getHeight();
		sharkLocation = new Point(screenWidth / 2, screenHeight / 2);
		movementSpeed = 2;
		sharkHeight = 100;
		sharkWidth = 150;
		try 
		{	sharkImg = ImageIO.read(this.getClass().getResourceAsStream("shark.png"));	} 
		catch (IOException ex) 
		{	System.out.println("File \"shark.png\" is missing.");	}
		sharkImg = sharkImg.getScaledInstance(sharkWidth, sharkHeight,  java.awt.Image.SCALE_SMOOTH );
		sharkIcon = new ImageIcon(sharkImg);
		shark = new JLabel(sharkIcon);
		up = false;
		down = false;
		right = false;
		left = false;
		playing = true;
		
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		setUpLayout();
		playGame();
	}
	
	private void setUpLayout() 
	{
		setLayout(theLayout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setBackground(new Color(213, 248, 255));
		setBackground(new Color(208, 243, 255));
		shark.setLocation(sharkLocation);
		add(shark);
	}
	
	private void playGame()
	{
		screenRefresh = new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{	
				if(playing)
				{	
					moveShark();
					repaint();	
				}
				else
				{	refreshTimer.stop();	}
			}
		};
		refreshTimer = new Timer(20, screenRefresh);
		refreshTimer.start();
		refreshTimer.setRepeats(true);
	}

	private void moveShark() 
	{
		if (up)
		{
			if (sharkLocation.y > 0)
			{	sharkLocation.y -= movementSpeed;	}
		}
		if (down)
		{
			if (sharkLocation.y < screenHeight - sharkHeight)
			{	sharkLocation.y += movementSpeed;	}
		}
		if (right)
		{
			if (sharkLocation.x < screenWidth - sharkWidth)
			{	sharkLocation.x += movementSpeed;	}
		}
		if (left)
		{
			if (sharkLocation.x > 0)
			{	sharkLocation.x -= movementSpeed;	}
		}
		shark.setLocation(sharkLocation);
	}

	public void resolveKeyEvent(KeyEvent e, boolean value) 
	{
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) 
		{	up = value;	}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) 
		{	down = value;	}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) 
		{	left = value;	}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) 
		{	right = value;	}
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
	{	}

	@Override
	public void paint(Graphics g)
	{
		requestFocus();
		shark.setLocation(sharkLocation);
		super.paint(g);;
	}
	
}
