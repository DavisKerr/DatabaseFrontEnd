package data.view;

import java.awt.Color;

import javax.swing.*;

import data.controller.DataAppController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DataPopupPanel extends JPanel
{
	private JTextArea databaseInfo;
	private JButton submitButton;
	private JButton cancelButton;
	private SpringLayout baseLayout;
	private DataAppController baseController;
	private DataPanel basePanel;
	private JComboBox selecter;
	
	public DataPopupPanel(DataAppController baseController, DataPanel basePanel)
	{
		databaseInfo = new JTextArea(5, 5);
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		baseLayout = new SpringLayout();
		
		selecter = new JComboBox();
		
		this.baseController = baseController;
		this.basePanel = basePanel;
		
		setupSelecter();
		setupPanel();
		setupLayout();
		setupInfoBox();
		setupListeners();
		
	}

	private void setupSelecter() 
	{
		String[] results = baseController.getDatabase().displayDBS();
		selecter.setModel(new DefaultComboBoxModel(baseController.getDatabase().displayDBS()));
		
	}

	private void setupInfoBox() 
	{
		
		databaseInfo.setEditable(false);
		//String[] results = baseController.getDatabase().displayDBS();
		
	}

	private void setupLayout() 
	{
		baseLayout.putConstraint(SpringLayout.WEST, selecter, 92, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, selecter, -89, SpringLayout.NORTH, submitButton);
		baseLayout.putConstraint(SpringLayout.NORTH, cancelButton, 0, SpringLayout.NORTH, submitButton);
		baseLayout.putConstraint(SpringLayout.WEST, cancelButton, 114, SpringLayout.EAST, submitButton);
		baseLayout.putConstraint(SpringLayout.SOUTH, submitButton, -19, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, submitButton, 32, SpringLayout.WEST, this);
	}

	private void setupPanel() 
	{
		this.setSize(400, 250);
		this.setBackground(Color.LIGHT_GRAY);
		this.setVisible(true);
		this.setLayout(baseLayout);
		this.add(submitButton);
		this.add(cancelButton);
		this.add(selecter);
		
	}

	private void setupListeners() 
	{
		
		submitButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent click) 
			{
				baseController.changeDatabase((String) (selecter.getItemAt(selecter.getSelectedIndex())));
				baseController.getDatabase().sendQuery("SHOW TABLES");
				basePanel.refreshTable("SHOW TABLES");
				basePanel.setupSelecter();
				basePanel.setClose();
			}
			
		});
		
		cancelButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent click) 
			{
				basePanel.setClose();
			}
			
		});
		
	}
}
