package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import adapter.Controller;

public class JellyfishObject extends JButton implements ActionListener
{
	private static final long serialVersionUID = -5425295018457401763L;
	private Game2 panel;
	private boolean onScreen;
	private String question;
	private int answer;
	private int xValue;
	private int yValue;
	private int maxY;
	
    public JellyfishObject(String question, int answer, Game2 panel, ImageIcon icon)
    {
    	super((question + ""), icon);
    	this.setBorderPainted(false);
    	this.setOpaque(false);
    	this.setContentAreaFilled(false);
    	this.setForeground(Color.BLACK);
    	this.setFont(new Font("Arial", Font.BOLD, 20));
    	this.setVerticalTextPosition(SwingConstants.TOP);
    	this.setHorizontalTextPosition(SwingConstants.CENTER);
    	
        this.panel = panel;
        onScreen = true;
        this.question = question;
        this.answer = answer;
        xValue = randomXvalue(); 
        yValue = 0;
        maxY = Frame.DIMENSIONS.height;

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
    	yValue += 15;
    	if(yValue >= maxY)
    	{	panel.wentOffScreen(this);	}
    }
    
    public int randomXvalue(){

    	double d = Math.random();
    	int y = (int)(Frame.DIMENSIONS.width*d);
    	if(y < 200){
    		y = y + 200;
    	}
    	if(y > Frame.DIMENSIONS.width - 200){
    		y = y - 200;
    	}
    	return y;
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