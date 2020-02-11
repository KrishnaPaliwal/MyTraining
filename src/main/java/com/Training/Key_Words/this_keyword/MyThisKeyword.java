package com.Training.Key_Words.this_keyword;

class Frog
{
	private String name;
	private int age;

	public void setName(String name)
	{
		this.name = name; 
	}
	
	public void setAge(int myage)
	{
		age = myage;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setInfo(String name, int age)
	{
		setName(name);
		setAge(age);
		
		
	}

}


public class MyThisKeyword {

	public static void main(String[] args)
	{
		Frog frog = new Frog();
		
		// Error : not visible
		//frog.name = "Krishna";
		//frog.age = 29;
		
		frog.setAge(2);
		frog.setName("Krishna");
		System.out.println(frog.getAge());
		System.out.println(frog.getName());
	}
	
}
