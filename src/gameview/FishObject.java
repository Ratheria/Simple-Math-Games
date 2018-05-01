/**
 *	@author Ariana Fairbanks
*	@author Jadie Adams
 */

package gameview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import adapter.Controller;
import view.Frame;

public class FishObject extends JButton implements ActionListener
{
	private static final long serialVersionUID = -5425295018457401763L;
	private Game1 panel;
	private int answer;
	private int xValue;
	private int yValue;
	private int maxX;
	private int movement;

	public FishObject(int answer, int numberFromTop, Game1 panel, ImageIcon icon)
	{
		super((answer + ""), icon);
		this.panel = panel;
		this.answer = answer;
		movement = 2; //TODO
		xValue = (50 - 15 * Controller.rng.nextInt(4));
		yValue = 100 + (numberFromTop * 70);
		maxX = Frame.DIMENSIONS.width - 25;

		setLocation(xValue, yValue);
		setBorderPainted(false);
		setOpaque(false);
		setContentAreaFilled(false);
		setForeground(Color.BLACK);
		setFont(new Font("Arial", Font.BOLD, 40));
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		addActionListener(this);
	}

	public void updateFishLocation()
	{
		xValue += movement;
		if (xValue >= maxX)
		{	panel.fishWentOffScreen(this);	}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this))
		{	panel.fishWasSelected(this);	}
	}

	public int getAnswer()
	{	return answer;	}

	public int getXValue()
	{	return xValue;	}

	public int getYValue()
	{	return yValue;	}
}
