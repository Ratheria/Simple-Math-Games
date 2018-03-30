/**
 *	@author Ariana Fairbanks
*	@author Jadie Adams
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import adapter.Controller;

public class FishObject extends JButton implements ActionListener
{
	private static final long serialVersionUID = -5425295018457401763L;
	private Game1 panel;
	private boolean onScreen;
	private int answer;
	private int xValue;
	private int yValue;
	private int maxX;
	
    public FishObject(int answer, int numberFromTop, Game1 panel, ImageIcon icon)
    {
    	super((answer + ""), icon);
    	this.setBorderPainted(false);
    	this.setOpaque(false);
    	this.setContentAreaFilled(false);
    	this.setForeground(Color.BLACK);
    	this.setFont(new Font("Arial", Font.BOLD, 40));
    	this.setVerticalTextPosition(SwingConstants.CENTER);
    	this.setHorizontalTextPosition(SwingConstants.CENTER);
    	
        this.panel = panel;
        onScreen = true;
        this.answer = answer;
        xValue = 50; 
        		//- (Controller.rng.nextInt(6) * 10);
        yValue = 100 + (numberFromTop * 70);
        maxX = Frame.DIMENSIONS.width - 200;
        addActionListener(this);
    }

    public void paint(Graphics g) 
    {
    	this.setLocation(xValue, yValue);
        super.paint(g);
    }
    
    public void updateLocation()
    {
    	xValue += 1;
    	if(xValue >= maxX)
    	{	panel.wentOffScreen(this);	}
    }
    
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this)) 
		{	panel.selected(this);	}
	}

	public int getAnswer()
	{	return answer;	}
	
	public int getXValue()
	{	return xValue;	}
	
	public int getYValue()
	{	return yValue;	}

}
