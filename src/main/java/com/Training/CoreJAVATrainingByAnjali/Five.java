abstract class A  
{
  abstract void disp();
  void show()
  {
    System.out.println("In show");
  }  
}  

class B extends A
{
   public void disp()
   { 
    System.out.println("In disp");
   }

   public void det()
   { 
    System.out.println("In det");
   }
}
 
public class Five
{
  public static void main(String args[])
  {
    B ob=new B();
    ob.disp();
    ob.show(); //Child class object can all the function of the parent class as well
    ob.det();
  }
}