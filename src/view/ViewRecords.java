/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import adapter.Controller;
import model.CustomTableModel;

public class ViewRecords extends JPanel
{
	private static final long serialVersionUID = 2146437373343203144L;
	private Controller base;
	private GridBagLayout layout;
	private JButton backButton;
	private JTable dataSet;
	private JScrollPane scrollPane;
    private TableRowSorter<TableModel> rowSorter;
	
	public ViewRecords(Controller base, int studentID, int value)
	{
		this.base = base;
		layout = new GridBagLayout();
		backButton = new JButton(" BACK ");
		dataSet = new JTable();
		
		String[] currentHeader = Controller.gamesHeader;
		ResultSet res = base.getGameRecords(studentID);
		if(value == 1)
		{	
			currentHeader = Controller.sessionsHeader;
			res = base.getSessionRecords(studentID);	
		}
		
		try 
		{	dataSet = new JTable(CustomTableModel.buildTableModel(res, currentHeader));	}
		catch (SQLException e) { e.printStackTrace(); }
		
		rowSorter = new TableRowSorter<>(dataSet.getModel());
		dataSet.setRowSorter(rowSorter);
		
		setUpLayout();
		setUpListeners();
	}
	
	private void setUpLayout() 
	{
		layout.rowHeights = new int[]{0, 0};
		layout.rowWeights = new double[]{0.0, 20.0};
		layout.columnWidths = new int[]{0, 0, 0};
		layout.columnWeights = new double[]{0.0, 1.0, 1.0};
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(245, 245, 245));
		
		backButton.setFont(new Font("Arial", Font.PLAIN, 25));
		backButton.setForeground(new Color(70, 130, 180));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(135, 206, 250), 2));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.insets = new Insets(20, 20, 20, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
		
		dataSet.setForeground(Color.BLACK);
		dataSet.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		dataSet.setBackground(new Color(245, 245, 250));
		dataSet.setCellSelectionEnabled(false);
		JTableHeader header = dataSet.getTableHeader();
		header.setReorderingAllowed(false);
		header.setForeground(new Color(70, 130, 180));
		header.setFont(new Font("Arial", Font.PLAIN, 20));
		header.setBackground(new Color(245, 245, 245));
		header.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		scrollPane = new JScrollPane(dataSet);
		scrollPane.setViewportBorder(new LineBorder(new Color(70, 130, 180)));
		scrollPane.getViewport().setForeground(Color.BLACK);
		scrollPane.getViewport().setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(new Color(245, 245, 250));
		scrollPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_dataSet = new GridBagConstraints();
		gbc_dataSet.gridwidth = 3;
		gbc_dataSet.gridy = 1;
		gbc_dataSet.insets = new Insets(0, 20, 20, 20);
		gbc_dataSet.fill = GridBagConstraints.BOTH;
		gbc_dataSet.gridx = 0;
		
		add(backButton, gbc_backButton);
		add(scrollPane, gbc_dataSet);
	}
	
	private void setUpListeners() 
	{
		backButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent onClick)
			{	
				base.returnToLastState();
			}
		});
	}

	//TODO
	//total score(s), user high scores, overall high scores, games played
	//stats for last 10 games? first and last? determine by date?
	
	
}
