package com.myTraining.Comparator_and_Comparable;

import java.util.Comparator;

public class EMPnameComparator implements Comparator<EMP> {
	
	@Override
	public int compare(EMP obj1, EMP obj2)
	{
			return obj1.getName().compareTo(obj2.getName());
			//return obj1.getAge()-obj2.getAge();
		
	}
	

}
