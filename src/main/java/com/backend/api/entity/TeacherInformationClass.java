package com.backend.api.entity;

import java.util.List;

public class TeacherInformationClass {

	private String name;

	private String lastName;

	private String phone;

	private String picture;

	private Double hourCost;

	private String profile;

	private Boolean busy;

	private String education;

	private String description;

	private Double calification;

	private Integer countStudent;

	private String email;

	private List<CourseEntity> courses;

	private List<GrupalCourseEntity> grupalCourses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Double getHourCost() {
		return hourCost;
	}

	public void setHourCost(Double hourCost) {
		this.hourCost = hourCost;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}


	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCalification() {
		return calification;
	}

	public void setCalification(Double calification) {
		this.calification = calification;
	}

	public Integer getCountStudent() {
		return countStudent;
	}

	public void setCountStudent(Integer countStudent) {
		this.countStudent = countStudent;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CourseEntity> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseEntity> courses) {
		this.courses = courses;
	}

	public List<GrupalCourseEntity> getGrupalCourses() {
		return grupalCourses;
	}

	public void setGrupalCourses(List<GrupalCourseEntity> grupalCourses) {
		this.grupalCourses = grupalCourses;
	}

	public Boolean getBusy() {
		return busy;
	}

	public void setBusy(Boolean busy) {
		this.busy = busy;
	}

}
