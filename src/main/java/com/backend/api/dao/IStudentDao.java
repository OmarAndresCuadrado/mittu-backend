package com.backend.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.api.entity.StudentEntity;

public interface IStudentDao extends JpaRepository<StudentEntity, Long> {

	@Query(value = "select email from usuarios where email = ?1", nativeQuery = true)
	public String findStudentByUsername(String username);

	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.students SET enabled = false, enabled_platform = false WHERE id_user = ?1", nativeQuery = true)
	public void disableStudent(Long userId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.students SET enabled = true, enabled_platform = true WHERE id_user = ?1", nativeQuery = true)
	public void enableStudent(Long userId);

	@Query(value = "select teacherhouse.students.time from teacherhouse.students WHERE id = ?1", nativeQuery = true)
	public Integer getStudentTime(Long studentId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.students SET time = ?1 where teacherhouse.students.id = ?2", nativeQuery = true)
	public void setStudentTime(Integer time, Long StudentId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.students SET money = ?1 where teacherhouse.students.id = ?2", nativeQuery = true)
	public void setStudentMoney(Double money, Long studentId);

	@Query(value = "select * from students where (fecha_de_creacion >= ?1 and fecha_de_creacion <= ?2);", nativeQuery = true)
	public List<StudentEntity> listAllStudentsFilterByDate(String initialDate, String finalDate);

	@Query(value = "select money from students where id = ?1", nativeQuery = true)
	public Long moneyFromStudent(Long studentId);

}
