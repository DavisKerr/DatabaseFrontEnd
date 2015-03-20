package data.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import data.controller.DataAppController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataPopupPanel extends JPanel
{
	private JTextArea databaseInfo;
	private JScrollPane container;
	private JTextField dbNameField;
	private JButton submitButton;
	private JButton cancelButton;
	private SpringLayout baseLayout;
	private DataAppController baseController;
	
	public DataPopupPanel(DataAppController baseController)
	{
		databaseInfo = new JTextArea(5, 5);
		container = new JScrollPane(databaseInfo);
		dbNameField = new JTextField(20);
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		baseLayout = new SpringLayout();
		this.baseController = baseController;
		
		setupPanel();
		setupLayout();
		setupInfoBox();
		setupListeners();
		
	}

	private void setupInfoBox() 
	{
		
		databaseInfo.setEditable(false);
		container.setWheelScrollingEnabled(true);
		String results = baseController.getDatabase().displayDBS();
		databaseInfo.setText(results);
	}

	private void setupLayout() 
	{
		baseLayout.putConstraint(SpringLayout.NORTH, container, 22, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, container, 101, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.EAST, container, -72, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, dbNameField, 169, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, cancelButton, -151, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.EAST, dbNameField, -151, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, container, -6, SpringLayout.NORTH, dbNameField);
		baseLayout.putConstraint(SpringLayout.NORTH, submitButton, 6, SpringLayout.SOUTH, dbNameField);
		baseLayout.putConstraint(SpringLayout.WEST, submitButton, 0, SpringLayout.WEST, dbNameField);
		baseLayout.putConstraint(SpringLayout.NORTH, cancelButton, 6, SpringLayout.SOUTH, dbNameField);
		
	}

	private void setupPanel() 
	{
		this.setSize(500, 250);
		this.setBackground(Color.LIGHT_GRAY);
		this.setVisible(true);
		this.setLayout(baseLayout);
		this.add(container);
		this.add(dbNameField);
		this.add(submitButton);
		this.add(cancelButton);
		
	}

	private void setupListeners() 
	{
		
		
		
	}
}
