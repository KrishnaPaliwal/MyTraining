package com.Training.PassingValues.PremitiveTypes;

public class PassPremitive {

	public static void main(String [] args) {
		
		int num = 10;
		System.out.println("num:"+num);
		
		int newNum = changeNum(num);
		
		System.out.println("newNum:"+newNum);
		System.out.println("num:"+num);
		
	}
	
	public static int changeNum(int num) {
		num = num*2;
		return num;
	}
}
