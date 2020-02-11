package com.Training.Key_Words.super_keyword;

public class MyExceptionClass extends RuntimeException 

{
	  static final long serialVersionUID = -7034897190745766939L;
	  
	  public MyExceptionClass() 
	  {
		super();  
		  
	  }
	  
	  public MyExceptionClass(String paramString)
	  {
	    super(paramString);
	  }
	  
	  public MyExceptionClass(String paramString, Throwable paramThrowable)
	  {
	    super(paramString, paramThrowable);
	  }
	  
	  public MyExceptionClass(Throwable paramThrowable)
	  {
	    super(paramThrowable);
	  }
	  
	  protected MyExceptionClass(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2)
	  {
	    super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
	  }
	}
