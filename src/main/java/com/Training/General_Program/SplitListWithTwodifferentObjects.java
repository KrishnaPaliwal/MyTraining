package com.Training.General_Program;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SplitListWithTwodifferentObjects 
{

	public void splitList(List<Address> list)
	{
		for(Address add : list )
				
		{
			if(add instanceof HomeAddress )
			{
				
				System.out.println(add);
			}
			

		}
	}	

	public static void main(String ttt [])
	{
		List<Address> list = new ArrayList<Address>();
		
		list.add(new HomeAddress());
		list.add(new HomeAddress());
		list.add(new HomeAddress());
		list.add(new OfficeAddress());
		
		SplitListWithTwodifferentObjects SLWTdO = new SplitListWithTwodifferentObjects();
		
		SLWTdO.splitList(list);
		
		List<Object> objList = new ArrayList<Object>();
		objList.add(12345);
		objList.add("Krishna");
		objList.add(new HomeAddress());
		objList.add(new OfficeAddress());
		objList.add(null);
		
		for(Object obj : objList)
		{
			System.out.println(obj);
		}
		
	}
}
