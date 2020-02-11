package com.Training.CoreJAVATrainingByAnjali;

interface A  
{
  void disp();
  void show();
}  

class B implements A
{
   public void disp()
   { 
    System.out.println("In disp");
   }

   public void show()
   { 
    System.out.println("In show");
   }

   void det()
   { 
    System.out.println("In det");
   }
}
 
public class Six
{
  public static void main(String args[])
  {
    B ob=new B();
    ob.disp();
    ob.show(); //Child class object can all the function of the parent class as well
    ob.det();
  }
}