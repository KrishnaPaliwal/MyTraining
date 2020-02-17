package com.Training.File_Handling;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFileStoreInStringNIOPackage {

	public static void main(String [] args) throws IOException {
		
		Path path = Paths.get("C:\\Program Files\\Java\\jdk1.8.0_112\\COPYRIGHT");
		
		BufferedReader br = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
		String line = null;
		while((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}
}
