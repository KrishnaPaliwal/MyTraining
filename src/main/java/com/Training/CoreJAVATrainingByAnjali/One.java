package com.myTraining.CoreJAVATrainingByAnjali;

class Test
{
  static void show()
  {
    System.out.println("In Show");
  }

  void disp()
  {
    System.out.println("In disp");
  }
}

class One 
{
  public static void main(String args[])
  {
     System.out.println("Hello");
     Test t=new Test();
     Test.show();
     t.disp();
      
  }
}