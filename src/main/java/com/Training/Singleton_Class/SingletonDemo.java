package com.Training.Singleton_Class;

public class SingletonDemo {
	   public static void main(String[] args) {
	      Singleton tmp = Singleton.getInstance( );
	      Singleton tmp2 = Singleton.getInstance( );
	      try {

			Singleton tmp3 = (Singleton) tmp2.clone();
		      System.out.println(tmp + "  " +tmp2 +"  "+tmp3);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   }
	}

//File Name: Singleton.java
class Singleton implements Cloneable {

   private static Singleton INSTANCE;
   
   /* A private Constructor prevents any other 
    * class from instantiating.
    */
   private Singleton(){ }
   
   @Override
   protected Singleton clone() throws CloneNotSupportedException 
   {
	   throw new CloneNotSupportedException();
	   // return (Singleton) super.clone();
	   
   }
   /* Static 'instance' method */
   public static Singleton getInstance( ) {
	   if(INSTANCE == null)
	   {
		   synchronized(Singleton.class) {
			   if(INSTANCE == null)
			   {
				   INSTANCE = new Singleton();			   }
		}
		   	
	   }
      return INSTANCE;
   }
}