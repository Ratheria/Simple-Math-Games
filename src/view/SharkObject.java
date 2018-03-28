/**
 *	@author Walker Flocker
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

public class SharkObject extends JButton implements ActionListener
{
	private static final long serialVersionUID = 3336796505727171017L;
	private Game3 panel;
	private boolean onScreen;
	private String question;
	private String answer;
	private int xValue;
	private int yValue;
	private int maxX;
	
    public SharkObject(String question, String answer, Game3 panel, int numberFromTop, ImageIcon icon)
    {
    	this.setBorderPainted(false);
    	this.setOpaque(false);
    	this.setContentAreaFilled(false);
    	this.setForeground(java.awt.Color.WHITE);
    	this.setFont(new Font("MV Boli", Font.BOLD, 40));
    	this.setVerticalTextPosition(SwingConstants.CENTER);
    	this.setHorizontalTextPosition(SwingConstants.CENTER);
    	
        this.panel = panel;
        onScreen = true;
        this.question = question;
        this.answer = answer;
        xValue = 50; 
        yValue = 100 + (numberFromTop * 90);
        maxX = Frame.DIMENSIONS.width - 200;

        panel.add(this);
        addActionListener(this);
    }

    public void paint(Graphics g) 
    {
    	this.setLocation(xValue, yValue);
        super.paint(g);
        panel.add(this);
    }
    
    public void updateLocation()
    {
    	xValue += 35;
    	if(xValue >= maxX)	
    		panel.wentOffScreen(this);	
    }
    
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this)) 
			panel.selected(this);
	}

	public String getEquation()
	{	
		return question;	
	}
	
	public String getAnswer()
	{
		return answer;
	}
	
	public int getXValue()
	{	
		return xValue;	
	}
	
	public int getYValue()
	{	
		return yValue;
	}

}
