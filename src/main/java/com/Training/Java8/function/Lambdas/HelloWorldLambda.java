package com.learn.lambda;

public class HelloWorldLambda {

	public static void main(String [] args) {
		
		HelloworldInterface helloWorldInterface = (x,y) ->  x+" "+y;
		
		String s = helloWorldInterface.sayHelloWorld("Hello", "Wolrd");
		
		System.out.println(s);
	}
}
