package com.learn.lambda;

import java.util.List;

public class Instructor {

	public String name;
	public List<String> courses;
	public boolean onlineCourse;
	public int yearsOfExperience;
	
	public String getName() {
		return name;
	}
	
	public boolean isOnlineCourse() {
		return onlineCourse;
	}

	public Instructor(String name, List<String> courses, boolean onlineCourse, int yearsOfExperience) {
		super();
		this.name = name;
		this.courses = courses;
		this.onlineCourse = onlineCourse;
		this.yearsOfExperience = yearsOfExperience;
	}

	public void setOnlineCourse(boolean onlineCourse) {
		this.onlineCourse = onlineCourse;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public Instructor(String name, List<String> courses) {
		super();
		this.name = name;
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Instructor [name=" + name + ", courses=" + courses + ", onlineCourse=" + onlineCourse
				+ ", yearsOfExperience=" + yearsOfExperience + "]";
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getCourses() {
		return courses;
	}
	
	public void setCourses(List<String> courses) {
		this.courses = courses;
	}
}

