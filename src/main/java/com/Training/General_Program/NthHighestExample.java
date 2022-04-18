package com.test;

import java.io.Console;
import java.util.Scanner;

public class NthHighestExample {

	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		String nthHighest = sc.next();
		
		int result = getNumber(Integer.parseInt(nthHighest));
		System.out.println(nthHighest+ "th Highest is : "+ result);

	}

	static int getNumber(int highest) {

		int result = 0;
		int[] array = new int[] { 2, 3,9, 10 , 9, 9, 10, 5, 6, 11, 7, 10, 10 };
		int temp = 0;

		for (int i = 0; i < array.length; i++) {
			for (int j = 1; j < array.length - i; j++) {
				if (array[j] > array[j - 1]) {
					temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
			}
		}
		int increament = 1;
		for (int i = 0; i<array.length; i++) {

			if (i<(array.length-1) && array[i] != array[i+1] && increament != highest)
				increament++;
			else if(increament == highest) {
				result = array[i];
				break;
			}
		}
		return result;
	}
}
