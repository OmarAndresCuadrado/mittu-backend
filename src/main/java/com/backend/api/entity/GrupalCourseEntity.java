package com.backend.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "grupal_courses")
public class GrupalCourseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private Long idTeacher;

	private String picture;

	private String classTime;

	private String Description;

	private String urlMeet;

	private Double price;

	private Integer totalClases;

	private Integer clasesDone;

	private Double moneyToBePaid;

	private boolean alreadyPaid;

	private Integer studentSubscribed;

	private String initialDateCourse;

	private String findalDateCourse;

	@NotNull(message = "no puede estar vacio")
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaDeCreacion;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "grupal_course_students", joinColumns = @JoinColumn(name = "grupalCourseEntity_id"), inverseJoinColumns = @JoinColumn(name = "studentEntity_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "grupalCourseEntity_id", "studentEntity_id" }) })
	private List<StudentEntity> studentes;

	public GrupalCourseEntity() {
		studentes = new ArrayList<>();
	}

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

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public List<StudentEntity> getStudentes() {
		return studentes;
	}

	public void setStudentes(List<StudentEntity> studentes) {
		this.studentes = studentes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getClassTime() {
		return classTime;
	}

	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}

	public String getUrlMeet() {
		return urlMeet;
	}

	public void setUrlMeet(String urlMeet) {
		this.urlMeet = urlMeet;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getTotalClases() {
		return totalClases;
	}

	public void setTotalClases(Integer totalClases) {
		this.totalClases = totalClases;
	}

	public Integer getClasesDone() {
		return clasesDone;
	}

	public void setClasesDone(Integer clasesDone) {
		this.clasesDone = clasesDone;
	}

	public Double getMoneyToBePaid() {
		return moneyToBePaid;
	}

	public void setMoneyToBePaid(Double moneyToBePaid) {
		this.moneyToBePaid = moneyToBePaid;
	}

	public boolean isAlreadyPaid() {
		return alreadyPaid;
	}

	public void setAlreadyPaid(boolean alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}

	public Integer getStudentSubscribed() {
		return studentSubscribed;
	}

	public void setStudentSubscribed(Integer studentSubscribed) {
		this.studentSubscribed = studentSubscribed;
	}

	public String getInitialDateCourse() {
		return initialDateCourse;
	}

	public void setInitialDateCourse(String initialDateCourse) {
		this.initialDateCourse = initialDateCourse;
	}

	public String getFindalDateCourse() {
		return findalDateCourse;
	}

	public void setFindalDateCourse(String findalDateCourse) {
		this.findalDateCourse = findalDateCourse;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
