package com.myTraining.String_Class;

import java.util.Date;

public class OwnStringReplaceLogic {

	private final String name;
	private final Date age;
	
	public OwnStringReplaceLogic(String name, Date age)
	{
		this.name = name;
		this.age = age;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Date getAge()
	{
		return age;
	}
	

	
    public static String replace(String str, char oldChar, char newChar) {
        if (oldChar != newChar) {
            int len = str.length();
            int i = 0;
            char[] val = str.toCharArray(); /* avoid getfield opcode */

/*            while (++i < len) {
                if (val[i] == oldChar) {
                    break;
                }
            }*/
            if (i < len) {
                char buf[] = new char[len];
/*                for (int j = 0; j < i; j++) {
                    buf[j] = val[j];
                }*/
                while (i < len) {
                	
                    char c = val[i];
                    buf[i] = (c == oldChar) ? newChar : c;
                    i++;
                }
                return new String(buf);
            }
        }
        return str;
    }
    
	public static void main(String [] arr)
	{
		String name = "Krishna";
		Date age = new Date();
		
		
		OwnStringReplaceLogic t = new OwnStringReplaceLogic(name, age);
		System.out.println(t);
		System.out.println(t.getName());
		System.out.println(t.getAge());
		
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name = "XYZ";
		age = new Date();
		
		//t = new Test(name, age);
		
		System.out.println(t);
		System.out.println(t.getName());
		System.out.println(t.getAge());

		String str = new String("HrKihKna");
		System.out.println(OwnStringReplaceLogic.replace(str, 'K', 'P'));
		
		
	}
	
}
