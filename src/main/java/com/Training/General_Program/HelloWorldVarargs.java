
/////////////////////// Varargs //////////////////////////
package com.myTraining.General_Program;

public class HelloWorldVarargs {
	
    public static void main(String args[]) {
    	HelloWorldVarargs j = new HelloWorldVarargs();
        j.test(215, "India", "Delhi");
        j.test(147, "United States", "New York", "California" ,"New York", "California");
        
    }
 
    public static void test(int some, String... args) {
        System.out.print("\n" + some);
        for(String arg: args) {
            System.out.print(", " + arg);
        }
    }
}

/*I
Varargs was added in Java 5 and the syntax includes three dots … (also called ellipses).

Following is the syntax of vararg method.
public void testVar(int count, <type>... vargs) { }

That mark the last argument of the method as variable argument. 
Also the vararg must be the last argument in the method.
*/