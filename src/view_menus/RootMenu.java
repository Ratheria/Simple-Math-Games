/**
 *	@author Ariana Fairbanks
 */

package view_menus;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import adapter.Controller;
import adapter.ViewStates;
import model.CustomTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class RootMenu extends JPanel
{
	private static final long serialVersionUID = -5873415761431343161L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel displayName;
	private JButton settingsButton;
	private JComboBox<String> userOptions;
	private JTextField userField;
	private JTextField searchField;
	private JButton operationButton;
	private JTable dataSet;
	private TableRowSorter<TableModel> rowSorter;
	private ListSelectionModel listSelectionModel;
	private JScrollPane scrollPane;
	private JButton clearDataButton;
	private JButton addUsersButton;
	private int value;

	public RootMenu(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		displayName = new JLabel(" ");
		displayName.setVerticalAlignment(SwingConstants.BOTTOM);
		settingsButton = new JButton("     ");
		userOptions = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[]
		{ " Unlock User Account", " Reset Password", " Delete User" }));
		userField = new JTextField();
		operationButton = new JButton(" UNLOCK ACCOUNT ");
		searchField = new JTextField();
		dataSet = new JTable();
		clearDataButton = new JButton(" Clear Data ");
		addUsersButton = new JButton(" Add Users ");

		value = 0;

		setUpTable();
	}

	private void setUpTable()
	{
		ResultSet res = base.getAllUsers();
		try
		{
			dataSet = new JTable(CustomTableModel.buildTableModel(res, Controller.allUsersHeader));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		rowSorter = new TableRowSorter<>(dataSet.getModel());
		dataSet.setRowSorter(rowSorter);
		dataSet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel = dataSet.getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
		dataSet.setSelectionModel(listSelectionModel);

		removeAll();
		revalidate();
		repaint();
		setUpLayout();
		setUpListeners();
	}

	private void setUpLayout()
	{
		layout.rowHeights = new int[]
		{ 0, 0, 0, 0, 0, 0, 0 };
		layout.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 };
		layout.columnWidths = new int[]
		{ 0, 0, 0, 0, 0, 0 };
		layout.columnWeights = new double[]
		{ 0.0, 0.0, 1.0, 1.0, 0.0, 0.0 };
		setLayout(layout);
		setBorder(new LineBorder(new Color(128, 128, 128), 10));
		setForeground(new Color(105, 105, 105));
		setBackground(new Color(245, 245, 245));
		displayName.setForeground(new Color(105, 105, 105));
		displayName.setFont(new Font("Arial", Font.PLAIN, 25));
		displayName.setText(base.getFullName());
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.NORTHWEST;
		gbc_displayName.gridwidth = 5;
		gbc_displayName.insets = new Insets(20, 10, 5, 5);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;

		settingsButton.setFont(new Font("Arial", Font.PLAIN, 25));
		settingsButton.setForeground(new Color(192, 192, 192));
		settingsButton.setBackground(new Color(211, 211, 211));
		settingsButton.setFocusPainted(false);
		settingsButton.setBorder(new LineBorder(new Color(105, 105, 105), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_settingsButton.insets = new Insets(20, 20, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;

		userOptions.setForeground(new Color(105, 105, 105));
		userOptions.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_userOptions = new GridBagConstraints();
		gbc_userOptions.anchor = GridBagConstraints.NORTHWEST;
		gbc_userOptions.gridwidth = 2;
		gbc_userOptions.insets = new Insets(15, 40, 5, 5);
		gbc_userOptions.gridx = 0;
		gbc_userOptions.gridy = 1;

		userField.setFont(new Font("Arial", Font.PLAIN, 20));
		userField.setBackground(new Color(220, 220, 220));
		userField.setForeground(new Color(0, 0, 0));
		userField.setToolTipText("Username");
		userField.setBorder(new CompoundBorder(new LineBorder(new Color(105, 105, 105)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_userField = new GridBagConstraints();
		gbc_userField.gridwidth = 4;
		gbc_userField.insets = new Insets(15, 40, 15, 5);
		gbc_userField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField.gridx = 0;
		gbc_userField.gridy = 2;

		operationButton.setFont(new Font("Arial", Font.PLAIN, 20));
		operationButton.setForeground(new Color(105, 105, 105));
		operationButton.setBackground(new Color(0, 0, 0));
		operationButton.setFocusPainted(false);
		operationButton.setContentAreaFilled(false);
		operationButton.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_operationButton = new GridBagConstraints();
		gbc_operationButton.gridwidth = 2;
		gbc_operationButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_operationButton.insets = new Insets(10, 10, 10, 40);
		gbc_operationButton.gridx = 4;
		gbc_operationButton.gridy = 2;

		searchField.setFont(new Font("Arial", Font.PLAIN, 20));
		searchField.setBackground(new Color(220, 220, 220));
		searchField.setForeground(new Color(0, 0, 0));
		searchField.setToolTipText("Username");
		searchField.setBorder(new CompoundBorder(new LineBorder(new Color(105, 105, 105)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.gridwidth = 6;
		gbc_searchField.insets = new Insets(5, 40, 5, 40);
		gbc_searchField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchField.gridx = 0;
		gbc_searchField.gridy = 3;

		dataSet.setForeground(new Color(0, 0, 0));
		dataSet.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dataSet.setBackground(new Color(245, 245, 245));
		dataSet.setRowHeight(30);
		JTableHeader header = dataSet.getTableHeader();
		header.setReorderingAllowed(false);
		header.setForeground(new Color(245, 245, 245));
		header.setFont(new Font("Arial", Font.PLAIN, 25));
		header.setBackground(new Color(105, 105, 105));
		header.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		scrollPane = new JScrollPane(dataSet);
		scrollPane.setViewportBorder(new LineBorder(new Color(128, 128, 128)));
		scrollPane.getViewport().setForeground(Color.BLACK);
		scrollPane.getViewport().setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(new Color(245, 245, 245));
		scrollPane.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		GridBagConstraints gbc_dataSet = new GridBagConstraints();
		gbc_dataSet.gridheight = 2;
		gbc_dataSet.gridwidth = 6;
		gbc_dataSet.gridy = 4;
		gbc_dataSet.insets = new Insets(15, 20, 15, 20);
		gbc_dataSet.fill = GridBagConstraints.BOTH;
		gbc_dataSet.gridx = 0;

		clearDataButton.setFont(new Font("Arial", Font.PLAIN, 25));
		clearDataButton.setForeground(new Color(105, 105, 105));
		clearDataButton.setBackground(new Color(105, 105, 105));
		clearDataButton.setFocusPainted(false);
		clearDataButton.setContentAreaFilled(false);
		clearDataButton.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_clearDataButton = new GridBagConstraints();
		gbc_clearDataButton.anchor = GridBagConstraints.EAST;
		gbc_clearDataButton.insets = new Insets(0, 0, 20, 20);
		gbc_clearDataButton.gridx = 4;
		gbc_clearDataButton.gridy = 6;
		
		addUsersButton.setFont(new Font("Arial", Font.PLAIN, 25));
		addUsersButton.setForeground(new Color(105, 105, 105));
		addUsersButton.setBackground(new Color(105, 105, 105));
		addUsersButton.setFocusPainted(false);
		addUsersButton.setContentAreaFilled(false);
		addUsersButton.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		GridBagConstraints gbc_manageUsersButton = new GridBagConstraints();
		gbc_manageUsersButton.anchor = GridBagConstraints.EAST;
		gbc_manageUsersButton.insets = new Insets(0, 0, 20, 20);
		gbc_manageUsersButton.gridx = 5;
		gbc_manageUsersButton.gridy = 6;

		add(displayName, gbc_displayName);
		add(settingsButton, gbc_settingsButton);
		add(userOptions, gbc_userOptions);
		add(userField, gbc_userField);
		add(operationButton, gbc_operationButton);
		add(searchField, gbc_searchField);
		add(scrollPane, gbc_dataSet);
		add(clearDataButton, gbc_clearDataButton);
		add(addUsersButton, gbc_manageUsersButton);
	}

	private void setUpListeners()
	{
		settingsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.SETTINGS);
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

		userOptions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (value != userOptions.getSelectedIndex())
				{
					value = userOptions.getSelectedIndex();
					switch (value)
					{
						case 0:
							operationButton.setText(" UNLOCK ACCOUNT ");
							break;
						case 1:
							operationButton.setText(" RESET PASSWORD ");
							break;
						case 2:
							operationButton.setText(" DELETE USER ");
							break;
					}
				}
			}
		});

		operationButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				String inputText = userField.getText().trim();
				if (inputText != null && inputText.length() > 0)
				{
					int id = Integer.parseInt(inputText);
					int valueReturned;
					switch (value)
					{
						case 0:
							base.unlockAccount(id);
							break;
						case 1:
							valueReturned = base.confirmationMessage("Are you sure you want to this user's password to be the same as their user ID?");
							if (valueReturned == JOptionPane.OK_OPTION)
							{
								base.resetPassword(id);
							}
							break;
						case 2:
							valueReturned = base.confirmationMessage("Are you sure you want to delete this user?");
							if (valueReturned == JOptionPane.OK_OPTION)
							{
								base.deleteUser(id);
								setUpTable();
							}
							break;
					}
					userField.setText("");
				}
			}
		});

		clearDataButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.MANAGEDATA);
			}
		});
		
		addUsersButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.ADDUSERS);
			}
		});
	}

	private void updateField()
	{
		int row = dataSet.getSelectedRow();
		String selection = "" + (Integer) dataSet.getValueAt(row, 0);
		userField.setText(selection);
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
