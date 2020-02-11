package com.Training.General_Program;

public class Cls {
	
	public static Cls cls= new Cls();
	
	public static void main(String afdgf[])
	{
		Cls x = new Cls();
		System.out.println(x);
		x = cls;
		System.out.println(x);
		System.out.println(cls);
		x.setCls(x);
	}

	public Cls getCls() {
		return cls;
	}

	public void setCls(Cls cls) {
		this.cls = cls;
	}

}
