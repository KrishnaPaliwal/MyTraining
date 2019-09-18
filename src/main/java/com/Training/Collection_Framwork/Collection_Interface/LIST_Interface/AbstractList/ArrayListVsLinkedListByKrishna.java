package com.myTraining.Collection_Framwork.Collection_Interface.LIST_Interface.AbstractList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArrayListVsLinkedListByKrishna {

	public static Logger LOGGER = Logger.getLogger(ArrayListVsLinkedListByKrishna.class.getName());
	public static void main(String ... rr)
	{
		List<Integer> arList = new ArrayList<>();
		List<Integer> lList = new LinkedList<>();
		
		long startTime;
		long endTime;
		
		startTime = System.nanoTime();
		for(int i=0 ; i<100000; i++)
		{
			arList.add(i);
		}
		endTime = System.nanoTime();
		LOGGER.log( Level.INFO, "ArrayList Addition time : {0}",endTime-startTime);
	
		
		startTime = System.nanoTime();
		for(int i=0 ; i<100000; i++)
		{
			lList.add(i);
		}
		endTime = System.nanoTime();
		LOGGER.log( Level.INFO, "LinkedList Addition time : {0}",endTime-startTime);
		
		
		
		startTime = System.nanoTime();
		for(int i=0 ; i<100000; i++)
		{
			arList.get(i);
		}
		endTime = System.nanoTime();
		LOGGER.log( Level.INFO, "ArrayList retrival time : {0}",endTime-startTime);
	
		
		startTime = System.nanoTime();
		for(int i=0 ; i<100000; i++)
		{
			lList.get(i);
		}
		endTime = System.nanoTime();
		LOGGER.log( Level.INFO, "LinkedList retrival time : {0}",endTime-startTime);
				
	}
	
	
}
