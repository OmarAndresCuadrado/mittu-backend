package com.backend.api.serviceImplement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.dao.ICourseDao;
import com.backend.api.entity.CourseEntity;
import com.backend.api.serviceInterface.ICourseServiceInterface;

@Service
public class CourseServiceImpl implements ICourseServiceInterface {

	@Autowired
	private ICourseDao courseDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<CourseEntity> listOfCourses() {
		return courseDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public CourseEntity findCourseById(Long id) {
		return courseDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public CourseEntity saveCourse(CourseEntity courseEntity) {
		return courseDao.save(courseEntity);
	}
	
	@Override
	@Transactional
	public CourseEntity updateCourse(Long id, CourseEntity courseEntity) {
		return courseDao.save(courseEntity);
	}

	
	@Override
	@Transactional
	public void enableCourse() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	@Transactional
	public void disableCourse() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<CourseEntity> findCoursesFromSubject(Long id) {
		return courseDao.findSubjectsFromStudent(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CourseEntity> findCoursesOfTeacher(Long id) {
		return courseDao.findCoursesOfTeacher(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CourseEntity> searchByCourseName(String courseName) {
		return courseDao.searchByCourseName(courseName);
	}

	@Override
	@Transactional
	public void insertNewCourse(String name, Date fechaDeCreacion, Long subjectId, Long idTeacher,
			Boolean busy, String description, String meetUrlCourse, Long teacherId) {
		System.out.println("id del profesor al crear el curso " + idTeacher);
		courseDao.createInscriptionToTutoria(name, fechaDeCreacion, subjectId, idTeacher, busy, description, meetUrlCourse, teacherId);
	}
	
}
