/**
 *	@author Ariana Fairbanks
 */

package view;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JSlider;
import javax.swing.JTable;

public class TeacherMenu extends JPanel implements ChangeListener
{
	private static final long serialVersionUID = -1163688838297846114L;
	private Controller base;
	private GridBagLayout layout;
	private JLabel displayName;
	private JButton settingsButton;
	private JButton viewRecordsButton;
	private JTextField addEquationsTextField;
	private JButton addEquationsButton;
	private JTextField removeEquationsTextField;
	private JButton removeEquationsButton;
	private JSlider frequencySlider;
	private JLabel frequencyLabel;
	private int numberOfQuestions;
	private String questionString;
	private ArrayList<String> questionList;
	private String[][] questionMatrix;
	private JTable dataSet;
	private JScrollPane scrollPane;
	private TableRowSorter<TableModel> rowSorter;
	private ListSelectionModel listSelectionModel;
	private int baseFrequency;
	private final String[] columnName =
	{ "Current Problems" };

	public TeacherMenu(Controller base)
	{
		this.base = base;
		layout = new GridBagLayout();
		settingsButton = new JButton("     ");
		displayName = new JLabel(" ");
		frequencyLabel = new JLabel("Custom Problem Frequency");
		frequencySlider = new JSlider();
		viewRecordsButton = new JButton(" Student Records ");
		addEquationsTextField = new JTextField();
		addEquationsButton = new JButton(" Add Problem ");
		removeEquationsTextField = new JTextField();
		removeEquationsButton = new JButton(" Remove Problem ");
		baseFrequency = base.getFrequency();
		dataSet = new JTable();

		setUpTable();
	}

	private void setUpTable()
	{
		updateQuestionInfo();
		questionMatrix = new String[numberOfQuestions][1];

		for (int i = 0; i < numberOfQuestions; i++)
		{
			questionMatrix[i][0] = questionList.get(i);
		}

		try
		{
			dataSet = new JTable(CustomTableModel.buildTableModel(questionMatrix, columnName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		dataSet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rowSorter = new TableRowSorter<>(dataSet.getModel());
		dataSet.setRowSorter(rowSorter);
		removeEquationsTextField = new JTextField();
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
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		layout.rowWeights = new double[]
		{ 0.0, 1.0, 0.0, 0.0, 3.0, 0.0, 0.0, 4.0, 25.0, 2.0, 0.0 };
		layout.columnWidths = new int[]
		{ 0, 0, 0, 0, 0, 0, 100 };
		layout.columnWeights = new double[]
		{ 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0 };
		setLayout(layout);
		setBorder(new LineBorder(new Color(70, 130, 180), 10));
		setForeground(new Color(0, 255, 255));
		setBackground(new Color(245, 245, 245));

		settingsButton.setFont(new Font("Arial", Font.PLAIN, 25));
		settingsButton.setForeground(new Color(240, 240, 245));
		settingsButton.setBackground(new Color(240, 240, 245));
		settingsButton.setFocusPainted(false);
		settingsButton.setContentAreaFilled(true);
		settingsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		GridBagConstraints gbc_settingsButton = new GridBagConstraints();
		gbc_settingsButton.anchor = GridBagConstraints.WEST;
		gbc_settingsButton.insets = new Insets(20, 25, 5, 5);
		gbc_settingsButton.gridx = 0;
		gbc_settingsButton.gridy = 0;

		displayName.setVerticalAlignment(SwingConstants.BOTTOM);
		displayName.setForeground(new Color(70, 130, 180));
		displayName.setFont(new Font("Arial", Font.PLAIN, 25));
		displayName.setText(base.getFullName());
		GridBagConstraints gbc_displayName = new GridBagConstraints();
		gbc_displayName.anchor = GridBagConstraints.WEST;
		gbc_displayName.gridwidth = 4;
		gbc_displayName.insets = new Insets(20, 10, 5, 5);
		gbc_displayName.gridx = 1;
		gbc_displayName.gridy = 0;

		frequencyLabel.setForeground(new Color(70, 130, 180));
		frequencyLabel.setFont(new Font("Arial", Font.PLAIN, 22));
		frequencyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_frequencyLabel = new GridBagConstraints();
		gbc_frequencyLabel.anchor = GridBagConstraints.NORTH;
		gbc_frequencyLabel.gridwidth = 7;
		gbc_frequencyLabel.insets = new Insets(5, 5, 5, 5);
		gbc_frequencyLabel.gridx = 0;
		gbc_frequencyLabel.gridy = 2;

		frequencySlider.setValue(baseFrequency);
		frequencySlider.setToolTipText("Frequency");
		frequencySlider.setPaintLabels(true);
		frequencySlider.setMaximum(10);
		frequencySlider.setMajorTickSpacing(1);
		frequencySlider.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frequencySlider.setSnapToTicks(true);
		frequencySlider.setForeground(new Color(70, 130, 180));
		frequencySlider.setBackground(new Color(245, 245, 245));
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.gridwidth = 7;
		gbc_slider.insets = new Insets(0, 70, 5, 70);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 3;

		addEquationsTextField.setFont(new Font("Arial", Font.PLAIN, 20));
		addEquationsTextField.setForeground(new Color(0, 0, 128));
		addEquationsTextField.setBackground(new Color(245, 245, 245));
		addEquationsTextField.setToolTipText("Username");
		addEquationsTextField.setBorder(new CompoundBorder(new LineBorder(new Color(70, 130, 180)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_addEquationsTextField = new GridBagConstraints();
		gbc_addEquationsTextField.gridwidth = 3;
		gbc_addEquationsTextField.insets = new Insets(0, 55, 10, 5);
		gbc_addEquationsTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addEquationsTextField.gridx = 2;
		gbc_addEquationsTextField.gridy = 5;

		addEquationsButton.setFont(new Font("Arial", Font.PLAIN, 20));
		addEquationsButton.setForeground(new Color(70, 130, 180));
		addEquationsButton.setBackground(new Color(70, 130, 180));
		addEquationsButton.setFocusPainted(false);
		addEquationsButton.setContentAreaFilled(false);
		addEquationsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_addEquationsButton = new GridBagConstraints();
		gbc_addEquationsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addEquationsButton.insets = new Insets(0, 10, 10, 15);
		gbc_addEquationsButton.gridx = 5;
		gbc_addEquationsButton.gridy = 5;

		removeEquationsTextField.setFont(new Font("Arial", Font.PLAIN, 20));
		removeEquationsTextField.setForeground(new Color(0, 0, 128));
		removeEquationsTextField.setBackground(new Color(245, 245, 245));
		removeEquationsTextField.setToolTipText("Username");
		removeEquationsTextField.setBorder(new CompoundBorder(new LineBorder(new Color(70, 130, 180)), new EmptyBorder(0, 10, 0, 0)));
		GridBagConstraints gbc_removeEquationsTextField = new GridBagConstraints();
		gbc_removeEquationsTextField.gridwidth = 3;
		gbc_removeEquationsTextField.insets = new Insets(0, 55, 5, 5);
		gbc_removeEquationsTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_removeEquationsTextField.gridx = 2;
		gbc_removeEquationsTextField.gridy = 6;

		removeEquationsButton.setFont(new Font("Arial", Font.PLAIN, 20));
		removeEquationsButton.setForeground(new Color(70, 130, 180));
		removeEquationsButton.setBackground(new Color(70, 130, 180));
		removeEquationsButton.setFocusPainted(false);
		removeEquationsButton.setContentAreaFilled(false);
		removeEquationsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_removeEquationsButton = new GridBagConstraints();
		gbc_removeEquationsButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_removeEquationsButton.insets = new Insets(0, 10, 5, 15);
		gbc_removeEquationsButton.gridx = 5;
		gbc_removeEquationsButton.gridy = 6;

		dataSet.setForeground(new Color(0, 0, 128));
		dataSet.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		dataSet.setBackground(new Color(240, 240, 245));
		dataSet.setRowHeight(50);
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
		GridBagConstraints gbc_dataSet = new GridBagConstraints();
		gbc_dataSet.gridwidth = 3;
		gbc_dataSet.gridy = 8;
		gbc_dataSet.insets = new Insets(0, 55, 20, 15);
		gbc_dataSet.fill = GridBagConstraints.BOTH;
		gbc_dataSet.gridx = 3;

		viewRecordsButton.setFont(new Font("Arial", Font.PLAIN, 25));
		viewRecordsButton.setForeground(new Color(70, 130, 180));
		viewRecordsButton.setBackground(new Color(70, 130, 180));
		viewRecordsButton.setFocusPainted(false);
		viewRecordsButton.setContentAreaFilled(false);
		viewRecordsButton.setBorder(new LineBorder(new Color(70, 130, 180), 2, true));
		GridBagConstraints gbc_viewRecordsButton = new GridBagConstraints();
		gbc_viewRecordsButton.gridwidth = 3;
		gbc_viewRecordsButton.anchor = GridBagConstraints.EAST;
		gbc_viewRecordsButton.insets = new Insets(0, 0, 20, 20);
		gbc_viewRecordsButton.gridx = 4;
		gbc_viewRecordsButton.gridy = 10;

		add(settingsButton, gbc_settingsButton);
		add(displayName, gbc_displayName);
		add(frequencyLabel, gbc_frequencyLabel);
		add(frequencySlider, gbc_slider);
		add(addEquationsTextField, gbc_addEquationsTextField);
		add(addEquationsButton, gbc_addEquationsButton);
		add(removeEquationsTextField, gbc_removeEquationsTextField);
		add(removeEquationsButton, gbc_removeEquationsButton);
		add(viewRecordsButton, gbc_viewRecordsButton);
		add(scrollPane, gbc_dataSet);
	}

	private void setUpListeners()
	{
		frequencySlider.addChangeListener(this);

		settingsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.SETTINGS);
			}
		});

		addEquationsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				String newEquation = addEquationsTextField.getText();
				if (newEquation != null && newEquation.length() > 1)
				{
					base.addEquation(newEquation);
					updateQuestionInfo();
					setUpTable();
				}
				addEquationsTextField.setText("");
				addEquationsTextField.requestFocus();
			}
		});

		removeEquationsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				String equation = removeEquationsTextField.getText().trim();
				if (equation != null && equation.length() > 0)
				{
					int location = questionList.indexOf(equation);
					if (location > -1)
					{
						questionList.remove(location);
						numberOfQuestions--;
						questionString = "";
						if (numberOfQuestions > 0)
						{
							for (int i = 0; i < numberOfQuestions; i++)
							{
								questionString = questionString + questionList.get(i) + ":";
							}
							questionString = questionString.substring(0, questionString.length() - 1);
						}
						base.changeCustomEquations(questionString, baseFrequency, numberOfQuestions);
						updateQuestionInfo();
						setUpTable();
					}
					else
					{
						JOptionPane.showMessageDialog(base.messagePanel, "No such equation.", "", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				removeEquationsTextField.setText("");
			}
		});

		viewRecordsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent onClick)
			{
				base.changeState(ViewStates.SELECTSTUDENTRECORDS);
			}
		});
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (e.getSource().equals(frequencySlider))
		{
			sliderChanged();
		}
	}

	private void sliderChanged()
	{
		baseFrequency = base.getFrequency();
		if (!frequencySlider.getValueIsAdjusting())
		{
			int currentValue = frequencySlider.getValue();
			if (baseFrequency != currentValue)
			{
				base.changeCustomEquations(base.getEquationString(), currentValue, base.getNumberOfEquations());
				baseFrequency = base.getFrequency();
				frequencySlider.setValue(baseFrequency);
			}
		}
	}

	private void updateQuestionInfo()
	{
		questionString = base.getEquationString();
		questionList = base.getEquations();
		numberOfQuestions = questionList.size();
	}

	private void updateField()
	{
		int row = dataSet.getSelectedRow();
		String selection = (String) dataSet.getValueAt(row, 0);
		removeEquationsTextField.setText(selection);
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
