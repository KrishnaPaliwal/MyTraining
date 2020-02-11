package com.Training.CoreJAVATrainingByAnjali;

class MyExcp2
{
   public static void main(String args[])
   {
     int a,b,c=0;
     try
     {
       a=Integer.parseInt(args[0]);
       b=Integer.parseInt(args[1]);
       if(b==0)
          throw new MyEx();
        else 
        {
          c=a/b;
          System.out.println("Output is " + c);
        }
     }
     catch(MyEx e)
     {
        System.out.println("I am in catch of My Exception");
     }
  }     
}

 
class MyEx extends Exception
{
  MyEx()
  {
    System.out.println("I am My Exception");      
  }
}