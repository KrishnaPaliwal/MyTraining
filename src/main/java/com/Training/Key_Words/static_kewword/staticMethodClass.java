package com.myTraining.Key_Words.static_kewword;
//Note : It is possible to invoke static method through
//the instance (object) of class, but it is bad programming 
//and compiler will give warning only .

public class staticMethodClass 
{
	
	public static int staticMethod(){
		int A=22;
		int B=45;
		System.out.println(A+" "+B);
		return A+B;

	}
	
	public static void main(String args[])
	{
		staticMethodClass smc = new staticMethodClass();

		int PwithWarning = smc.staticMethod(); // With warning "The static method staticMethod() 
											   // from the type staticMethodClass should be accessed 
											   //in a static way"
		System.out.println(PwithWarning);
		
		int PCorrect = staticMethodClass.staticMethod(); // Correct way to call static method , it does
														// not required instance(object) of class
		System.out.println(PCorrect);
		
		int PAlsoCorrect = staticMethod(); // This is also correct because method is available in same class
		System.out.println(PAlsoCorrect);  // Even if it extends any super-class then the static method of that
										  // class also can be called in this way.

		}

}
