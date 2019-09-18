package com.myTraining.Key_Words.super_keyword;

//Here is a subclass, called Subclass, that overrides printMethod():

	public class Subclass extends Superclass {
		
		int a = 200;

	    // overrides printMethod in Superclass
	    public void printMethod() {
	        super.printMethod();
	        System.out.println("Printed in Subclass");
	        
	        System.out.println("a variable in super class  "+super.a);
	        System.out.println("a variable in sub-class  "+a);
	    }
	    public static void main(String[] args) {
	        Subclass s = new Subclass();
	        s.printMethod();
	        
	    }
	}
