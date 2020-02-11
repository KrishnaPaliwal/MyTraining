package com.Training.CoreJAVATrainingByAnjali;

class AA  
{
   int i;
   
   AA()
   {  
     i=10;
   }

   AA(int x)
   {
     i=x;
   }
}


class BB extends AA
{
   int j;
   
   BB()
   {  
     super();
     j=20;
   }

   BB(int x, int y)
   {
     super(x);
     j=y;
   }

   void show()
   {
     System.out.println("i is " + i);
     System.out.println("j is " + j);
   }  
}

public class Three
{
  public static void main(String args[])
  {
    BB ob1=new BB();
    ob1.show();

    BB ob2=new BB(50,100);
    ob2.show();
  }
}