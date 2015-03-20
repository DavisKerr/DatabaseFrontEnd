package data.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import data.controller.DataAppController;
import data.view.TableCellWrapRenderer;

/**
 * The panel for the gui. It goes inside the frame.
 * @author Davis
 * @version 1.3 Improved gui.
 */
public class DataPanel extends JPanel
{
	/**
	 * the controller.
	 */
	private DataAppController baseController;
	/**
	 * the way the program is layed out.
	 */
	private SpringLayout baseLayout;
	/**
	 * Submits the query.
	 */
	private JButton appButton;
	/**
	 * Where the query is typed in.
	 */
	private JTextField queryArea;
	/**
	 * The pane that holds the table.
	 */
	private JScrollPane tablePane;
	/**
	 * The table that holds the data.
	 */
	private JTable databaseView;
	/**
	 * The model for the table.
	 */
	private DefaultTableModel model;
	/**
	 * The cellrenderer that formats the table.
	 */
	private TableCellRenderer myCellRenderer;
	/**
	 * The label for thelogin area.
	 */
	private JLabel loginLabel;
	/**
	 * the login button *NOT USED*
	 */
	private JButton loginButton;
	
	/**
	 * Sets the baseController, creates the spring layout, and runs the helper methods.
	 * @param baseController The controller.
	 */
	public DataPanel(DataAppController baseController)
	{
		
		this.baseController = baseController;
		myCellRenderer = new TableCellWrapRenderer();
		appButton = new JButton("Test query");
		databaseView = new JTable();
		//tablePane = new JScrollPane(databaseView);
		baseLayout = new SpringLayout();
		loginLabel = new JLabel("change the database here:");
		queryArea = new JTextField(25);
		model = new DefaultTableModel();
		loginButton = new JButton("Change active database");
		
		
		setupTable(baseController.getDatabase().getResultArray());
		//setupTablePane();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	/**
	 * sets the color of the pane.
	 */
	private void setupTablePane() 
	{
	tablePane.setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Sets the placement of all the objects in the panel.
	 */
	private void setupLayout() 
	{
		baseLayout.putConstraint(SpringLayout.NORTH, queryArea, 6, SpringLayout.SOUTH, tablePane);
		baseLayout.putConstraint(SpringLayout.WEST, tablePane, 166, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.EAST, tablePane, 701, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, appButton, 403, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, appButton, 21, SpringLayout.SOUTH, queryArea);
		baseLayout.putConstraint(SpringLayout.WEST, queryArea, 345, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, tablePane, -201, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, loginLabel, 4, SpringLayout.NORTH, loginButton);
		baseLayout.putConstraint(SpringLayout.EAST, loginLabel, -43, SpringLayout.WEST, loginButton);
		baseLayout.putConstraint(SpringLayout.NORTH, loginButton, 52, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, loginButton, 0, SpringLayout.EAST, appButton);
	}

	/**
	 * sets up the listeners for user input, button clicks, etc.
	 */
	private void setupListeners() 
	{
		
		appButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent click) 
			{
				String query = queryArea.getText();
				baseController.getDatabase().sendQuery(query);
				refreshTable(query);
			}
			
		});
		
		loginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent click)
			{
				
				DataPopupFrame popup = new DataPopupFrame(baseController);
//				baseController.changeDatabase();
//				String query = "SHOW TABLES";
//				baseController.getDatabase().sendQuery(query);
//				refreshTable(query);
				
			}
		});
		
	}
	
	/**
	 * sets up the panel and its elements.
	 */
	private void setupPanel() 
	{
		
		this.setSize(1000, 900);
		this.setBackground(Color.LIGHT_GRAY);
		this.setVisible(true);
		this.setLayout(baseLayout);
		this.add(appButton);
		this.add(queryArea);
		this.add(tablePane);
		this.add(loginLabel);
		this.add(loginButton);
		
	}
	/**
	 * The default table.
	 * @param values the values to fill the table.
	 */
	private void setupTable(String[][] values)
	{
		
		
		databaseView = new JTable(new DefaultTableModel(values, baseController.getDatabase().getMetaData("SHOW TABLES") ));
		tablePane = new JScrollPane(databaseView);
		
		
		
		for(int col = 0; col < baseController.getDatabase().getMetaData("SHOW TABLES").length; col++)
		{
			databaseView.getColumnModel().getColumn(col).setCellRenderer(myCellRenderer);
		}
	}
	/**
	 * Refreshes the data everytime the table needs to change.
	 * @param query
	 */
	private void refreshTable(String query)
	{
		
		model.setDataVector(baseController.getDatabase().getDataArray(), baseController.getDatabase().getMetaData(query));
		databaseView.setModel(model);
		
		for(int col = 0; col < baseController.getDatabase().getMetaData(query).length; col++)
		{
			databaseView.getColumnModel().getColumn(col).setCellRenderer(myCellRenderer);
		}
	}
	
}
