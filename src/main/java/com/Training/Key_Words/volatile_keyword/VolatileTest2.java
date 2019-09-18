package com.myTraining.Key_Words.volatile_keyword;

import java.util.logging.Logger;
/**
* @author Krishna
*/
public class VolatileTest2 {

	private static Logger LOGGER = Logger.getLogger(VolatileTest2.class.getName());
	private volatile static int MY_INT=0;
	
	public static void main(String ... args)
	{
		new ChangeListner().start();
		new ChangeMaker().start();
	}
	
	static class ChangeListner extends Thread 
	{
		@Override
		public void run()
		{
			int local_value = MY_INT;
			while(local_value<5)
			{
				if(local_value!=MY_INT)
				{
					LOGGER.info("we got change");
					local_value= MY_INT;
				}
			}
		}
		
	}
	
	static class ChangeMaker extends Thread 
	{
		
		@Override
		public void run()
		{	int local_value=MY_INT;
			while(MY_INT<5)
			{
				LOGGER.info("MY_INT incremented");
				
				MY_INT = ++local_value;
			}
			
		}
	}
}
