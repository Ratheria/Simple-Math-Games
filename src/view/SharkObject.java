/**
 *	@author Walker Flocker
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import adapter.Controller;

public class SharkObject extends JButton implements ActionListener
{
	private static final long serialVersionUID = -6301327677560567434L;
	private Game3 panel;
	private boolean onScreen;
	private String question;
	private String answer;
	private int xValue;
	private int yValue;
	private int maxX;
	
    public SharkObject(String question, String answer, Game3 panel, ImageIcon icon)
    {
    	this.setBorderPainted(false);
    	this.setOpaque(false);
    	this.setContentAreaFilled(false);
    	this.setForeground(java.awt.Color.BLACK);
    	this.setFont(new Font("MV Boli", Font.BOLD, 40));
    	this.setVerticalTextPosition(SwingConstants.CENTER);
    	this.setHorizontalTextPosition(SwingConstants.CENTER);
    	
        this.panel = panel;
        onScreen = true;
        this.question = question;
        this.answer = answer;
        xValue = 10; 
        yValue = 90;

        panel.add(this);
        addActionListener(this);
    }

    public void paint(Graphics g) 
    {
    	this.setLocation(xValue, yValue);
        super.paint(g);
        panel.add(this);
    }
    
    public void updateLocation(String direction)
    {
    	switch(direction) { 
        case "up":
        	yValue -= 90;
            break;
        case "down":
			yValue += 90;  
			break;
        case "left":
        	xValue -= 45;
        	break;
        case "right":
        	xValue += 45;
        	break;
    	}
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
