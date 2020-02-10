package com.Training.Java8.stream;

public class Book {

	private String title;
	private String authorFname;
	private String authorLName;
	private int noOfPages;
	
	
	public Book( String title, String authorFName, String authorLName, int noOfPages) {
		
		this.title = title;
		this.authorFname = authorFName;
		this.authorLName = authorLName;
		this.noOfPages = noOfPages;
		
	}
	
	public String getTitle() {
		return title;
	}

	public String getAuthorFname() {
		return authorFname;
	}

	public String getAuthorLName() {
		return authorLName;
	}

	public int getNoOfPages() {
		return noOfPages;
	}
	
}
