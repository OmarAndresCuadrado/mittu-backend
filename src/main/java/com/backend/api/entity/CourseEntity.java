package com.backend.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "courses")
public class CourseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(length = 60)
	private String name;

	private String description;

	private String picture;

	@NotNull(message = "no puede estar vacio")
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaDeCreacion;

	private Boolean busy;

	private Long idTeacher;

	private String teacherName;
	
	private String meetUrlCourse;

	@PrePersist
	public void prePersist() {
		this.fechaDeCreacion = new Date();
	}

	public CourseEntity() {
		this.prePersist();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getBusy() {
		return busy;
	}

	public void setBusy(Boolean busy) {
		this.busy = busy;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getMeetUrlCourse() {
		return meetUrlCourse;
	}

	public void setMeetUrlCourse(String meetUrlCourse) {
		this.meetUrlCourse = meetUrlCourse;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


}
