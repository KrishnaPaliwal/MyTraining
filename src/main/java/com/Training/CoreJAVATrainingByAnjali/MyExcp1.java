package com.Training.CoreJAVATrainingByAnjali;

class MyExcp1
{
   public static void main(String args[])
   {
     int a,b,c=0;
     try
     {
       a=Integer.parseInt(args[0]);
       b=Integer.parseInt(args[1]);
       c=a/b;
       System.out.println("Output is " + c);
     }
     catch(ArrayIndexOutOfBoundsException ex)
     {
       System.out.println("ArrayIndexOutOfBoundsException");
     }
     catch(ArithmeticException ex)
     {
       System.out.println("ArithmeticException");
     }
     catch(Exception ex)
     {
       System.out.println("Exception");
     }
   }
}