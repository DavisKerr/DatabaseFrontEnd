package data.controller;

import data.view.DataFrame;
/**
 * The class that controlls the program.
 * @author Davis
 * @version 1.0
 */
public class DataAppController 
{
	/**
	 * The base frame for the program.
	 */
	private DataFrame baseFrame;
	/**
	 * The database controller method.
	 */
	private DataController database;
	
	/**
	 * Constructor. Constructs the frame and the database.
	 */
	public DataAppController()
	{
		database = new DataController(this);
		baseFrame = new DataFrame(this);
	}
	
	/**
	 * empty start method. run when the program begins.
	 */
	public void start()
	{
		
	}
	
	/**
	 * Returns the frame.
	 * @return baseFrame The frame.
	 */
	public DataFrame getAppFrame()
	{
		return baseFrame;
		
	}
}
