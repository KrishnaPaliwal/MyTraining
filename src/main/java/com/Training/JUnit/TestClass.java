package com.myTraining.JUnit;

import com.myTraining.General_Program.TestMap;

import junit.framework.TestCase;


public class TestClass extends TestCase {

	public void testAddMethod()
	{
		TestMap st = new TestMap();
		
		int result = st.addMethd(2, 4);
		
		assertEquals(result, 7);
		
		
	}
	
}
