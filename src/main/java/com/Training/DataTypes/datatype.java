package com.Training.DataTypes;

public class datatype {
	
	
	
	public static void main(String args[])
	{
		//Primitive Data Types:
		
	/*	Byte :
	  	 Byte data type is an 8-bit signed two's complement integer.
		 Byte data type is used to save space in large arrays,
		 mainly in place of integers, since a byte is four times smaller than an int.
		 It has a minimum value of -128(-2^7) and a maximum value of 127(2^7-1) (inclusive)

		short : 
		 The short data type is a 16-bit signed two's complement integer.
		 It has a minimum value of -32,768 and a maximum value of 32,767
	
		int:
		 int data type is a 32-bit signed two's complement integer
		 Minimum value is - 2,147,483,648.(-2^31)
		 Maximum value is 2,147,483,647(inclusive).(2^31 -1)
		
		long:
		 long data type is a 64-bit signed two's complement integer.
		 Minimum value is -9,223,372,036,854,775,808.(-2^63)
		 Maximum value is 9,223,372,036,854,775,807 (inclusive). (2^63 -1)
		
		float:
		 float data type is a single-precision 32-bit IEEE 754 floating point. 
		 Example: float f1 = 234.5f
		
		double:
		 double data type is a double-precision 64-bit IEEE 754 floating point.
		 This data type is generally used as the default data type for decimal
		 values, generally the default choice.
		
		boolean:
		 boolean data type represents one bit of information.
		 There are only two possible values: true and false.
		 This data type is used for simple flags that track true/false conditions.
		 Default value is false.
		 
		char:
		 char data type is a single 16-bit Unicode character. 
		 Minimum value is '\u0000' (or 0)
		 Maximum value is '\uffff' (or 65,535 inclusive).
		
		Java Literals:
		A literal is a source code representation of a fixed value. 
		They are represented directly in the code without any computation.
		Literals can be assigned to any primitive type variable
	*/
		byte B1 = -128; //OK
		byte B2 = 127;	//OK
		
	//	byte B3 = -129; //Error : Type mismatch: cannot convert from int to byte
						//Minimum value is -128 only
	//	byte B3 = 128; 	//Error : Type mismatch: cannot convert from int to byte
						//Maximum value is 127 only
		
		short S1 =32767; //OK 
	//	short S2 = 32768; // Error : Type mismatch: cannot convert from int to short
						  //maximum value is 32,767.
		
		char C1 = 'F';	//OK
	//	char C2 = "F";	//Error due to double codes 'cannot convert from String to char'.
		char C3 = '\u0065'; //OK, OUTPUT : e 
		//(String and char types of literals can contain any Unicode characters )
		
		int int_variable1 = 2147483647; //OK
		//int int_variable2 = 2147483648; //Error : The literal 2147483648 of type int is out of range
										//since the range is only 2^31-1(int data type is a 32-bit
										//signed two's complement integer which has a minimum value 
										//of -2^31 and a maximum value of 2^31-1.)	
		int decimal = 100; 
		int octal = 0144; // OUTPUT : 100 
		// Prefix 0 is used to indicate octal when using number systems for literals
		
		int hexa =  0x64; //OUTPUT : 100
		// Prefix 0x indicates hexadecimal when using number systems for literals
		
		float float_variable1 = 123.1234567891234567789f; //OK , OUTPUT : 123.12346 
	//	float double_variable = 123555.1234567891234567789; // Error : Type mismatch: cannot  
															// convert from double to float
		
	//	long L1 = 9223372036854775808; // Error : The literal 9223372036854775808 of type  
										//int is out of range
		long L2 = 9223372036854775807L; //OK OUTPUT :  9223372036854775807
		
	//	long L3 = 9223372036854775809L; // Error : The literal 9223372036854775809L of type 
					       				// long is out of range in it can have only "2^63-1
										//(9223372036854775808)"
	//	double D1 = 9223372036854775809888888888564445;  //The literal 9223372036854775809888888888   
												         //of type int is out of range
		double D2 = 92233720368547758098888888885644459999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999D; //OK OUTPUT : 9.223372036854776E33
		
//		long L3 = 9223372036854775809888888888564445D; //Error : Type mismatch: cannot
														//convert from double to long
		double D3 = 9223372036854775807L; //OK OUTPUT :  9.223372036854776E18
		
	}
	
}