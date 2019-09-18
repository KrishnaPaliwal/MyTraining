package com.myTraining.General_Program;

public class instanceof_Example {

    public static void main(String[] args) {
        Child child = new Child();
        if (child instanceof Parent && child instanceof GrandFather && child instanceof Child) {
            System.out.println("True");
        } else {
        	System.out.println("False");
        }
    }
}

class GrandFather
{
	public void ABC()
	{
		int X;
	}
	
}
class Parent extends GrandFather{
    public Parent() {}
    
	public void ABC()
	{
		int XYZ;
	}
}

class Child extends Parent {
    public Child() {
        super();
    }
}


