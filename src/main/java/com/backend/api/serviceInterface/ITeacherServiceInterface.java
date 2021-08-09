package com.backend.api.serviceInterface;

import java.util.List;

import com.backend.api.entity.TeacherEntity;

public interface ITeacherServiceInterface {

	public List<TeacherEntity> listOfTeachers();

	public TeacherEntity findTeacherById(Long id);

	public TeacherEntity saveTeacher(TeacherEntity teacherEntity);

	public void deleteTeacher(Long id);

	public void sentEmailToTeacher(TeacherEntity teacher, String cleanPassword);

	public void disableTeacher(Long idUser);

	public void enableTeacher(Long idUser);

	public Boolean getStateOfTeacher(Long idTeacher);
	
	public void setTeacherBusy(Long idTeacher);
	
	public void setTeacherAvailable(Long idTeacher);
	
	public void setTeacherChatBusy(Long idTeacher);
	
	public void setTeacherChatAvailable(Long idTeacher);
	
	public Boolean getChatStateFromTeacher(Long idTeacher);
	
	public Integer getTeacherTime(Long idTeacher);
	
	public void setTeacherTime(Integer time, Long teachertId);
	
	public void setTeacherMoney(Double money, Long teacherId);
	
	public Double getTeacherCalification(Long teacherId);
	
	public Double getTeacherCalificationStudents(Long teacherId);
	
	public void updateTeacherCalification(Double califaction, Integer studentCount, Long teacherId);
	
	public void updateProfileTeacher(String profile, Long teacherId);
	
    public List<TeacherEntity> listOfTeacherFilterByDate (String initialDate, String finalDate);
	

}
