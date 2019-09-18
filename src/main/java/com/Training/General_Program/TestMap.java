package com.myTraining.General_Program;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestMap {
	
	public int addMethd(int a, int b)
	{
		return (a+b);
	}
	public static void main(String [] args)
	{
	Map<dummy, String> dm = new HashMap<dummy, String>();
	
	dm.put(new dummy("Krishna"), "Good Boy");
	dm.put(new dummy("Paliwal"), "Intelligent Boy");
	dm.put(new dummy("Krishna"), "Intelligent Boy");
	
	Iterator it = dm.values().iterator();
	
	while(it.hasNext()){
		
		System.out.println(it.next());
	}
	
/*	Map<String, String> dm2 = new HashMap<String, String>();
	
	dm2.put("Krishna", "Good Boy");
	dm2.put("Krishna", "Intelligent Boy");
	
	Iterator it2 = dm.values().iterator();
	
	while(it2.hasNext()){
		System.out.println(it2.next());
	}*/

	}
}

class dummy
{
	private String name;
	
	public dummy()
	{}
	
	public dummy(String name)
	{
		this.name = name;
	}
	

}