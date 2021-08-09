package com.backend.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.api.entity.CourseEntity;

public interface ICourseDao extends JpaRepository<CourseEntity, Long> {

	@Query(value = "select * from courses where subject_id = ?1", nativeQuery = true)
	public List<CourseEntity> findSubjectsFromStudent(Long subject_id);

	@Query(value = "select * from courses where teacher_id = ?1", nativeQuery = true)
	public List<CourseEntity> findCoursesOfTeacher(Long subject_id);

	@Query(value = "select * from teacherhouse.courses where teacherhouse.courses.name like %?1% and busy = false", nativeQuery = true)
	public List<CourseEntity> searchByCourseName(String courseName);

	@Modifying(clearAutomatically = true)
	@Query(value = "insert into teacherhouse.courses (name,fecha_creacion,subject_id,teacher_id,busy,description,meet_url_course,id_teacher) VALUES (?1, ?2, ?3, ?4 ,?5, ?6 ,?7,?8);", nativeQuery = true)
	public void createInscriptionToTutoria(String name, Date fechaDeCreacion, Long subjectId, Long idTeacher,
			Boolean busy, String description, String meetUrlCourse, Long teacherId);
}
