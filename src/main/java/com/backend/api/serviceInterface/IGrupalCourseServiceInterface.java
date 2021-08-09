package com.backend.api.serviceInterface;

import java.math.BigInteger;
import java.util.List;

import com.backend.api.entity.GrupalCourseEntity;

public interface IGrupalCourseServiceInterface {
	
	public List<GrupalCourseEntity> listOfGrupalCourse();
	
	public GrupalCourseEntity findGrupalCourseById(Long id);
	
	public GrupalCourseEntity saveGrupalCourse(GrupalCourseEntity grupalCourseEntity);
	
	public GrupalCourseEntity updateGrupalCourse(Long id, GrupalCourseEntity grupalCourseEntity);
	
	public List<GrupalCourseEntity> getGrupalCoursesByTeacherId(Long idTeacher);

	public List<GrupalCourseEntity> searchByGrupalCourseName(String grupalCourseName);
	
	public void createInscriptionToGrupalCourse (Long courseId, Long studentId);
	
	public List<BigInteger> findGrupalCoursesFromStudent(Long studentId);
}
