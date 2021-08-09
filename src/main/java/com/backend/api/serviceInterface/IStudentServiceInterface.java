package com.backend.api.serviceInterface;

import java.util.List;

import com.backend.api.entity.StudentEntity;

public interface IStudentServiceInterface {

	public List<StudentEntity> findAllStudents();

	public StudentEntity findStudent(Long id);

	public StudentEntity saveStudent(StudentEntity studentEntity);

	public void delete(Long id);
	
	public void sentEmailToStudent(StudentEntity student, String cleanPassword);
	
	public String verifyStudentByUsername(String username);
	
	public void disableStudent(Long idUser);
	
	public void enableStudent(Long idUser);
	
	public Integer getStudentTime(Long idStudent);
	
	public void setStudentTime(Integer time, Long StudentId);
	
	public void setStudentMoney(Double money, Long studentId);
	
	public List<StudentEntity> listAllStudentsFilterByDate(String initialDate, String finalDate);
	
	public Long moneyFromStudent(Long studentId);
	

}
