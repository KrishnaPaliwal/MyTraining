package com.learn.lambda;

public class HelloWorldTraditional implements HelloworldInterface {
	
	public String sayHelloWorld(String x, String y) {
		return x+" "+y;
	}
	
	public static void main(String [] args) {
		HelloworldInterface helloWorldInterface = new HelloWorldTraditional();
		
		String s = helloWorldInterface.sayHelloWorld("Hello", "World");
		
		System.out.println(s);
	}

}
