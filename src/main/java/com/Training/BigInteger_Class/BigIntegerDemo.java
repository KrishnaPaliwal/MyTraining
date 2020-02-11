package com.Training.BigInteger_Class;

import java.math.BigInteger;

public class BigIntegerDemo {

public static void main(String[] args) {

        // create 2 BigInteger objects
	BigInteger bi1, bi2;

	// create 2 Integer objects
	Integer i1, i2;

	// assign values to bi1, bi2
	bi1 = new BigInteger("123");
	bi2 = new BigInteger("9888486986");

	// assign the integer values of bi1, bi2 to i1, i2
	i1 = bi1.intValue();
/*
The java.math.BigInteger.intValue() converts this BigInteger to an int. 
 this conversion is analogous to a narrowing primitive conversion from long to int.
If this BigInteger is too big to fit in an int, only the low-order 32 bits are returned.
 This conversion can lose information about the overall magnitude of the BigInteger value 
 as well as return a result with the opposite sign.
 */	
	i2 = bi2.intValue();

	String str1 = "Integer value of " +bi1+ " is " +i1;
	String str2 = "Integer value of " +bi2+ " is " +i2;

	// print i1, i2 values
	System.out.println( str1 );
	System.out.println( str2 );
	
	
    // create 3 BigInteger objects
	BigInteger Bi3, Bi4, Bi5;

	// create 3 Boolean objects
	Boolean b1, b2, b3;

	// assign values to BI1, BI2
	Bi3 = new BigInteger("7");
	Bi4 = new BigInteger("9");
	Bi5 = new BigInteger("9");
	
	// perform isProbablePrime on BI1, BI2
	b1 = Bi3.isProbablePrime(1);
	b2 = Bi4.isProbablePrime(1);
	b3 = Bi5.isProbablePrime(-1);

	String str3 = Bi3+ " is prime with certainity 1 is " +b1;
	String str4 = Bi4+ " is prime with certainity 1 is " +b2;
	String str5 = Bi5+ " is prime with certainity -1 is " +b3;

	// print b1, b2, b3 values
	System.out.println( str3 );
	System.out.println( str4 );
	System.out.println( str5 );
	
	// create 2 BigInteger objects
	BigInteger bi6, bi7;

	// create 2 Long objects
	Long l1, l2;

	// assign values to bi1, bi2
	bi6 = new BigInteger("-123");
	bi7 = new BigInteger("988848698666666666666666666666666666666666666");

	// assign the long values of bi1, bi2 to l1, l2
	l1 = bi1.longValue();
	l2 = bi2.longValue();

	String str6 = "Long value of " +bi6+ " is " +l1;
	String str7 = "Long value of " +bi7+ " is " +l2;

	// print l1, l2 values
	System.out.println( str6);
	System.out.println( str7 );
	
	
    }
}