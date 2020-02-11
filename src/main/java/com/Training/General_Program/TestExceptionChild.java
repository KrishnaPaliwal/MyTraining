package com.Training.General_Program;


import java.io.*;
class ParentClass{
  void msg() throws Exception
  	{System.out.println("parent");
  	}
}

class TestExceptionChild extends ParentClass{
  void msg() throws IOException {
    System.out.println("TestExceptionChild");
  }
  public static void main(String args[]) throws Exception{
	  ParentClass p=new TestExceptionChild();
   p.msg();
  }
}
