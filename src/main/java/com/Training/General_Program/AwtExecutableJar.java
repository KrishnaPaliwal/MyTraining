package com.Training.General_Program;

	import java.awt.Frame;
	import java.awt.Label;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;
	 
public class AwtExecutableJar {
	 
		public static void main(String[] args) {
	 
			Frame f = new Frame();
			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			f.add(new Label("This JAR file is executable!"));
			f.setSize(500, 500);
			f.setVisible(true);
		}
	}

/*
Create a manifest.txt file.

manifest.txt:
Main-Class: com.Training.General_Programs.AwtExecutableJar

*/
