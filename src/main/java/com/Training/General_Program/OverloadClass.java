package com.Training.General_Program;

public class OverloadClass {
	
	public String method(String st)
	{
		System.out.println("String method "+st);
		return st;
		
	}
	
	public String method(Object obj)	
	{
	System.out.println("Object method");
	
		return null;
	}
	   protected static final String addEscapes(String str) {
		      StringBuffer retval = new StringBuffer();
		    if( str.matches("(.*)is(.*)")) System.out.println("true");
		      char ch;
		      for (int i = 0; i < str.length(); i++) {
		        switch (str.charAt(i))
		        {
		           case 0 :
		              continue;
		           case 'b':
		              retval.append("\\b");
		              continue;
		           case '\t':
		              retval.append("\\t");
		              continue;
		           case '\n':
		              retval.append("\\n");
		              continue;
		           case '\f':
		              retval.append("\\f");
		              continue;
		           case '\r':
		              retval.append("\\r");
		              continue;
		           case '\"':
		              retval.append("\\\"");
		              continue;
		           case '\'':
		              retval.append("\\\'");
		              continue;
		           case '\\':
		              retval.append("\\\\");
		              continue;
		           default:
		              if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
		                 String s = "0000" + Integer.toString(ch, 16);
		                 retval.append("\\u" + s.substring(s.length() - 4, s.length()));
		              } else {
		                 retval.append(ch);
		              }
		              continue;
		        }
		      }
		      return retval.toString();
		   }


	
	public static void main(String args[])
	{
		OverloadClass oc = new OverloadClass();
		Object obj = new Object();
		oc.method(obj);
		oc.method("Krishna");
		
		String srt = OverloadClass.addEscapes("bkrishna");
		System.out.println(srt);
		
	}
}
