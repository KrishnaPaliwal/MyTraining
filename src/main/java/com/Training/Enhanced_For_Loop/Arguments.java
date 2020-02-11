package com.Training.Enhanced_For_Loop;

import com.Training.enum_type.DayEnum;
import com.Training.enum_type.EnumTest;

public class Arguments {

	public void method() {    
		EnumTest rr = new EnumTest();
	   rr.tellItLikeItIs();
	}

	public static void main(String[] args) {
		    for (String t: args) {
		      System.out.println(t);

		    }
	  	}
}
