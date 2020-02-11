
package com.Training.ARRAY_int_char_String_Character_Boolean;

import java.lang.reflect.Array;
import java.util.Arrays;

@SuppressWarnings("unused")
public class int_char_String_Character_Boolean_Array{
	  public static void main(String[] args){
	    int   i = 0;
	    int[] a = {8,1,2, 3, 6, 8, 4, 3};
	    byte b3 = 123;
	    a[i] = i = 2;
	    System.out.println(i + " " + a[0]+ " "+a[1]);
	    
	    int[] b = new int[] {8,1,2,3};
	    
	    //Sorting 
	    Arrays.sort(a);
	    for (int x : a)
	    {
	    	System.out.println(x);
	    }
	    
	    int   j = 0;
	    Boolean[] B = new Boolean[] {true,false, null};
	    B[j] = 8 == 2;
	    System.out.println(j + " " + B[0]+ " "+B[1]);
	    
	    //Three ways to initialize any type of array
	    String [] st = new String[122];
	    st[0] = "KRISHNA";
	    System.out.println(st[0]);
	    
	    String[] errorSoon = {"Hello", "World"};
	    System.out.println(errorSoon[0]);
	    
		String[] st2= new String[] {"ACCEPT", "APPROVE"} ;
		System.out.println(st2[1]);	
	   

		String s1 = "abc";
 		String s2 = "abc";
		System.out.println("s1 == s2 is:" + s1 == s2);   
	    
	    
	    
	  }
	}