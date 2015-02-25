package data.view;

import javax.swing.JFrame;

import data.controller.DataAppController;

/**
 * The frame for the gui.
 * @author Davis
 * @version 1.0
 */
public class DataFrame extends JFrame
{
	
	/**
	 * the DataAppController.
	 */
	private DataAppController baseController;
	/**
	 * the panel
	 */
	private DataPanel basePanel;
	
	/**
	 * Constructor: builds the panel and then runs the setupFrame() method.
	 * @param baseController Links to the controller
	 */
	public DataFrame(DataAppController baseController)
	{
		basePanel = new DataPanel(baseController);
		setupFrame();
	}
	
	/**
	 * sets up the frame.
	 */
	private void setupFrame() 
	{
		
		this.setSize(1000, 900);
		this.setResizable(true);
		this.setContentPane(basePanel);
		this.setVisible(true);
		
	}
}
