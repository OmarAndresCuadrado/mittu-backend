package com.backend.api.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.backend.api.entity.GrupalCourseEntity;

public interface IGrupalCourseDao extends JpaRepository<GrupalCourseEntity, Long> {
	
	// traer los estudiantes que peretenescan a un curso grupal en especifico
	
	
	// traer los cursos grupales que pereteneces a un profesor
	@Query(value = "select * from teacherhouse.grupal_courses where id_teacher = ?1", nativeQuery = true)
	public List<GrupalCourseEntity> getGrupalCoursesByTeacherId(Long idTeacher);
	
	@Query(value = "select * from teacherhouse.grupal_courses where teacherhouse.grupal_courses.name like %?1%", nativeQuery = true)
	public List<GrupalCourseEntity> searchByCourseName(String grupalCourseName);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "insert into teacherhouse.grupal_course_students (grupal_course_entity_id, student_entity_id) VALUES (?1, ?2);", nativeQuery = true)
	public void createInscriptionToGrupalCourse(Long courseId, Long studentId);
	
	@Query(value = "select * from teacherhouse.grupal_courses where id_teacher = ?1", nativeQuery = true)
	public List<GrupalCourseEntity> getAllGrupalCoursesFromStudent();
	
	@Query(value = "select grupal_course_entity_id from grupal_course_students where student_entity_id = ?1", nativeQuery = true)
	public List<BigInteger> findGrupalCoursesFromStudent(Long studentId);

}
