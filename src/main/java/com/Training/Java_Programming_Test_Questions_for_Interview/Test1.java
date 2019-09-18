package com.myTraining.Java_Programming_Test_Questions_for_Interview;

import java.util.HashSet;

public class Test1 {

	public static void main(String[]args)
	{
		String s1= "ABC";
		String s2 = "ABC";
		
		//The given statements output will be “false” because in java +
		//operator precedence is more than == operator. So the given expression 
		//will be evaluated to “s1 == s2 is:abc” == “abc” i.e false.
		
	// 1st Question
		System.out.println("s1==s1 is : " +s1==s2); //false
		
		System.out.println("s1==s1 is : " +(s1==s2)); //true
		
	// 2nd Question
		/*
		 * The given statements output will be "ourn". First character will be 
		 * automatically type caste to int. After that since in java first character
		 * index is 0, so it will start from 'o' and print till 'n'. Note that in String
		 * substring function it leaves the end index.
		 */
		String s3 = "JournalDev";
		int start = 1;
		char end = 5;
		System.out.println(start + end);  // 6
		System.out.println(s3.substring(start, end)); //ourn
		
	
	// 3rd Question			
		/*
		 * The size of the shortSet will be 100. Java Autoboxing feature has been introduced
		 * in JDK 5, so while adding the short to HashSet<Short> it will automatically 
		 * convert it to Short object. Now "i-1" will be converted to int while evaluation
		 * and after that it will autoboxed to Integer object but there are no Integer object
		 * in the HashSet, so it will not remove anything from the HashSet and finally its
		 * size will be 100.
		 */
		HashSet<Short> shortSet = new HashSet<Short>();
		for (short i = 0; i < 100; i++) {
			shortSet.add(i);
			shortSet.remove(i - 1);
		}
		System.out.println(shortSet.size());

	// 4th Question	
		/*
		 * The given print statement will throw java.lang.NullPointerException
		 * because while evaluating the OR logical operator it will first evaluate 
		 * both the literals and since str is null, .equals() method will throw exception. 
		 * Its always advisable to use short circuit logical operators i.e "||" and "&&" which
		 * evaluates the literals values from left and since the first literal will return true,
		 * it will skip the second literal evaluation.
		 */
		String str = null;
		String str1="abc";
		System.out.println(str1.equals("abc") | str.equals(null)); // java.lang.NullPointerException
		System.out.println(str1.equals("abc") || str.equals(null)); // correct
	// 5th Question	
		/*
		 * The finally block will never be reached here. If flag will be TRUE, 
		 * it will go into an infinite loop and if its false its exiting the JVM. 
		 * So finally block will never be reached here.
		 */
		Boolean flag = false; 
		try {
			if (flag) {
				while (true) {
					System.out.println("in while loop");
				}
			} else {
				System.exit(1);
			}
			
		}
		catch(Exception e){
			System.out.println("Exception thrown");
		}
		finally {
			System.out.println("In Finally");
		}		

	}
}
