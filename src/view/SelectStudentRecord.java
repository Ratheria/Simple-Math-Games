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
	private JTextField studentLookupField;
	private JTable studentRecordsSet;
	private TableRowSorter<TableModel> rowSorter;
	private JScrollPane scrollPane;

	public SelectStudentRecord(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		header = new JLabel("Select Student Records");
		backButton = new JButton(" BACK ");
		studentLookupField = new JTextField();
		studentRecordsSet = new JTable();

		ResultSet res = base.getStudents();
		try
		{
			studentRecordsSet = new JTable(CustomTableModel.buildTableModel(res, Controller.selectStudentRecordHeader));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		rowSorter = new TableRowSorter<>(studentRecordsSet.getModel());
		studentRecordsSet.setRowSorter(rowSorter);

		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout()
	{
		layout.rowHeights = new int[]
		{ 0, 0, 0, 0, 0 };
		layout.rowWeights = new double[]
		{ 0.0, 1.0, 0.0, 1.0, 20.0 };
		layout.columnWidths = new int[]
		{ 0, 0, 0 };
		layout.columnWeights = new double[]
		{ 0.0, 1.0, 1.0 };
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
		gbc_backButton.insets = new Insets(20, 20, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;

		studentLookupField.setFont(new Font("Arial", Font.PLAIN, 20));
		studentLookupField.setForeground(new Color(0, 0, 128));
		studentLookupField.setBackground(new Color(245, 245, 245));
		studentLookupField.setToolTipText("Username");
		studentLookupField.setBorder(new CompoundBorder(new LineBorder(new Color(70, 130, 180)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_studentLookupField = new GridBagConstraints();
		gbc_studentLookupField.gridwidth = 4;
		gbc_studentLookupField.insets = new Insets(10, 100, 10, 100);
		gbc_studentLookupField.fill = GridBagConstraints.HORIZONTAL;
		gbc_studentLookupField.gridx = 0;
		gbc_studentLookupField.gridy = 2;

		studentRecordsSet.setForeground(new Color(0, 0, 128));
		studentRecordsSet.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		studentRecordsSet.setBackground(new Color(240, 240, 245));
		studentRecordsSet.setRowHeight(30);
		JTableHeader header = studentRecordsSet.getTableHeader();
		header.setReorderingAllowed(false);
		header.setForeground(new Color(245, 245, 245));
		header.setFont(new Font("Arial", Font.PLAIN, 25));
		header.setBackground(new Color(70, 130, 180));
		header.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		scrollPane = new JScrollPane(studentRecordsSet);
		scrollPane.setViewportBorder(new LineBorder(new Color(70, 130, 180)));
		scrollPane.getViewport().setForeground(Color.BLACK);
		scrollPane.getViewport().setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(new Color(245, 245, 245));
		scrollPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_studentRecordsSet = new GridBagConstraints();
		gbc_studentRecordsSet.gridwidth = 5;
		gbc_studentRecordsSet.gridy = 4;
		gbc_studentRecordsSet.insets = new Insets(0, 20, 20, 20);
		gbc_studentRecordsSet.fill = GridBagConstraints.BOTH;
		gbc_studentRecordsSet.gridx = 0;

		add(header, gbc_header);
		add(backButton, gbc_backButton);
		add(studentLookupField, gbc_studentLookupField);
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

		studentLookupField.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				String text = studentLookupField.getText();
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
				String text = studentLookupField.getText();
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
	}

	private void updateField()
	{
		int row = studentRecordsSet.getSelectedRow();
		String selection = (String) studentRecordsSet.getValueAt(row, 0);
		// userField.setText(selection);
		// TODO
	}

	private class SharedListSelectionHandler implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (lsm.isSelectionEmpty())
			{}
			else
			{
				updateField();
			}
		}
	}

}
