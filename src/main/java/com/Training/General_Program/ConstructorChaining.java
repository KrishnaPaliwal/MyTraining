package com.myTraining.General_Program;


public class ConstructorChaining {
	
	public ConstructorChaining(){
	        System.out.println("In default constructor...");
	    }
	public ConstructorChaining(int i){
	        this();
	        System.out.println("In single parameter constructor...");
	    }
	public ConstructorChaining(int i,int j){
	        this(j);
	        System.out.println("In double parameter constructor...");
	    }
	     
	public static void main(String a[]){
	        ConstructorChaining ch = new ConstructorChaining(10,20);
	    }
	}

/*
In Java you can call one constructor from another and it’s known as constructor chaining in Java.
*/