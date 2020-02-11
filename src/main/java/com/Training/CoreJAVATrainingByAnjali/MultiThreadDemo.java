package com.Training.CoreJAVATrainingByAnjali;

class NewThread23 implements Runnable
{
  String name;
  Thread t;

   NewThread23(String threadname)
   {
     name=threadname;
     t=new Thread(this,name);
     System.out.println("New Thread " + t);
     t.start();
   }
  
   public void run()
   {
     try
     {
       for(int i=5;i>0;i--)
       {
         System.out.println(name + " : " + i);
         Thread.sleep(1000);
       }
     }   
     catch(InterruptedException e)
     {
       System.out.println(name + "Interrupted");
     } 
     System.out.println(name + " Exiting");
   }  
}

class MultiThreadDemo
{
  public static void main(String args[])
  {
     new NewThread23("One");
     new NewThread23("Two");
     new NewThread23("Three");
      
     try
     {
       Thread.sleep(10000);
     }   
     catch(InterruptedException e)
     {
         System.out.println("Main Thread Interrupted");
     } 
     System.out.println("Exiting Main Thread");
   }  
}