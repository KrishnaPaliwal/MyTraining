package com.myTraining.CoreJAVATrainingByAnjali;

class NewThread1 implements Runnable
{
  String name;
  Thread t;

   NewThread1(String threadname)
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
       for(int i=1;i<10;i++)
       {
         
	System.out.println("Table 3: " + i*3);
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

class MultiTableDemo
{
  public static void main(String args[])
  {
     new NewThread1("One");
     
     try
     {
for(int i=1;i<10;i++)
       {
         
	System.out.println("Table 2: " + i*2);
         Thread.sleep(1000);
       }
       
     }   
     catch(InterruptedException e)
     {
         System.out.println("Main Thread Interrupted");
     } 
     System.out.println("Exiting Main Thread");
   }  
}