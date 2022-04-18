package com.Training.General_Program;

//Java program to bubble sort 

public class BubbleSort {

	public static void main(String[] args) {
		
		int[] array = new int[] { 2, 3,9, 10 , 9, 9, 10, 5, 6, 11, 7, 10, 10 };
		
		System.out.print(" Befor Sorting : ");
		for (int i = 0; i < array.length; i++) {
			System.out.print(" ");
			System.out.print(array[i]);
		}
		int temp = 0;

		for (int i = 0; i < array.length; i++) {
			for (int j = 1; j < array.length - i; j++) {
				if (array[j] < array[j - 1]) {
					temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
			}
		}
		
		System.out.print(" \n After Sorting : ");
		for (int i = 0; i < array.length; i++) {
			System.out.print(" ");
			System.out.print(array[i]);
		}

	}
}
/*
 * Complexity of bubble sort is O(n2) which makes it a 
 * less frequent option for arranging in sorted order when
 *  quantity of numbers is high.
 */
