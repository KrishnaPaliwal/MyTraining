package com.Training.File_Handling;

import java.io.File;

public class RenameFileExample 
{
    public static void main(String[] args)
    {	
 
		File oldfile =new File("D:/JAVA/File Input and OutputStream/oldfile.txt");//oldfile.txt should be at given path
		File newfile =new File("D:/JAVA/File Input and OutputStream/newfile.txt");
 
		if(oldfile.renameTo(newfile)){
			System.out.println("Rename succesful");
		}else{
			System.out.println("Rename failed");
		}
 
    }
}