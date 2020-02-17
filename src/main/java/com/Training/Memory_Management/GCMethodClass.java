package com.Training.Memory_Management;

import com.Training.Memory_Management.EscapingReferences.Customer;

public class GCMethodClass {

	public static void main(String [] args) {
		
		Runtime runtime = Runtime.getRuntime();
		
		long availableBytes = runtime.freeMemory();
		
		System.out.println("Free Memory: "+availableBytes/1024+"Kb");
		
		for(int i=0; i<=1000000000; i++) {
			
			for(int j=0; j<=100000; j++) {
				
				Customer c;
				c = new Customer(Integer.toString(i));
			}
			availableBytes = runtime.freeMemory();
			System.out.println("Free Memory: "+availableBytes/1024+"Kb");
		}
		
		availableBytes = runtime.freeMemory();
		System.out.println("Free Memory: "+availableBytes/1024+"Kb");
		
		System.gc();
		
		availableBytes = runtime.freeMemory();
		System.out.println("Free Memory: "+availableBytes/1024+"Kb");

	}
	
}
