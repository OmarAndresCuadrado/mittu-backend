package com.backend.api.serviceInterface;

import java.util.Date;
import java.util.List;

import com.backend.api.entity.CourseEntity;

public interface ICourseServiceInterface  {
	
	public List<CourseEntity> listOfCourses();
	
	public CourseEntity findCourseById(Long id);
	
	public CourseEntity saveCourse(CourseEntity courseEntity);
	
	public CourseEntity updateCourse(Long id, CourseEntity courseEntity);
	
	public List<CourseEntity> findCoursesFromSubject(Long id);

	public void enableCourse();
	
	public void disableCourse();
	
	public List<CourseEntity> findCoursesOfTeacher(Long id);
	
	public List<CourseEntity> searchByCourseName(String courseName);
	
	public void insertNewCourse(String name, Date fechaDeCreacion, Long subjectId  , Long idTeacher , Boolean busy , String description, String meetUrlCourse, Long teacherId);

}
