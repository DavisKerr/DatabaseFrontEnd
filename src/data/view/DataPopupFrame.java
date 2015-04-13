package data.view;

import javax.swing.JFrame;

import data.controller.DataAppController;

public class DataPopupFrame extends JFrame
{
	/**
	 * the DataAppController.
	 */
	private DataAppController baseController;
	/**
	 * the panel
	 */
	private DataPopupPanel popupPanel;
	
	/**
	 * Constructor: builds the panel and then runs the setupFrame() method.
	 * @param baseController Links to the controller
	 */
	public DataPopupFrame(DataAppController baseController, DataPanel basePanel)
	{
		popupPanel = new DataPopupPanel(baseController, basePanel);
		setupFrame();
		
	}
	
	/**
	 * sets up the frame.
	 */
	private void setupFrame() 
	{
		
		this.setSize(400, 250);
		this.setResizable(true);
		this.setContentPane(popupPanel);
		this.setVisible(true);
		
	}
}
