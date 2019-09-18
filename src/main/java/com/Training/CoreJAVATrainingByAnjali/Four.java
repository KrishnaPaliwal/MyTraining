package com.myTraining.CoreJAVATrainingByAnjali;

class A  
{
   int i;
   
   A()
   {  
     i=10;
   }

   A(int x)
   {
     i=x;
   }
}


class B extends A
{
   int i;
   
   B()
   {  
     super();
     this.i=20;
   }

   B(int x, int y)
   {
     super(x);
     this.i=y;
   }

   void show()
   {
     System.out.println("A's i is " + super.i);
     System.out.println("B's i is " + this.i);
   }  
}

public class Four
{
  public static void main(String args[])
  {
    B ob1=new B();
    ob1.show();

    B ob2=new B(50,100);
    ob2.show();
  }
}