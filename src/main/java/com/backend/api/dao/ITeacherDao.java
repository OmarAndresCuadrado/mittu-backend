package com.backend.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.api.entity.TeacherEntity;

public interface ITeacherDao extends JpaRepository<TeacherEntity, Long> {
	
	@Query(value = "select email from usuarios where email = ?1", nativeQuery = true)
	public String findStudentByUsername(String username);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET enabled = false, enabled_platform = false, busy = true WHERE id_user = ?1", nativeQuery = true)
	public void disableTeacher(Long userId);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET enabled = true, enabled_platform = true, busy = false WHERE id_user = ?1", nativeQuery = true)
	public void enableTeacher(Long userId);
	
	@Query(value = "select busy from teacherhouse.teachers where id = ?1", nativeQuery = true)
	public Boolean getStateOfTeacher(Long idTeacher);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET busy = true WHERE id = ?1", nativeQuery = true)
	public void setTeacherBusy(Long idTeacher);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET busy = false WHERE id = ?1", nativeQuery = true)
	public void setTeacherAvailable(Long idTeacher);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET on_chat = true WHERE id = ?1", nativeQuery = true)
	public void setTeacherChatBusy(Long idTeacher);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET on_chat = false WHERE id = ?1", nativeQuery = true)
	public void setTeacherChatAvailable(Long idTeacher);
	
	@Query(value = "select on_chat from teacherhouse.teachers WHERE id = ?1", nativeQuery = true)
	public Boolean getChatStateFromTeacher(Long idTeacher);
	
	@Query(value = "select teacherhouse.teachers.time from teacherhouse.teachers where id = ?1", nativeQuery = true)
	public Integer getTeacherTime(Long idTeacher);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET time = ?1 where teacherhouse.teachers.id = ?2", nativeQuery = true)
	public void setTeacherTime(Integer time, Long teachertId);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET money = ?1 where teacherhouse.teachers.id = ?2", nativeQuery = true)
	public void setTeacherMoney(Double money, Long teacherId);
	
	@Query(value = "select SUM(calification)/count(*) as calification_result from teacherhouse.feedbacks where teacher_id = ?1", nativeQuery = true)
	public Double getTeacherCalification(Long teacherId);
	
	@Query(value = "select count(*) as student_count from teacherhouse.feedbacks where teacher_id = ?1", nativeQuery = true)
	public Double getTeacherCalificationStudents(Long teacherId);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET calification = ?1, count_student = ?2  WHERE id = ?3", nativeQuery = true)
	public void updateTeacherCalification(Double califaction, Integer studentCount, Long teacherId);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.teachers SET profile = ?1 WHERE id = ?2", nativeQuery = true)
	public void updateProfileTeacher(String profile, Long teacherId);
	
	
	@Query(value = "select * from teachers where (fecha_de_creacion >= ?1 and fecha_de_creacion <= ?2);", nativeQuery = true)
	public List<TeacherEntity> listOfTeacherFilterByDate(String initialDate, String endDate);


}
