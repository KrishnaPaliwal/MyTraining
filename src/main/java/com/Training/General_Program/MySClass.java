package com.Training.General_Program;

public class MySClass {
	private int X;
	private char C;
	private String S;
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public char getC() {
		return C;
	}
	public void setC(char c) {
		C = c;
	}
	public String getS() {
		return S;
	}
	public void setS(String s) {
		S = s;
	}
	
	public void display()
	{
		System.out.println("X is "+X);
		
		System.out.println("c is "+C);
		
		System.out.println("S is "+S);		 
		
	}

}
