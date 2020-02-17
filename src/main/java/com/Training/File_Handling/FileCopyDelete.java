package com.Training.File_Handling;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileCopyDelete {

	public static void main (String[] args) throws IOException {
		
		Path source = Paths.get("files/NewFile.txt");
		Path destination = Paths.get("files/CopyFile2.txt");
		
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
	}
	
}

