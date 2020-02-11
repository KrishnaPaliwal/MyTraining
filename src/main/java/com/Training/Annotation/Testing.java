package com.Training.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target( ElementType.METHOD)
public @interface Testing{
	
 String name();	
 String Adddress();
	
}
