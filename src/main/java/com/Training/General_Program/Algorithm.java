package com.myTraining.General_Program;

import java.math.BigInteger;
import java.util.List;

class Algorithm {
	
	Algorithm(String x)
	{
		String Y=x+1;
		System.out.println(Y);
	}

	public static Integer max(Integer x, Integer y) {
        return x > y ? x : y;
    }
    
    public static void print(List<? extends Number> list) {
        for (Number n : list)
            System.out.print(n + " ");
        System.out.println();
    }
   // BigInteger l = new BigInteger();
    Short s = new Short((short)5);
    
    public static void main(String args[])
    {
    	Algorithm lg = new Algorithm("ffef");
    	
    	Algorithm.InnerAlgorithm ih = new Algorithm.InnerAlgorithm();
    	//System.out.println(ih.myMethod());
    	
    	class InnerAlgorithm
    	{
    		void myMethod()
    		{
    			
    		}
    }
    	
    }
   
    
}
