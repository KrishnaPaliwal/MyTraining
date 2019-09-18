package com.myTraining.enum_type;

public class EnumHowTo {
	
public enum Language{ 
	JAVA(1), PYTHON(2), PERL(3), SCALA(4);
	private int rank; 
	private Language(int rank)
	{ this.rank = rank; } 
	
	public int getRank(){ return rank; } };
	
	public static void main(String args[]) 
	
	{ System.out.println("Java Enum Iterate Example using for loop"); 
	
	for(Language pl : Language.values()){ System.out.println( pl.name() + " : " + pl.rank); } } }

