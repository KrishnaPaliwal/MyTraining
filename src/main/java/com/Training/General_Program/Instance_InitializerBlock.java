package com.myTraining.General_Program;

public class Instance_InitializerBlock {

    int speed;  
    
    Instance_InitializerBlock(){System.out.println("speed is "+speed);}  
   
    {speed=100;}  // THIS IS instance initializer block
       
    public static void main(String args[]){  
    Instance_InitializerBlock b1=new Instance_InitializerBlock();  
    Instance_InitializerBlock b2=new Instance_InitializerBlock();  
    }

}

/*
Rules for instance initializer block :
	There are mainly three rules for the instance initializer block. They are as follows:

	    The instance initializer block is created when instance of the class is created.
	    
	    The instance initializer block is invoked after the parent class constructor is
	    invoked (i.e. after super() constructor call).
	    
	    The instance initializer block comes in the order in which they appear.
*/