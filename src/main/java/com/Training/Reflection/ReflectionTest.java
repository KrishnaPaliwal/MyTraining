package com.Training.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {

	public static void main(String[] args) throws Exception {
		
		ReflectionTest rt = new ReflectionTest();
		Class<?> c  = rt.getClass();

		System.out.println(c);
		System.out.println(c.getName());
		System.out.println(c.getSimpleName());
		System.out.println(c.getTypeName());
		
		System.out.println(c.getSuperclass());

		Constructor<?>[] constructors = c.getConstructors();
	    
	    System.out.println(constructors.length);
	    
	    Object obj = constructors[0];
	    
	    System.out.println("obj : "+obj);
	     
	    Method[] methods = c.getMethods();
	    
	    System.out.println(methods.length);

		Package p = c.getPackage();
		  
		System.out.println("Package:"+p.getName());
		  
		// Instance method call
    	Method method1 = ReflectionTest.class.getMethod("testMethod1", null);
		method1.invoke(new ReflectionTest(), null);
	    
		Method method2 = ReflectionTest.class.getMethod("testMethod2", String.class);
		method2.invoke(new ReflectionTest(), "is passed");
		 
		Method method3 = ReflectionTest.class.getMethod("testMethod3", int.class);
		method3.invoke(null, 12);
		
		Method method4 = ReflectionTest.class.getDeclaredMethod("testMethod4", Double.class);
		
		method4.invoke(new ReflectionTest(), 5.5);
	}
	
	public void testMethod1() {
		System.out.println("testMethod 1 called");
	}
	
	public void testMethod2(String s) {
		System.out.println("testMethod 2 called "+s);
	}
	
	public static void testMethod3(int a) {
		System.out.println("testMethod 3 called with "+a);
	}
	
	public static void testMethod4(Double b) {
		System.out.println("testMethod 4 called with "+b);
	}
}
