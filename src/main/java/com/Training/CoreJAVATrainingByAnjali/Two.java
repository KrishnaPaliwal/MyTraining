package com.myTraining.CoreJAVATrainingByAnjali;

class Box
{
  int height;
  int width;
  int depth;
  int v;

  Box()
  {
    height=10;
    width=10;
    depth=10;
  }
  
  Box(int h,int w, int d)
  {
    height=h;
    width=w;
    depth=d;
  }

  Box(int comm)
  {
    height=comm;
    width=comm;
    depth=comm;
  }

  void vol()
  {
    v=0;
    v=height*width*depth;
    System.out.println("Volume is " + v);
  }
}

public class Two 
{
  public static void main(String args[])
  {
    Box b1=new Box();
    Box b2=new Box(5,2,6);
    Box b3=new Box(5); 
    b1.vol();
    b2.vol();
    b3.vol();
    
  }
}