package com.myTraining.Enhanced_For_Loop;

import com.myTraining.enum_type.DayEnum;
import com.myTraining.enum_type.EnumTest;

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
