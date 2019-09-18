package com.myTraining.General_Program;

import java.math.BigInteger;

public class InstanceCounter {


	   private static void addInstance() {
	      
	   }

	   public static void main(String[] arguments) {
		   int i;
	      for (i = 1; i<5000000; ++i){
	         new InstanceCounter();
		  }
	      System.out.println("Created " +i+ " instances");
	   }
}
