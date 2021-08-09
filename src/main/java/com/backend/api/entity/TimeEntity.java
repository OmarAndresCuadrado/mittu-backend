package com.backend.api.entity;

import java.io.Serializable;

public class TimeEntity implements Serializable {

	private Long studentId;

	private Long teacherId;

	private String message;

	private Integer timeOnTransaction;

	private Integer newTeacherTime;

	private Integer newStudentTime;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getTimeOnTransaction() {
		return timeOnTransaction;
	}

	public void setTimeOnTransaction(Integer timeOnTransaction) {
		this.timeOnTransaction = timeOnTransaction;
	}

	public Integer getNewTeacherTime() {
		return newTeacherTime;
	}

	public void setNewTeacherTime(Integer newTeacherTime) {
		this.newTeacherTime = newTeacherTime;
	}

	public Integer getNewStudentTime() {
		return newStudentTime;
	}

	public void setNewStudentTime(Integer newStudentTime) {
		this.newStudentTime = newStudentTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
