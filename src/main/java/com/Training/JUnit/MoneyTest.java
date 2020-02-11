package com.Training.JUnit;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MoneyTest extends TestCase {


	public void testSampleAdd() throws Exception
	{
		Money m12CHF  = new Money(12, "CHF");
		Money m14CHF  = new Money(14, "CHF");
		
		Money result = m12CHF.add(m14CHF);
	
		Money expected = m12CHF.add(m14CHF);
		
		assertEquals(result, expected);
		
		tearDown();

	}
}
