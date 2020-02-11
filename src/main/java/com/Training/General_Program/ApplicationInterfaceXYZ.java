package com.Training.General_Program;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

interface X {

	void methodX();
}

class Y implements X
{
	public void methodX()
	{
		System.out.println("methodX called");
	}
	
	public void ClassYsMethod()
	{
		System.out.println("ClassYsMethod caled");
	}
}

public class ApplicationInterfaceXYZ {
	private static final String Y = null;

	public static void main(String arg[])
	{
		X x = new Y();
		
		x.methodX();
		
	((Y)x).ClassYsMethod();
		

	
	List<String> list = new ArrayList<String>();
		
	((LinkedList<String>) list).peek();
		
	((ArrayList<String>) list).ensureCapacity(5);
		
		
	LinkedList<String> linkedList = new LinkedList<String>();
	linkedList.peek();	
	
	//linkedList.ensureCapacity(556); 
	
	//((ArrayList<String>) linkedList).ensureCapacity(556);
	
		
		
	ArrayList<String> arrayList = new ArrayList<String>();
	arrayList.ensureCapacity(556);
		
		
	}

}
