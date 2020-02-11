package com.Training.Comparator_and_Comparable;

public class EMP implements Comparable<EMP>{
	
	private String name;
	private int age;

	public EMP(String name, int age) 
	{
		this.name = name;
		this.age = age;
	}
	
	public String getName()
	{
		return name;
	}

	public int getAge()
	{
		return age;
	}
	
	@Override
	public int compareTo(EMP obj)
	{	EMP emp = (EMP)obj;
		if(this.age>emp.age) return 1;
		else if(this.age<emp.age) return -1;	
		else return 0;
		
		
	}

}

