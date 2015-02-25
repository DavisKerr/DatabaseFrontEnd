package data.view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import data.controller.DataAppController;

/**
 * The panel for the gui. It goes inside the frame.
 * @author Davis
 * @version 1.0
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
	 * Sets the baseController, creates the spring layout, and runs the helper methods.
	 * @param baseController The controller.
	 */
	public DataPanel(DataAppController baseController)
	{
		
		this.baseController = baseController;
		baseLayout = new SpringLayout();
		
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	/**
	 * Sets the placement of all the objects in the panel.
	 */
	private void setupLayout() 
	{
		
		
		
	}

	/**
	 * sets up the listeners for user input, button clicks, etc.
	 */
	private void setupListeners() 
	{
		
		
		
	}
	
	/**
	 * sets up the panel and its elements.
	 */
	private void setupPanel() 
	{
		
		this.setSize(1000, 900);
		this.setBackground(Color.LIGHT_GRAY);
		this.setVisible(true);
		
	}
}
