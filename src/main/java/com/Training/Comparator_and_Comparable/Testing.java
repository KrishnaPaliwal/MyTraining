package com.Training.Comparator_and_Comparable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Testing {
	
	public static void main(String eee[])
	{
		String s1="ABC";
		String s2="abc";
		System.out.println(s1=s2);
		
		List<EMP> listemp = new LinkedList<EMP>();
		
		EMP emp1 = new EMP("Krishna", 23);
		EMP emp2 = new EMP("Kumar", 53);
		EMP emp3 = new EMP("Paliwal", 13);
		
		listemp.add(emp1);
		listemp.add(emp2);
		listemp.add(emp3);
		
		Collections.sort(listemp);
		for(EMP emp : listemp)
		{
			System.out.println((emp.getName()+emp.getAge()));
		}
		Collections.sort(listemp, new EMPnameComparator());
		
		for(EMP emp : listemp)
		{
			System.out.println((emp.getName()+emp.getAge()));
		}
	}
	
	

}
