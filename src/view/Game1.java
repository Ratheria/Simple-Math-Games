/**
 *	@author Jadie Adams
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import adapter.Controller;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Game1 extends JPanel
{
	private static final long serialVersionUID = -5262708339581599541L;
	private Controller base;
	private GridBagLayout gridBagLayout;
	private JLabel timerLabel;
	private JLabel question;
	
	public Game1(Controller base) 
	{
		this.base = base;
		gridBagLayout = new GridBagLayout();
		timerLabel = new JLabel("timerLabel");
		question = new JLabel("Question");
		
		setUpLayout();
		setUpListeners();
		
	}
	
	private void setUpLayout() 
	{
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gridBagLayout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(0, 0, 0));
		
		timerLabel.setVerticalAlignment(SwingConstants.TOP);
		timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		timerLabel.setForeground(new Color(135, 206, 250));
		timerLabel.setFont(new Font("MV Boli", Font.PLAIN, 35));
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.insets = new Insets(0, 0, 5, 0);
		gbc_timerLabel.gridx = 2;
		gbc_timerLabel.gridy = 0;
		add(timerLabel, gbc_timerLabel);
		
		question.setVerticalAlignment(SwingConstants.BOTTOM);
		question.setHorizontalAlignment(SwingConstants.CENTER);
		question.setForeground(new Color(135, 206, 250));
		question.setFont(new Font("MV Boli", Font.PLAIN, 35));
		GridBagConstraints gbc_question = new GridBagConstraints();
		gbc_question.insets = new Insets(0, 0, 0, 5);
		gbc_question.gridx = 1;
		gbc_question.gridy = 2;
		add(question, gbc_question);
	
	}
	
	
	private void setUpListeners() 
	{
	
	}
}
