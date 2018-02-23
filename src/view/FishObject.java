/**
 *	@author Ariana Fairbanks
*	@author Jadie Adams
 */

package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class FishObject extends JButton implements ActionListener
{
	private static final long serialVersionUID = -5425295018457401763L;
	private Game1 panel;
	private boolean onScreen;
	private int answer;
	private int xValue;
	private int yValue;
	private int maxX;
	
    public FishObject(int answer, int numberFromTop, int panelWidth, Game1 panel, ImageIcon icon)
    {
    	super((answer + ""), icon);
    	this.setBorderPainted(false);
    	this.setOpaque(false);
    	this.setContentAreaFilled(false);
    	this.setForeground(java.awt.Color.WHITE);
    	this.setFont(new Font("MV Boli", Font.BOLD, 10));
    	this.setVerticalTextPosition(SwingConstants.CENTER);
    	this.setHorizontalTextPosition(SwingConstants.CENTER);
    	
        this.panel = panel;
        onScreen = true;
        this.answer = answer;
        xValue = 50;
        yValue = 100 + (numberFromTop * 50);
        maxX = panelWidth - 50;
        panel.add(this);
        addActionListener(this);
    }

    public void paint(Graphics g) 
    {
    	this.setLocation(xValue, yValue);
 //       g.translate(xValue, yValue);
        super.paint(g);
        panel.add(this);
        panel.repaint();
    }
    
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this)) 
		{
            panel.selected(this);
        }
		else
		{
			//TODO Movement that actually works
			xValue += 2;
	    	if(xValue > maxX)
	    	{
	    		onScreen = false;
	    	}
	    	if(onScreen)
	    	{	
	    		repaint();	
	    	}
	    	else
	    	{
	    		panel.wentOffScreen(this);
	    	}
		}
	}

	public int getAnswer()
	{	return answer;	}
	
	public int getXValue()
	{	return xValue;	}
	
	public int getYValue()
	{	return yValue;	}
	
}
