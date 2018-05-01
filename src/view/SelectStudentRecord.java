/**
 * @author Ariana Fairbanks
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.JTableHeader;
import adapter.Controller;
import model.CustomTableModel;

public class SelectStudentRecord extends JPanel
{
	private static final long serialVersionUID = -7386656563185975615L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel header;
	private JButton backButton;
	private JTextField selectionField;
	private JButton statsButton;
	private JButton gamesButton;
	private JButton sessionsButton;
	private JTextField searchField;
	private JTable dataSet;
	private ListSelectionModel listSelectionModel;
	private TableRowSorter<TableModel> rowSorter;
	private JScrollPane scrollPane;

	public SelectStudentRecord(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		header = new JLabel("Select Student Records");
		backButton = new JButton(" BACK ");
		selectionField = new JTextField();
		statsButton = new JButton("   STATS   ");
		gamesButton = new JButton("   GAMES   ");
		sessionsButton = new JButton(" SESSIONS ");
		searchField = new JTextField();
		dataSet = new JTable();

		setUpTable();
		setUpLayout();
		setUpListeners();
		searchField.requestFocusInWindow();
	}

	private void setUpTable()
	{
		ResultSet res = base.getStudents();
		try
		{
			dataSet = new JTable(CustomTableModel.buildTableModel(res, Controller.selectStudentRecordHeader));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		dataSet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rowSorter = new TableRowSorter<>(dataSet.getModel());
		dataSet.setRowSorter(rowSorter);
		listSelectionModel = dataSet.getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
		dataSet.setSelectionModel(listSelectionModel);
	}
	
	private void setUpLayout()
	{
		layout.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0 };
		layout.rowWeights = new double[]
		{ 0.0, 2.0, 0.0, 0.0, 1.0, 20.0 };
		layout.columnWidths = new int[]
		{ 0, 0, 0 };
		layout.columnWeights = new double[]
		{ 0.0, 1.0, 0.0 };
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(245, 245, 245));

		header.setVerticalAlignment(SwingConstants.TOP);
		header.setForeground(new Color(70, 130, 180));
		header.setFont(new Font("Arial", Font.PLAIN, 35));
		GridBagConstraints gbc_header = new GridBagConstraints();
		gbc_header.anchor = GridBagConstraints.NORTHWEST;
		gbc_header.gridwidth = 4;
		gbc_header.insets = new Insets(15, 10, 5, 0);
		gbc_header.gridx = 1;
		gbc_header.gridy = 0;

		backButton.setFont(new Font("Arial", Font.PLAIN, 25));
		backButton.setForeground(new Color(70, 130, 180));
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setFocusPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_backButton.insets = new Insets(20, 20, 10, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;

		selectionField.setFont(new Font("Arial", Font.PLAIN, 20));
		selectionField.setForeground(new Color(0, 0, 128));
		selectionField.setBackground(new Color(245, 245, 245));
		selectionField.setToolTipText("Username");
		selectionField.setBorder(new CompoundBorder(new LineBorder(new Color(70, 130, 180)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_selectionField = new GridBagConstraints();
		gbc_selectionField.gridwidth = 2;
		gbc_selectionField.insets = new Insets(10, 50, 10, 5);
		gbc_selectionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectionField.gridx = 0;
		gbc_selectionField.gridy = 2;
		
		statsButton.setHorizontalTextPosition(SwingConstants.CENTER);
		statsButton.setFont(new Font("Arial", Font.PLAIN, 20));
		statsButton.setForeground(new Color(70, 130, 180));
		statsButton.setBackground(new Color(0, 0, 0));
		statsButton.setFocusPainted(false);
		statsButton.setContentAreaFilled(false);
		statsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_statsButton = new GridBagConstraints();
		gbc_statsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_statsButton.insets = new Insets(10, 10, 10, 5);
		gbc_statsButton.gridx = 2;
		gbc_statsButton.gridy = 2;
		
		gamesButton.setHorizontalTextPosition(SwingConstants.CENTER);
		gamesButton.setFont(new Font("Arial", Font.PLAIN, 20));
		gamesButton.setForeground(new Color(70, 130, 180));
		gamesButton.setBackground(new Color(0, 0, 0));
		gamesButton.setFocusPainted(false);
		gamesButton.setContentAreaFilled(false);
		gamesButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_gamesButton = new GridBagConstraints();
		gbc_gamesButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_gamesButton.insets = new Insets(10, 5, 10, 5);
		gbc_gamesButton.gridx = 3;
		gbc_gamesButton.gridy = 2;
		
		sessionsButton.setHorizontalTextPosition(SwingConstants.CENTER);
		sessionsButton.setFont(new Font("Arial", Font.PLAIN, 20));
		sessionsButton.setForeground(new Color(70, 130, 180));
		sessionsButton.setBackground(new Color(0, 0, 0));
		sessionsButton.setFocusPainted(false);
		sessionsButton.setContentAreaFilled(false);
		sessionsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_sessionsButton = new GridBagConstraints();
		gbc_sessionsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_sessionsButton.insets = new Insets(10, 5, 10, 50);
		gbc_sessionsButton.gridx = 4;
		gbc_sessionsButton.gridy = 2;
		
		searchField.setFont(new Font("Arial", Font.PLAIN, 20));
		searchField.setForeground(new Color(0, 0, 128));
		searchField.setBackground(new Color(245, 245, 245));
		searchField.setToolTipText("Username");
		searchField.setBorder(new CompoundBorder(new LineBorder(new Color(70, 130, 180)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.gridwidth = 5;
		gbc_searchField.insets = new Insets(0, 50, 5, 50);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 0;
		gbc_searchField.gridy = 3;

		dataSet.setForeground(new Color(0, 0, 128));
		dataSet.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dataSet.setBackground(new Color(240, 240, 245));
		dataSet.setRowHeight(30);
		JTableHeader header = dataSet.getTableHeader();
		header.setReorderingAllowed(false);
		header.setForeground(new Color(245, 245, 245));
		header.setFont(new Font("Arial", Font.PLAIN, 25));
		header.setBackground(new Color(70, 130, 180));
		header.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		scrollPane = new JScrollPane(dataSet);
		scrollPane.setViewportBorder(new LineBorder(new Color(70, 130, 180)));
		scrollPane.getViewport().setForeground(Color.BLACK);
		scrollPane.getViewport().setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(new Color(245, 245, 245));
		scrollPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_studentRecordsSet = new GridBagConstraints();
		gbc_studentRecordsSet.gridwidth = 5;
		gbc_studentRecordsSet.gridy = 5;
		gbc_studentRecordsSet.insets = new Insets(0, 20, 20, 20);
		gbc_studentRecordsSet.fill = GridBagConstraints.BOTH;
		gbc_studentRecordsSet.gridx = 0;

		add(header, gbc_header);
		add(backButton, gbc_backButton);
		add(selectionField, gbc_selectionField);
		add(statsButton, gbc_statsButton);
		add(gamesButton, gbc_gamesButton);
		add(sessionsButton, gbc_sessionsButton);
		add(searchField, gbc_searchField);
		add(scrollPane, gbc_studentRecordsSet);
	}

	private void setUpListeners()
	{
		backButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.returnToMenu();
			}
		});

		searchField.getDocument().addDocumentListener(new DocumentListener()
		{
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                String text = searchField.getText();
                if (text.trim().length() == 0) 
                {
                	rowSorter.setRowFilter(null);
                } 
                else 
                {	
                	rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                String text = searchField.getText();
                if (text.trim().length() == 0) 
                {
                	rowSorter.setRowFilter(null);	
                } 
                else 
                {
                	rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));	
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) 
            {	
            	throw new UnsupportedOperationException("Not supported."); 
            }
        });
		
		statsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				try
				{
					if(selectionField.getText().length() > 0)
					{
						base.changeState(Integer.parseInt(selectionField.getText()), -1);
					}
				}
				catch(NumberFormatException e){	}
				selectionField.setText("");
			}
		});
		
		gamesButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				try
				{
					if(selectionField.getText().length() > 0)
					{
						base.changeState(Integer.parseInt(selectionField.getText()), 0);
					}
				}
				catch(NumberFormatException e){	}
				selectionField.setText("");
			}
		});
		
		sessionsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				try
				{
					if(selectionField.getText().length() > 0)
					{
						base.changeState(Integer.parseInt(selectionField.getText()), 1);
					}
				}
				catch(NumberFormatException e){	}
				selectionField.setText("");
			}
		});
		
	}

	private void updateField()
	{
		int row = dataSet.getSelectedRow();
		selectionField.setText("" + ((Integer) dataSet.getValueAt(row, 0)).intValue());
	}

	class SharedListSelectionHandler implements ListSelectionListener 
	{		
	    public void valueChanged(ListSelectionEvent e) 
	    {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();;
	        if (lsm.isSelectionEmpty())
	        {	} 
	        else 
	        {	
	        	updateField();
	        }
	    }
	}

}
