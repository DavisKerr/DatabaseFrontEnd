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
 * @version 1.2
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
	 * The username box.
	 */
	private JTextField username;
	/**
	 * the password box.
	 */
	private JPasswordField password;
	/**
	 * The label for the username box.
	 */
	private JLabel userLabel;
	/**
	 * The label for thelogin area.
	 */
	private JLabel loginLabel;
	/**
	 * The label for the password box.
	 */
	private JLabel passwordLabel;
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
		password = new JPasswordField(null, 20);
		loginLabel = new JLabel("Login to the database here:");
		userLabel = new JLabel("username:");
		passwordLabel = new JLabel("Password:");
		username = new JTextField(20);
		
		queryArea = new JTextField(25);
		model = new DefaultTableModel();
		loginButton = new JButton("Login");
		
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
		baseLayout.putConstraint(SpringLayout.NORTH, password, 104, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, username, -8, SpringLayout.NORTH, userLabel);
		baseLayout.putConstraint(SpringLayout.EAST, username, 0, SpringLayout.EAST, password);
		baseLayout.putConstraint(SpringLayout.WEST, passwordLabel, 23, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.EAST, userLabel, 0, SpringLayout.EAST, passwordLabel);
		baseLayout.putConstraint(SpringLayout.WEST, password, 25, SpringLayout.EAST, passwordLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 8, SpringLayout.NORTH, password);
		baseLayout.putConstraint(SpringLayout.WEST, loginLabel, 23, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, userLabel, 80, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, loginButton, 6, SpringLayout.SOUTH, password);
		baseLayout.putConstraint(SpringLayout.WEST, loginButton, 186, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, loginLabel, -30, SpringLayout.SOUTH, username);
		baseLayout.putConstraint(SpringLayout.NORTH, tablePane, 134, SpringLayout.SOUTH, password);
		baseLayout.putConstraint(SpringLayout.SOUTH, tablePane, -201, SpringLayout.SOUTH, this);
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
				
				setupTable(baseController.getDatabase().getResultArray());
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
		this.add(passwordLabel);
		this.add(loginLabel);
		this.add(userLabel);
		this.add(password);
		this.add(loginButton);
		password.setEchoChar('~');
		password.setForeground(Color.GREEN);
		password.setFont(new Font("Serif", Font.BOLD, 15));
		this.add(username);
		username.setFont(new Font("Serif", Font.PLAIN ,15));
		
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
