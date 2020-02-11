package com.Training.ClassLoader_Class;

public class ClassLoaderTest {

	public static void main(String[] args) {

		System.out.println("class loader for HashMap: "
				+ java.util.HashMap.class.getClassLoader());
		
		System.out.println("class loader for this class: "
				+ ClassLoaderTest.class.getClassLoader());

	}

}
