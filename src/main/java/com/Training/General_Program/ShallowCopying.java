package com.myTraining.General_Program;

public class ShallowCopying {
	
	int Marks = 40;
	
	public static void main(String args[])
	{
		ShallowCopying ShC1 = new ShallowCopying();
		
		ShallowCopying ShC2 = new ShallowCopying();
	
		ShC1.Marks=50;
		
		ShC2 =  ShC1;
	
		ShC1.clone();
		System.out.println(ShC1.Marks);
		System.out.println(ShC2.Marks);
		
	}
	

}
