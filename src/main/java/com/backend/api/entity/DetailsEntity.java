package com.backend.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "details")
public class DetailsEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long idStudent;

	private Long idTeacher;

	private String tutoriaName;

	private String studentName;

	private String teacherName;

	private String duration;

	private String studentPayment;

	private String teacherEarning;

	private String plataformEarning;

	@Temporal(TemporalType.DATE)
	private Date fechaDeCreacion;

	@PrePersist
	public void prePersist() {
		this.fechaDeCreacion = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public String getTutoriaName() {
		return tutoriaName;
	}

	public void setTutoriaName(String tutoriaName) {
		this.tutoriaName = tutoriaName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStudentPayment() {
		return studentPayment;
	}

	public void setStudentPayment(String studentPayment) {
		this.studentPayment = studentPayment;
	}

	public String getTeacherEarning() {
		return teacherEarning;
	}

	public void setTeacherEarning(String teacherEarning) {
		this.teacherEarning = teacherEarning;
	}

	public String getPlataformEarning() {
		return plataformEarning;
	}

	public void setPlataformEarning(String plataformEarning) {
		this.plataformEarning = plataformEarning;
	}

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
