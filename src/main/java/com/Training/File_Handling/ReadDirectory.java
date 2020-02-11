package com.Training.File_Handling;

import java.io.File;
import java.text.SimpleDateFormat;

/*
 * Listing Directories:

You can use list( ) method provided by File object to list down all the files
and directories available in a directory as follows:
 */

public class ReadDirectory {
   public static void main(String[] args) {
      
      File file = null;
      String[] paths;
            
      try{      
         // create new file object
         file = new File("D:/JAVA/JAVA STUDY MATERIAL");
                                 
         // array of files and directory
         paths = file.list();
            
         // for each name in the path array
         for(String path:paths)
         {
            // prints filename and directory name
            System.out.println(path);
         }
         
  // file.lastModified method
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
         System.out.println(sdf.format(file.lastModified()));
         
      }catch(Exception e){
         // if any error occurs
         e.printStackTrace();
      }
   }
}