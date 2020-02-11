package com.Training.General_Program;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RemoveDupWOUsngSetFrmLst {
	
	public static void main(String [] ar)
	{	
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("A");
		list.add("b");
		list.add("A");
		list.add("C");
		DuplicateElement del = new DuplicateElement();
		List<String> loist =del.removeDuplicate(list);
		
		for(String st : loist)
		{
			System.out.println(st);
		}
	}
	
}

class DuplicateElement{
	
	public List<String> removeDuplicate(List<String> list)
	
	{
		List<String> tempList = new ArrayList<String>();
		Iterator it = list.iterator();
		while(it.hasNext())
		{	
			
			String str = (String) it.next();
			if(list.contains(str))	
			it.remove();	
			
			else tempList.add((String) it.next());
		}

		
		return tempList;
	}
	
}
